package com.raodevs.touchdraw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by gaurav on 1/10/17.
 */

public class TouchDrawView extends View {

    private Pen myPen;
    private Pen lastDrawPen; // remembers draw settings when switching to/from eraser
    private Path path;
    private String bg_color="WHITE";
    private boolean eraserMode = false;
    private float eraserWidth = 30f;
    private Context mContext;
    private ArrayList<Pair<Path, Pen>> paths;//keeps record of every different path and paint properties associated with it
    private Stack<Pair<Path, Pen>> backup;//keeps a backup for redoing the changes

    public TouchDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        path = new Path();
        paths = new ArrayList<>();
        backup = new Stack<>();
        // Disable hardware acceleration for this view — required for correct
        // Path rendering and off-screen bitmap capture via draw(canvas).
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initPaint(attrs);
    }

    //called when view is refreshed
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getParent().requestDisallowInterceptTouchEvent(true);
        Paint paint = myPen.getPen();
        for(Pair<Path, Pen> p : paths){
            canvas.drawPath(p.first, p.second.getPen());
        }
        canvas.drawPath(path, paint);
    }

    private float lastX, lastY;

    //called when screen is touched or untouched
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float X = event.getX();
        float Y = event.getY();
        int eventaction = event.getAction();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(X, Y);
                lastX = X;
                lastY = Y;
                break;

            case MotionEvent.ACTION_MOVE:
                // Draw a quadratic bezier curve through the midpoint between
                // the last position and the current position. This produces
                // smooth, natural-looking curves instead of jagged line segments.
                float midX = (lastX + X) / 2;
                float midY = (lastY + Y) / 2;
                path.quadTo(lastX, lastY, midX, midY);
                lastX = X;
                lastY = Y;
                break;

            case MotionEvent.ACTION_UP:
                // Draw final segment to the exact finger-up position
                path.lineTo(X, Y);
                Pair<Path, Pen> pair = new Pair<>(path, myPen);
                paths.add(pair);
                path = new Path();
                if (eraserMode) {
                    myPen = Pen.createEraser(eraserWidth);
                } else {
                    String c = myPen.getPaint_color();
                    float w = myPen.getStroke_width();
                    myPen = new Pen();
                    myPen.setStrokeWidth(w);
                    myPen.setPaint_color(Color.parseColor(c));
                }
                break;
        }
        invalidate();
        return true;
    }

    //Initializes and set all the values of paint and background color by taking values from xml
    private void initPaint(AttributeSet atr){
        myPen = new Pen();
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(atr, R.styleable.Canvas, 0, 0);
        String t_color = typedArray.getString(R.styleable.Canvas_paint_color);
        String t_size = typedArray.getString(R.styleable.Canvas_paint_width);
        if(t_color != null){
            myPen.setPaint_color(Color.parseColor(t_color));
        }
        if(t_size != null){
            myPen.setStrokeWidth(Float.valueOf(t_size));
        }
        String background_color = typedArray.getString(R.styleable.Canvas_bg_color);
        if(background_color != null){
            try {
                this.setBackgroundColor(Color.parseColor(background_color));
                bg_color = String.format("#%06X", (0xFFFFFF & Color.parseColor(background_color)));
            }catch (Exception e){
                Log.d("TouchDrawView", e.toString());
            }
        }
        typedArray.recycle();
    }

    //renders the current view into a Bitmap
    private Bitmap renderBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return bitmap;
    }

    //saves the screen data in storage
    public void saveFile(String folderName, String fileName){
        Bitmap bitmap = renderBitmap();

        File dir = new File(mContext.getExternalFilesDir(null), folderName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName + ".jpeg");
        FileOutputStream ostream;
        try {
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.flush();
            ostream.close();
            Toast.makeText(mContext, "image saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "error", Toast.LENGTH_LONG).show();
        }
    }

    //returns the bitmap of the current view
    public Bitmap getFile(){
        return renderBitmap();
    }

    //UnDo the last change done
    public void undo(){
        if(paths.size() >=1){
            backup.push(paths.get(paths.size()-1));
            paths.remove(paths.size()-1);
            invalidate();
        }
    }

    //ReDo the last change done
    public void redo(){
        if(!backup.empty()){
            paths.add(backup.peek());
            backup.pop();
            invalidate();
        }
    }

    //Clears the screen
    public void clear(){
        backup.clear();
        for(Pair<Path, Pen> p : paths){
            backup.push(p);
        }
        paths.clear();
        invalidate();
    }

    //getters start
    public String getPaintColor(){
        return myPen.getPaint_color();
    }

    public float getStrokeWidth(){
        return myPen.getStroke_width();
    }

    public String getBGColor(){
        return bg_color;
    }

    //getters end


    //setters start
    public void setPaintColor(int paintColor){
        myPen.setPaint_color(paintColor);
    }

    public void setStrokeWidth(float paintWidth){
        myPen.setStrokeWidth(paintWidth);
    }

    public void setBGColor(int bgColor){
        try {
            this.setBackgroundColor(bgColor);
            bg_color = String.format("#%06X", (0xFFFFFF & bgColor));
        }catch (Exception e){
            Log.d("Paint", e.toString());
        }
    }

    /**
     * Enables or disables eraser mode.
     * In eraser mode strokes clear pixels rather than painting them.
     * Switching back to draw mode restores the previous color and width.
     */
    public void setEraserMode(boolean enabled) {
        if (eraserMode == enabled) return;
        eraserMode = enabled;
        if (enabled) {
            lastDrawPen = myPen;
            myPen = Pen.createEraser(eraserWidth);
        } else {
            myPen = (lastDrawPen != null) ? lastDrawPen : new Pen();
        }
    }

    /** Returns true if eraser mode is currently active. */
    public boolean isEraserMode() {
        return eraserMode;
    }

    /**
     * Sets the eraser stroke width. Takes effect immediately if eraser mode is active.
     * @param width stroke width in pixels
     */
    public void setEraserWidth(float width) {
        eraserWidth = width;
        if (eraserMode) {
            myPen = Pen.createEraser(eraserWidth);
        }
    }
    //setters end
}

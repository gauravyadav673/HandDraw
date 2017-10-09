package com.raodevs.touchdraw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.support.annotation.Nullable;
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
    private Path path;
    private String bg_color="WHITE";
    private Context mContext;
    private ArrayList<Pair<Path, Pen>> paths;//keeps record of every different path and paint properties associated with it
    private Stack<Pair<Path, Pen>> backup;//keeps a backup for redoing the changes

    public TouchDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        path = new Path();
        paths = new ArrayList<>();
        backup = new Stack<>();
        initPaint(attrs);
    }

    //called when view is refreshed
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = myPen.getPen();
        for(Pair<Path, Pen> p : paths){
            canvas.drawPath(p.first, p.second.getPen());
        }
        canvas.drawPath(path, paint);
    }

    //called when screen is touched or untouched
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float X = (int) event.getX();
        float Y = (int) event.getY();
        int eventaction = event.getAction();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                 path.moveTo(X, Y);
                break;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(X, Y);
                break;

            case MotionEvent.ACTION_UP:
                Pair<Path, Pen> pair = new Pair<>(path, myPen);
                paths.add(pair);
                path = new Path();
                String c = myPen.getPaint_color();
                float w = myPen.getStroke_width();
                myPen = new Pen();
                myPen.setStrokeWidth(w);
                myPen.setPaint_color(Color.parseColor(c));
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
        this.setDrawingCacheEnabled(true);
        this.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


    }

    //saves the screen data in storage
    public void saveFile(String folderName, String fileName){
        Bitmap bitmap = this.getDrawingCache();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File f = new File(Environment.getExternalStorageDirectory(), folderName);
        if (!f.exists()) {
            f.mkdirs();
        }
        File file = new File(path+"/" + folderName + "/" + fileName + ".jpeg");
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

    //returns the  bitmap file of the screen
    public Bitmap getFile(){
        Bitmap file = this.getDrawingCache();
        return file;
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
    //setters end
}

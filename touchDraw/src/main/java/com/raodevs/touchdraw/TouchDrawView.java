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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

/**
 * Created by root on 1/10/17.
 */

public class TouchDrawView extends View {

    private Pen myPen;
    private Path path;
    private String bg_color="WHITE";
    private Context mContext;
    private boolean isFillOn= false;


    public TouchDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        path = new Path();
        initPaint(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = myPen.getPen();
        canvas.drawPath(path, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float X = (int) event.getX();
        float Y = (int) event.getY();
        int eventaction = event.getAction();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                if(isFillOn){
                    fillColor(X, Y);
                }else {
                    path.moveTo(X, Y);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(X, Y);
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

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


    public String getPaintColor(){
        return myPen.getPaint_color();
    }

    public float getStrokeWidth(){
        return myPen.getStroke_width();
    }

    public String getBGColor(){
        return bg_color;
    }

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

    public void setFillingOn(boolean isFill){
        isFillOn = isFill;
    }

    public boolean getIsFillingOn(){
        return isFillOn;
    }

    public void saveFile(){
        Bitmap bitmap = this.getDrawingCache();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File f = new File(Environment.getExternalStorageDirectory(), "NEW_FOLDER");
        if (!f.exists()) {
            f.mkdirs();
        }
        File file = new File(path+"/NEW_FOLDER/image.png");
        FileOutputStream ostream;
        try {
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

    public Bitmap getFile(){
        Bitmap file = this.getDrawingCache();
        return file;
    }

    public void fillColor(float x, float y){

    }
}

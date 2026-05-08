package com.raodevs.touchdraw;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

/**
 * Created by root on 1/10/17.
 */

public class Pen {
    private Paint mPen;
    private String paint_color = "#0000FF";
    private String stroke_width = "5f";
    private boolean isEraser = false;

    public Pen() {
        mPen = new Paint();
        mPen.setColor(Color.parseColor(paint_color));
        mPen.setStrokeWidth(Float.parseFloat(stroke_width));
        mPen.setAntiAlias(true);
        mPen.setStrokeJoin(Paint.Join.ROUND);
        mPen.setStrokeCap(Paint.Cap.ROUND);
        mPen.setStyle(Paint.Style.STROKE);
    }

    /**
     * Creates a Pen configured as an eraser.
     * Uses PorterDuff.CLEAR to punch through to transparency,
     * which works correctly with LAYER_TYPE_SOFTWARE.
     */
    public static Pen createEraser(float strokeWidth) {
        Pen eraser = new Pen();
        eraser.isEraser = true;
        eraser.mPen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser.mPen.setStrokeWidth(strokeWidth);
        eraser.mPen.setStrokeCap(Paint.Cap.ROUND);
        return eraser;
    }


    /* Getters Start*/

    public Paint getPen() {
        return mPen;
    }

    public String getPaint_color() {
        return paint_color;
    }

    public Float getStroke_width() {
        return Float.parseFloat(stroke_width);
    }

    public boolean isEraser() {
        return isEraser;
    }

    /* Getters End*/



    /* Setters Start*/

    public void setPaint_color(int PaintColor){
        try {
            mPen.setColor(PaintColor);
            paint_color = String.format("#%06X", (0xFFFFFF & PaintColor));
        }catch (Exception e){
            Log.d("Pen", e.toString());
        }
    }

    public void setStrokeWidth(float StrokeWidth){
        try {
            mPen.setStrokeWidth(StrokeWidth);
            stroke_width = String.valueOf(StrokeWidth);
        }catch (Exception e){
            Log.d("Paint", e.toString());
        }
    }

    /*Setters End*/
}

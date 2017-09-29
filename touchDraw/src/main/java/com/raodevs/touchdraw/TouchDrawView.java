package com.raodevs.touchdraw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gaurav on 28/9/17.
 */

public class TouchDrawView extends View {

    private Paint paint;
    private Path path;
    private String text_color="BLUE", bg_color="WHITE";
    private String text_size="5f";


    public TouchDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Canvas, 0, 0);
        initAttributes(typedArray);
        paint.setColor(Color.parseColor(text_color));
        paint.setStrokeWidth(Float.parseFloat(text_size));
        this.setBackgroundColor(Color.parseColor(bg_color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

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
                break;
        }
        invalidate();
        return true;
    }


    private void initAttributes(TypedArray typedArray) {
        String t_color = typedArray.getString(R.styleable.Canvas_paint_color);
        String t_size = typedArray.getString(R.styleable.Canvas_paint_width);
        String background_color = typedArray.getString(R.styleable.Canvas_bg_color);
        if(t_color != null){
            text_color = t_color;
        }
        if(t_size != null){
            text_size = t_size;
        }
        if(background_color != null){
            bg_color = background_color;
        }
    }


}

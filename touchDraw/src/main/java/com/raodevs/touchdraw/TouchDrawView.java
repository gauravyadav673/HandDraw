package com.raodevs.touchdraw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gaurav on 28/9/17.
 */

public class TouchDrawView extends View {

    private Paint paint;
    private Path path;
    private String text_color, bg_color;
    private String text_size;


    public TouchDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Canvas, 0, 0);
        initAttributes(typedArray);

        if(text_color != null){
            paint.setColor(Color.parseColor(text_color));
        }
        if(text_size != null){
            paint.setStrokeWidth(Float.parseFloat(text_size));
        }
        if(bg_color!= null ){
            this.setBackgroundColor(Color.parseColor(bg_color));
        }

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

    public void clear(){
        path.reset();
    }

    private void initAttributes(TypedArray typedArray) {

        text_color = typedArray.getString(R.styleable.Canvas_text_color);
        text_size = typedArray.getString(R.styleable.Canvas_text_size);
        bg_color = typedArray.getString(R.styleable.Canvas_bg_color);
    }
}

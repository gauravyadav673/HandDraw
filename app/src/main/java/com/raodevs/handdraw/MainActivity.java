package com.raodevs.handdraw;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.raodevs.touchdraw.TouchDrawView;
import com.raodevs.touchdraw.TrialTouchDrawView;

public class MainActivity extends AppCompatActivity {

    TouchDrawView touchDrawView;
    Button clearButton, unDoButton, reDoButon;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchDrawView = (TouchDrawView)findViewById(R.id.touch);

/*        touchDrawView.setPaintColor(Color.MAGENTA);
        touchDrawView.setBGColor(Color.BLUE);
        touchDrawView.setStrokeWidth(20f);*/
        Log.d("pColor", String.valueOf(touchDrawView.getPaintColor()));
        Log.d("bColor", String.valueOf(touchDrawView.getBGColor()));
        Log.d("pWidth", String.valueOf(touchDrawView.getStrokeWidth()));

        clearButton = (Button) findViewById(R.id.clear);
        unDoButton = (Button) findViewById(R.id.undo);
        reDoButon = (Button) findViewById(R.id.redo);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touchDrawView.clear();
            }
        });
        unDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touchDrawView.undo();
            }
        });
        reDoButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touchDrawView.redo();
            }
        });


    }
}

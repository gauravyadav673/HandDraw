package com.raodevs.handdraw;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.raodevs.touchdraw.TouchDrawView;

public class MainActivity extends AppCompatActivity {

    TouchDrawView touchDrawView;
    Button clearButton, unDoButton, reDoButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchDrawView = (TouchDrawView) findViewById(R.id.touch);

        Log.d("pColor", String.valueOf(touchDrawView.getPaintColor()));
        Log.d("bColor", String.valueOf(touchDrawView.getBGColor()));
        Log.d("pWidth", String.valueOf(touchDrawView.getStrokeWidth()));

        clearButton = (Button) findViewById(R.id.clear);
        unDoButton  = (Button) findViewById(R.id.undo);
        reDoButon   = (Button) findViewById(R.id.redo);
        Button eraserButton = (Button) findViewById(R.id.eraser);

        clearButton.setOnClickListener(v -> touchDrawView.clear());
        unDoButton.setOnClickListener(v -> touchDrawView.undo());
        reDoButon.setOnClickListener(v -> touchDrawView.redo());
        eraserButton.setOnClickListener(v -> {
            boolean nowErasing = !touchDrawView.isEraserMode();
            touchDrawView.setEraserMode(nowErasing);
            eraserButton.setText(nowErasing ? "DRAW" : "ERASER");
        });
    }
}

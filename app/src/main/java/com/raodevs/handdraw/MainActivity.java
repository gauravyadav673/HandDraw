package com.raodevs.handdraw;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.raodevs.touchdraw.TouchDrawView;

public class MainActivity extends AppCompatActivity {

    TouchDrawView touchDrawView;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchDrawView = (TouchDrawView)findViewById(R.id.touch);
        touchDrawView.setPaintColor(Color.MAGENTA);
        touchDrawView.setPaintColor(Color.rgb(10, 20, 10));
        touchDrawView.setBGColor(Color.BLUE);
        touchDrawView.setStrokeWidht(20f);
        saveButton = (Button)findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touchDrawView.saveFile();
            }
        });
    }
}

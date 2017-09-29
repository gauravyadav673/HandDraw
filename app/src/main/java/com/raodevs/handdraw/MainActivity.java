package com.raodevs.handdraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.raodevs.touchdraw.TouchDrawView;

public class MainActivity extends AppCompatActivity {

    TouchDrawView touchDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchDrawView = (TouchDrawView)findViewById(R.id.touch);
    }
}

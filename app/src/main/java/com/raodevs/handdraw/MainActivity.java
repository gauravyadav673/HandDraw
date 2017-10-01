package com.raodevs.handdraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
/*        touchDrawView.setPaintColor(Color.MAGENTA);
        touchDrawView.setBGColor(Color.BLUE);
        touchDrawView.setStrokeWidth(20f);*/
        Log.d("pColor", String.valueOf(touchDrawView.getPaintColor()));
        Log.d("bColor", String.valueOf(touchDrawView.getBGColor()));
        Log.d("pWidth", String.valueOf(touchDrawView.getStrokeWidth()));

        saveButton = (Button)findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touchDrawView.saveFile();
            }
        });
    }
}

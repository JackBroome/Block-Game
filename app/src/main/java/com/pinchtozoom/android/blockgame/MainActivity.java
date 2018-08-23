package com.pinchtozoom.android.blockgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));

        Level level = new Level();
        level.initialiseGrid();
    }
}

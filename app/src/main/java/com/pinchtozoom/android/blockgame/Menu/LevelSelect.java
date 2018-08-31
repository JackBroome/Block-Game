package com.pinchtozoom.android.blockgame.Menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.pinchtozoom.android.blockgame.R;

public class LevelSelect extends AppCompatActivity {

    RecyclerView levelSelectRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

         levelSelectRecyclerView = findViewById(R.id.level_select_recycler_view);
    }
}
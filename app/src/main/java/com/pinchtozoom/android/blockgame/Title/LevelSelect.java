package com.pinchtozoom.android.blockgame.Title;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pinchtozoom.android.blockgame.Objects.Level;
import com.pinchtozoom.android.blockgame.R;

import java.util.ArrayList;


public class LevelSelect extends AppCompatActivity {

    RecyclerView levelSelectRecyclerView;
    LevelSelectRecyclerViewAdapter levelSelectRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        ArrayList<Level> levels = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Level level = new Level();
            level.initialiseGrid(i + 1);
            levels.add(level);
        }

        levelSelectRecyclerView = findViewById(R.id.level_select_recycler_view);
        levelSelectRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        levelSelectRecyclerViewAdapter = new LevelSelectRecyclerViewAdapter(levels, this);
        levelSelectRecyclerView.setAdapter(levelSelectRecyclerViewAdapter);
        levelSelectRecyclerViewAdapter.notifyDataSetChanged();
    }
}
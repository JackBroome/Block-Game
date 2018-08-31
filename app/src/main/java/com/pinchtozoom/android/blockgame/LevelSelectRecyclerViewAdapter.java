package com.pinchtozoom.android.blockgame;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LevelSelectRecyclerViewAdapter extends RecyclerView.Adapter<LevelSelectRecyclerViewAdapter.LevelSelectViewHolder> {

    private ArrayList<Level> levels;
    private Context context;

    public LevelSelectRecyclerViewAdapter(ArrayList<Level> levels, Context context) {
        this.levels = levels;
        this.context = context;
    }

    static class LevelSelectViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        private LevelSelectViewHolder(View view) {
            super(view);

            textView = view.findViewById(R.id.text_view);
        }
    }

    @NonNull
    @Override
    public LevelSelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LevelSelectViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.level_select_recycler_view_content, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LevelSelectViewHolder levelSelectViewHolder, int position) {
        final Level level = levels.get(position);
        levelSelectViewHolder.textView.setText(level.levelNumber + "");
        levelSelectViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MainActivity.class).putExtra("level", level.levelNumber));
            }
        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }
}
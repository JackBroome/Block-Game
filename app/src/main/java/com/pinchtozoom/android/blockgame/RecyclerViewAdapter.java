package com.pinchtozoom.android.blockgame;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class RecyclerViewAdapter extends RecyclerView.Adapter{

    private Integer[] mThumbIds = {
            R.drawable.block, R.drawable.block,
            R.drawable.block, R.drawable.block,
            R.drawable.block, R.drawable.block,
            R.drawable.block, R.drawable.block,
            R.drawable.block, R.drawable.block,
            R.drawable.block, R.drawable.block,
            R.drawable.block, R.drawable.block,
    };

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private View view;

        private ViewHolder(View view) {
            super(view);

            view = view.findViewById(R.id.block);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mThumbIds.length;
    }
}

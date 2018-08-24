package com.pinchtozoom.android.blockgame;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pinchtozoom.android.blockgame.Library.OnSwipeListener;

class Movement {

    static int moveCharacter(Tile[][] tiles, int position, View.OnTouchListener listener, GridLayout blockLayout, GridLayout tileLayout, int[] blockHeightWidth, OnSwipeListener.Direction direction) {

        RelativeLayout relativeLayout = (RelativeLayout) blockLayout.getParent();
        final ImageView character = relativeLayout.findViewById(R.id.character);

        ImageView currentChild = (ImageView) blockLayout.getChildAt(position);

        int targetPosition = calculatePosition(direction, position, blockLayout.getColumnCount());

        if (checkTargetPosition(targetPosition, tiles)) {

            position = targetPosition;

            ImageView targetChild = (ImageView) blockLayout.getChildAt(position);

            int blockHeight = blockHeightWidth[0];
            int blockWidth = blockHeightWidth[1];

            int color = Color.TRANSPARENT;
            Drawable background = currentChild.getBackground();
            if (background instanceof ColorDrawable)
                color = ((ColorDrawable) background).getColor();

            final Drawable drawable = currentChild.getDrawable();

            currentChild.setBackgroundColor(Color.TRANSPARENT);
            currentChild.setImageDrawable(null);

            character.setLayoutParams(new RelativeLayout.LayoutParams(blockWidth, blockHeight));
            character.setImageDrawable(drawable);
            character.setBackgroundColor(color);
            character.setVisibility(View.VISIBLE);
            character.setX(currentChild.getX());
            character.setY(currentChild.getY());

            final int finalColor = color;
            final ImageView finalTargetChild = targetChild;
            character.animate().x(finalTargetChild.getX()).y(finalTargetChild.getY()).withEndAction(new Runnable() {
                @Override
                public void run() {
                    finalTargetChild.setImageDrawable(drawable);
                    finalTargetChild.setBackgroundColor(finalColor);
                    character.setVisibility(View.GONE);
                }
            });

            currentChild.setOnTouchListener(null);
            finalTargetChild.setOnTouchListener(listener);
        }
        return position;
    }

    private static int calculatePosition(OnSwipeListener.Direction direction, int position, int columnCount) {
        switch (direction) {

            case up:
                return position - columnCount;

            case down:
                return position + columnCount;

            case left:
                return position - 1;

            case right:
                return position + 1;

            default:
                return position;
        }
    }

    private static boolean checkTargetPosition(int position, Tile[][] tiles) {

        int columnCount = tiles[0].length;
        int row = position / columnCount;
        int column = position % columnCount;

        Tile tile = tiles[row][column];

        if (tile == null) {
            return true;
        }

        if (tile.tileType == TileType.WALL) {
            return false;
        } else {
            return true;
        }
    }
}
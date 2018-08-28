package com.pinchtozoom.android.blockgame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pinchtozoom.android.blockgame.Library.OnSwipeListener;

class Movement {

    static int moveCharacter(final Context context, final Tile[][] tiles, final Block[][] blocks,
                             int position, final GridLayout blockLayout,
                             int[] blockHeightWidth, OnSwipeListener.Direction direction, final CallBackListener completedListener, final BlockArrayCallbackListener arrayUpdated) {

        RelativeLayout relativeLayout = (RelativeLayout) blockLayout.getParent();
        final ImageView character = relativeLayout.findViewById(R.id.character);

        ImageView currentChild = (ImageView) blockLayout.getChildAt(position);
        final Block currentBlock = Brain.getBlock(position, blocks);

        final int originalPosition = position;

        final int targetPosition = calculatePosition(direction, position, blockLayout.getColumnCount());
        final Tile tile = Brain.getTile(targetPosition, tiles);

        if (tile == null || tile.tileType != TileType.WALL) {

            final Block targetBlock = Brain.getBlock(targetPosition, blocks);

            if (targetBlock != null && !targetBlock.hasDiamond) {
                return position;
            }
            currentChild.setOnTouchListener(null);

            position = targetPosition;

            final ImageView targetChild = (ImageView) blockLayout.getChildAt(position);

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

                    final Block[][][] updatedBlocks = {null};

                    Brain.updateArray(blocks, originalPosition, targetPosition, new BlockArrayCallbackListener() {
                        @Override
                        public void callback(Block[][] blocks) {
                            updatedBlocks[0] = blocks;
                            arrayUpdated.callback(blocks);
                        }
                    });

                    Block touchBlock = Brain.getBlock(targetPosition, updatedBlocks[0]);

                    touchBlock.touchListener = currentBlock.touchListener;

                    finalTargetChild.setOnTouchListener(touchBlock.touchListener);

                    Brain.fateCalculator(tile, targetChild, new CallBackListener() {
                        @Override
                        public void callback() {
                            Toast.makeText(context, "Level Complete", Toast.LENGTH_SHORT).show();
                            completedListener.callback();
                        }
                    });
                }
            });
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
}
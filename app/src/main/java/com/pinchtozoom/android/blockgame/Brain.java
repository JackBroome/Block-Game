package com.pinchtozoom.android.blockgame;

import android.view.View;
import android.widget.ImageView;

public class Brain {

    static void fateCalculator(Tile tile, final ImageView view, CallBackListener callBackListener) {

        if (tile != null && tile.tileType == TileType.HOLE) {
            callBackListener.callback();
        }

        if (tile != null && tile.tileType == TileType.VORTEX) {
            view.animate().scaleY(0.1f).scaleX(0.1f).rotation(270).withEndAction(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.INVISIBLE);
                    view.setClickable(false);
                    view.setFocusable(false);
                    view.setEnabled(false);
                }
            });
        }
    }

    static void updateArray(Block[][] blocks, int currentPosition, int targetPosition, BlockArrayCallbackListener blockArrayCallbackListener) {

        int columnCount = blocks[0].length;
        int currentRow = currentPosition / columnCount;
        int currentColumn = currentPosition % columnCount;

        int targetRow = targetPosition / columnCount;
        int targetColumn = targetPosition % columnCount;

        blocks[targetRow][targetColumn] = blocks[currentRow][currentColumn];

        blocks[currentRow][currentColumn] = null;

        blockArrayCallbackListener.callback(blocks);
    }

    static Tile getTile(int position, Tile[][] tiles) {

        int columnCount = tiles[0].length;
        int row = position / columnCount;
        int column = position % columnCount;
        return tiles[row][column];
    }

    static Block getBlock(int position, Block[][] blocks) {

        int columnCount = blocks[0].length;
        int row = position / columnCount;
        int column = position % columnCount;
        return blocks[row][column];
    }
}
package com.pinchtozoom.android.blockgame;

import android.widget.TextView;

public class Brain {

    static TileType getType(Tile tile) {

        if (tile != null && tile.tileType == TileType.HOLE) {
            return TileType.HOLE;
        }

        if (tile != null && tile.tileType == TileType.VORTEX) {
          return TileType.VORTEX;
        }

        return null;
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

    static void populateScores(Level level, TextView goldScore, TextView silverScore, TextView bronzeScore) {
        goldScore.setText(String.format("%s", level.goldScore));
        silverScore.setText(String.format("%s", level.silverScore));
        bronzeScore.setText(String.format("%s", level.bronzeScore));
    }
}
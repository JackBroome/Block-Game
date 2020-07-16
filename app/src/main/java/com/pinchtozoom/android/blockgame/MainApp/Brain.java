package com.pinchtozoom.android.blockgame.MainApp;

import android.widget.TextView;

import com.pinchtozoom.android.blockgame.Interface.BlockArrayCallbackListener;
import com.pinchtozoom.android.blockgame.Objects.Block;
import com.pinchtozoom.android.blockgame.Objects.Level;
import com.pinchtozoom.android.blockgame.Objects.Tile;

public class Brain {

    public static Tile.TileType getType(Tile tile) {

        if (tile != null && tile.tileType == Tile.TileType.HOLE) {
            return Tile.TileType.HOLE;
        }

        if (tile != null && tile.tileType == Tile.TileType.VORTEX) {
          return Tile.TileType.VORTEX;
        }

        return null;
    }

    public static void updateArray(Block[][] blocks, int currentPosition, int targetPosition, boolean shouldStick, BlockArrayCallbackListener blockArrayCallbackListener) {

        int columnCount = blocks[0].length;
        int currentRow = currentPosition / columnCount;
        int currentColumn = currentPosition % columnCount;

        int targetRow = targetPosition / columnCount;
        int targetColumn = targetPosition % columnCount;

        blocks[targetRow][targetColumn] = blocks[currentRow][currentColumn];

        if (!shouldStick) {
            blocks[currentRow][currentColumn] = null;
        }

        blockArrayCallbackListener.callback(blocks);
    }

    public static Tile getTile(int position, Tile[][] tiles) {

        int columnCount = tiles[0].length;
        int row = position / columnCount;
        int column = position % columnCount;
        return tiles[row][column];
    }

    public static Block getBlock(int position, Block[][] blocks) {

        int columnCount = blocks[0].length;
        int row = position / columnCount;
        int column = position % columnCount;
        return blocks[row][column];
    }

    public static void populateScores(Level level, TextView goldScore, TextView silverScore, TextView bronzeScore, TextView levelName) {
        goldScore.setText(String.format("%s", level.goldScore));
        silverScore.setText(String.format("%s", level.silverScore));
        bronzeScore.setText(String.format("%s", level.bronzeScore));
        if (levelName != null) {
            levelName.setText(String.format("Level %s", level.levelNumber));
        }
    }
}
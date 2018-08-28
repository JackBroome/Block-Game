package com.pinchtozoom.android.blockgame;

import android.view.GestureDetector;
import android.view.View;

class Block {

    int column;
    int row;
    BlockType blockType;
    View.OnTouchListener touchListener;
    GestureDetector gestureDetector;
    boolean hasDiamond;

    static Block createBlock(int row, int column, BlockType blockType, boolean hasDiamond) {
        Block block = new Block();
        block.row = row;
        block.column = column;
        block.blockType = blockType;
        block.hasDiamond = hasDiamond;
        return block;
    }
}

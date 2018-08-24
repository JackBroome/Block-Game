package com.pinchtozoom.android.blockgame;

class Block {

    int column;
    int row;
    BlockType blockType;
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

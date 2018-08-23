package com.pinchtozoom.android.blockgame;

class Block {

    private int column;
    private int row;
    private BlockType blockType;
    private boolean hasDiamond;

    static Block createBlock(int row, int column, BlockType blockType, boolean hasDiamond) {
        Block block = new Block();
        block.row = row;
        block.column = column;
        block.blockType = blockType;
        block.hasDiamond = hasDiamond;
        return block;
    }
}

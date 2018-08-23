package com.pinchtozoom.android.blockgame;

class Tile {

    private int column;
    private int row;
    private TileType tileType;

    static Tile createTile(int row, int column, TileType tileType) {
        Tile tile = new Tile();
        tile.row = row;
        tile.column = column;
        tile.tileType = tileType;
        return tile;
    }
}
package com.pinchtozoom.android.blockgame;

class Tile {

    int column;
    int row;
    TileType tileType;
    boolean canPassThrough;

    static Tile createTile(int row, int column, TileType tileType, boolean canPassThrough) {
        Tile tile = new Tile();
        tile.row = row;
        tile.column = column;
        tile.tileType = tileType;
        tile.canPassThrough = canPassThrough;
        return tile;
    }
}
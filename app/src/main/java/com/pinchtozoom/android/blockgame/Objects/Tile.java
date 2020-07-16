package com.pinchtozoom.android.blockgame.Objects;

public class Tile {

    public enum TileType {
        HOLE,
        JELLY,
        VORTEX,
        WALL
    }

    private int column;
    private int row;
    public TileType tileType;
    public boolean canPassThrough;

    static Tile createTile(int row, int column, TileType tileType, boolean canPassThrough) {
        Tile tile = new Tile();
        tile.row = row;
        tile.column = column;
        tile.tileType = tileType;
        tile.canPassThrough = canPassThrough;
        return tile;
    }
}
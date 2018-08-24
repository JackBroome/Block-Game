package com.pinchtozoom.android.blockgame;

import org.json.JSONArray;
import org.json.JSONObject;

class Level {

    private Tile [][] tiles;
    private Block [][] blocks;

    private int numberOfRows;
    private int numberOfColumns;

    private int bronzeScore = 0;

    void initialiseGrid() {

        try {

            JSONObject jsonObject = new JSONObject(JsonHelper.loadJSONFromAsset());

            JSONArray jsonArray = (JSONArray) jsonObject.get("tiles");
            numberOfRows = jsonArray.length();
            numberOfColumns = jsonArray.getJSONArray(0).length();

            tiles = new Tile[numberOfRows][numberOfColumns];
            blocks = new Block[numberOfRows][numberOfColumns];

            for (int row = 0; row < jsonArray.length(); row++) {

                for (int column = 0; column < jsonArray.getJSONArray(row).length(); column++) {

                    String value = (String) jsonArray.getJSONArray(row).get(column);

                    switch (value) {

                        case "w":

                            tiles[row][column] = Tile.createTile(row, column, TileType.WALL);
                            break;

                        case "v":

                            tiles[row][column] = Tile.createTile(row, column, TileType.VORTEX);
                            break;

                        case "j":

                            tiles[row][column] = Tile.createTile(row, column, TileType.JELLY);
                            break;

                        case "s":

                            blocks[row][column] = Block.createBlock(row, column, BlockType.STONE, false);
                            break;

                        case "s*":

                            blocks[row][column] = Block.createBlock(row, column, BlockType.STONE, true);
                            break;

                            default:

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }

    public int getBronzeScore() {
        return bronzeScore;
    }

    public void setBronzeScore(int bronzeScore) {
        this.bronzeScore = bronzeScore;
    }
}
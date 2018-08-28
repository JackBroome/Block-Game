package com.pinchtozoom.android.blockgame;

import org.json.JSONArray;
import org.json.JSONObject;

class Level {

    int levelNumber;

    Tile [][] tiles;
    Block [][] blocks;

    int numberOfRows;
    int numberOfColumns;

    int bronzeScore = 0;

    void initialiseGrid(int level) {

        try {

            this.levelNumber = level;

            JSONObject jsonObject = new JSONObject(JsonHelper.loadJSONFromAsset(level));

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

                        case "h":
                            tiles[row][column] = Tile.createTile(row, column, TileType.HOLE);
                            break;

                        case "s":
                            blocks[row][column] = Block.createBlock(row, column, BlockType.STONE, false);
                            break;

                        case "s*":
                            blocks[row][column] = Block.createBlock(row, column, BlockType.STONE, true);
                            break;

                        case "o":
                            blocks[row][column] = Block.createBlock(row, column, BlockType.OIL, false);
                            break;

                        case "o*":
                            blocks[row][column] = Block.createBlock(row, column, BlockType.OIL, true);
                            break;

                        case "b":
                            blocks[row][column] = Block.createBlock(row, column, BlockType.BULLDOZER, false);
                            break;

                        case "b*":
                            blocks[row][column] = Block.createBlock(row, column, BlockType.BULLDOZER, true);
                            break;

                        case "p":
                            blocks[row][column] = Block.createBlock(row, column, BlockType.PAWN, false);
                            break;

                        case "p*":
                            blocks[row][column] = Block.createBlock(row, column, BlockType.PAWN, true);
                            break;

                        case "c":
                            blocks[row][column] = Block.createBlock(row, column, BlockType.CASTLE, false);
                            break;

                        case "c*":
                            blocks[row][column] = Block.createBlock(row, column, BlockType.CASTLE, true);
                            break;

                            default:

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
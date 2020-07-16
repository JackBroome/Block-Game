package com.pinchtozoom.android.blockgame.Objects;

import com.pinchtozoom.android.blockgame.Library.JsonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class Level {

    public int levelNumber;

    public Tile[][] tiles;
    public Block[][] blocks;

    private int numberOfRows;
    private int numberOfColumns;

    public int bronzeScore;
    public int silverScore;
    public int goldScore;
    private int lowestMoves;
    private boolean canRotate;

    public void initialiseGrid(int level) {

        try {

            levelNumber = level;

            JSONObject jsonObject = new JSONObject(JsonHelper.loadJSONFromAsset(level));

            bronzeScore = jsonObject.getInt("bronzeScore");
            silverScore = jsonObject.getInt("silverScore");
            goldScore = jsonObject.getInt("goldScore");
            lowestMoves = jsonObject.getInt("lowestMoves");

            canRotate = jsonObject.getBoolean("canRotate");

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
                            tiles[row][column] = Tile.createTile(row, column, Tile.TileType.WALL, false);
                            break;

                        case "v":
                            tiles[row][column] = Tile.createTile(row, column, Tile.TileType.VORTEX, true);
                            break;

                        case "j":
                            tiles[row][column] = Tile.createTile(row, column, Tile.TileType.JELLY, false);
                            break;

                        case "h":
                            tiles[row][column] = Tile.createTile(row, column, Tile.TileType.HOLE, true);
                            break;

                        case "s":
                            blocks[row][column] = Block.createBlock(row, column, Block.BlockType.STONE, false);
                            break;

                        case "s*":
                            blocks[row][column] = Block.createBlock(row, column, Block.BlockType.STONE, true);
                            break;

                        case "o":
                            blocks[row][column] = Block.createBlock(row, column, Block.BlockType.OIL, false);
                            break;

                        case "o*":
                            blocks[row][column] = Block.createBlock(row, column, Block.BlockType.OIL, true);
                            break;

                        case "b":
                            blocks[row][column] = Block.createBlock(row, column, Block.BlockType.BULLDOZER, false);
                            break;

                        case "b*":
                            blocks[row][column] = Block.createBlock(row, column, Block.BlockType.BULLDOZER, true);
                            break;

                        case "p":
                            blocks[row][column] = Block.createBlock(row, column, Block.BlockType.PAWN, false);
                            break;

                        case "p*":
                            blocks[row][column] = Block.createBlock(row, column, Block.BlockType.PAWN, true);
                            break;

                        case "c":
                            blocks[row][column] = Block.createBlock(row, column, Block.BlockType.CASTLE, false);
                            break;

                        case "c*":
                            blocks[row][column] = Block.createBlock(row, column, Block.BlockType.CASTLE, true);
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
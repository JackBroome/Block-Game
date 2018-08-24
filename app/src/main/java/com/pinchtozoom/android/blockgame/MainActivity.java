package com.pinchtozoom.android.blockgame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int linearPositionTiles = 0, linearPositionBlocks = 0;

    GridLayout tileLayout, blockLayout;

    int characterPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tileLayout = findViewById(R.id.tile_layout);
        blockLayout = findViewById(R.id.block_layout);

        Level level = new Level();
        level.initialiseGrid();

        loadTiles(level);
        loadBlocks(level);
    }

    private void loadTiles(Level level) {

        int height = getWidthHeight()[0];
        int width = getWidthHeight()[1];

        Tile[][] tilesArray =  level.getTiles();

        int rowCount = tilesArray.length;
        int columnCount = tilesArray[0].length;

        tileLayout.setRowCount(rowCount);
        tileLayout.setColumnCount(columnCount);

        int blockWidth = width / columnCount;
        int blockHeight = height / rowCount;

        for (Tile[] tiles : tilesArray) {
            for (Tile tile : tiles) {

                TextView view = new TextView(this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(blockWidth, blockHeight);
                view.setLayoutParams(layoutParams);

                if (tile != null) {

                    switch (tile.tileType) {

                        case HOLE:
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorHole));
                            break;

                        case WALL:
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWall));
                            break;

                        case JELLY:
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorJelly));
                            break;

                        case VORTEX:
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorVortex));
                            break;
                    }
                } else {
                    view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackground));
                }

                tileLayout.addView(view, linearPositionTiles);

                linearPositionTiles++;
            }
        }
    }

    private void loadBlocks(Level level) {

        final int height = getWidthHeight()[0];
        int width = getWidthHeight()[1];

        Block[][] blocksArray = level.getBlocks();

        int rowCount = blocksArray.length;
        final int columnCount = blocksArray[0].length;

        blockLayout.setRowCount(rowCount);
        blockLayout.setColumnCount(columnCount);

        final int characterWidth = width / columnCount;
        final int characterHeight = height / rowCount;

        for (Block[] blocks : blocksArray) {
            for (final Block block : blocks) {

                ImageView view = new ImageView(this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(characterWidth, characterHeight);
                view.setLayoutParams(layoutParams);

                if (block != null) {

                    switch (block.blockType) {

                        case CASTLE:
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorCastle));
                            break;

                        case PAWN:
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPawn));
                            break;

                        case BULLDOZER:
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBulldozer));
                            break;

                        case OIL:
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorOil));
                            break;

                        case STONE:
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorStone));
                            break;
                    }
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }

                if (block != null && block.hasDiamond) {
                    view.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));

                    characterPosition = linearPositionBlocks;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            characterPosition = Movement.moveCharacter(view, characterPosition, this, blockLayout, new int[] {characterHeight, characterWidth});
                        }
                    });
                }

                blockLayout.addView(view, linearPositionBlocks);

                linearPositionBlocks++;
            }
        }
    }

    private int[] getWidthHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return new int[] {height, width};
    }
}
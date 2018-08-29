package com.pinchtozoom.android.blockgame;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pinchtozoom.android.blockgame.Library.OnSwipeListener;

import java.util.Timer;
import java.util.TimerTask;

interface CallBackListener {
    void callback();
}

interface BlockArrayCallbackListener {
    void callback(Block[][] blocks);
}

public class MainActivity extends AppCompatActivity {

    int linearPositionTiles = 0, linearPositionBlocks = 0;

    GridLayout tileLayout, blockLayout;

    int characterWidth, characterHeight;

    Tile[][] tilesArray;

    Block[][] blocksArray;

    Level level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tileLayout = findViewById(R.id.tile_layout);
        blockLayout = findViewById(R.id.block_layout);

        level = new Level();
        level.initialiseGrid(1);

        loadTiles(level);
        loadBlocks(level);
    }

    private void loadTiles(Level level) {

        int height = getWidthHeight()[0];
        int width = getWidthHeight()[1];

        tilesArray = level.tiles;

        int rowCount = tilesArray.length;
        int columnCount = tilesArray[0].length;

        tileLayout.setRowCount(rowCount);
        tileLayout.setColumnCount(columnCount);

        int blockWidth = width / columnCount;
        int blockHeight = height / rowCount;

        for (Tile[] tiles : tilesArray) {
            for (Tile tile : tiles) {

                ImageView view = new ImageView(this);
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

        blocksArray = level.blocks;

        int rowCount = blocksArray.length;
        final int columnCount = blocksArray[0].length;

        blockLayout.setRowCount(rowCount);
        blockLayout.setColumnCount(columnCount);

        characterWidth = width / columnCount;
        characterHeight = height / rowCount;

        for (Block[] blocks : blocksArray) {
            for (final Block block : blocks) {

                final ImageView view = new ImageView(this);
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

                if (block != null) {
                    if (block.hasDiamond) {
                        view.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
                    }

                    block.characterPosition = linearPositionBlocks;

                    block.gestureDetector = new GestureDetector(this, new OnSwipeListener() {

                        @Override
                        public boolean onSwipe(Direction direction) {

                            Log.w("", direction + "");
                            Movement.moveCharacter(MainActivity.this, tilesArray, blocksArray, block, blockLayout, tileLayout, new int[]{characterHeight, characterWidth}, direction, new CallBackListener() {
                                @Override
                                public void callback() {
                                    Toast.makeText(MainActivity.this, "level Complete", Toast.LENGTH_SHORT).show();
                                    levelCompleteListener.callback();
                                }
                            }, new BlockArrayCallbackListener() {
                                @Override
                                public void callback(Block[][] blocks) {
                                    MainActivity.this.blocksArray = blocks;
                                }
                            });
                            return true;
                        }
                    });

                    block.touchListener = new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {

                            block.gestureDetector.onTouchEvent(motionEvent);
                            return true;
                        }
                    };

                    view.setOnTouchListener(block.touchListener);
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
        return new int[]{height, width};
    }

    CallBackListener levelCompleteListener = new CallBackListener() {
        @Override
        public void callback() {

            final Handler mainHandler = new Handler(getMainLooper());

            int r = tileLayout.getRowCount();
            int c = tileLayout.getColumnCount();

            final int[] tileCount = {tileLayout.getRowCount() * tileLayout.getColumnCount()};

            final Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {

                    ImageView tile = (ImageView) tileLayout.getChildAt(tileCount[0] - 1);
                    ImageView block = (ImageView) blockLayout.getChildAt(tileCount[0] - 1);

                    if (block != null) {
                        block.animate().alpha(0).scaleX(0).scaleY(0);
                    }

                    if (tile != null) {
                        tile.animate().alpha(0).scaleX(0).scaleY(0);
                    } else {
                        timer.cancel();

                        mainHandler.postDelayed(myRunnable, 225);
                    }
                    tileCount[0]--;
                }
            };

            timer.scheduleAtFixedRate(timerTask, 0, 25);
        }
    };

    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {

            tileLayout.removeAllViews();
            blockLayout.removeAllViews();

            tileLayout.setAlpha(0f);
            blockLayout.setAlpha(0f);

            Level level = new Level();
            if (MainActivity.this.level.levelNumber == 3) {
                level.initialiseGrid(MainActivity.this.level.levelNumber);
            } else {
                level.initialiseGrid(MainActivity.this.level.levelNumber + 1);
            }

            MainActivity.this.level = level;

            linearPositionBlocks = 0;
            linearPositionTiles = 0;

            loadTiles(level);
            loadBlocks(level);

            tileLayout.animate().alpha(1);
            blockLayout.animate().alpha(1);
        }
    };
}
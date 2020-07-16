package com.pinchtozoom.android.blockgame.MainApp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pinchtozoom.android.blockgame.Library.OnSwipeListener;
import com.pinchtozoom.android.blockgame.Objects.Block;
import com.pinchtozoom.android.blockgame.Objects.Tile;
import com.pinchtozoom.android.blockgame.R;

public class Movement {

    public static int calculatePosition(OnSwipeListener.Direction direction, int position, int columnCount) {

        switch (direction) {

            case up:
                return position - columnCount;

            case down:
                return position + columnCount;

            case left:
                return position - 1;

            case right:
                return position + 1;

            default:
                return position;
        }
    }

    public static int calculateCastlePosition(OnSwipeListener.Direction direction, int position, int columnCount, Block[][] blocks, Tile[][] tiles) {

        switch (direction) {

            case up:
                Block upCharacterBlock = Brain.getBlock(position, blocks);

                position = position - columnCount;

                while (true) {
                    Block block = Brain.getBlock(position, blocks);
                    Tile tile = Brain.getTile(position, tiles);

                    if ((tile != null && !tile.canPassThrough) || block != null) {
                        position = position + columnCount;
                        return position;
                    } else {

                        if (upCharacterBlock.hasDiamond && tile != null && tile.tileType == Tile.TileType.HOLE || (tile != null && tile.tileType == Tile.TileType.VORTEX)) {
                            return position;
                        }
                        position = position - columnCount;
                    }
                }

            case down:
                Block downCharacterBlock = Brain.getBlock(position, blocks);

                position = position + columnCount;

                while (true) {
                    Block block = Brain.getBlock(position, blocks);
                    Tile tile = Brain.getTile(position, tiles);

                    if ((tile != null && !tile.canPassThrough) || block != null) {
                        position = position - columnCount;
                        return position;
                    } else {

                        if (downCharacterBlock.hasDiamond && tile != null && tile.tileType == Tile.TileType.HOLE || (tile != null && tile.tileType == Tile.TileType.VORTEX)) {
                            return position;
                        }
                        position = position + columnCount;
                    }
                }

            case left:
                Block leftCharacterBlock = Brain.getBlock(position, blocks);

                position = position - 1;

                while (true) {
                    Block block = Brain.getBlock(position, blocks);
                    Tile tile = Brain.getTile(position, tiles);

                    if ((tile != null && !tile.canPassThrough || block != null)) {

                        position = position + 1;
                        return position;
                    } else {

                        if (leftCharacterBlock.hasDiamond && tile != null && tile.tileType == Tile.TileType.HOLE || (tile != null && tile.tileType == Tile.TileType.VORTEX)) {
                            return position;
                        }
                        position = position - 1;
                    }
                }

            case right:
                Block rightCharacterBlock = Brain.getBlock(position, blocks);

                position = position + 1;

                while (true) {
                    Block block = Brain.getBlock(position, blocks);
                    Tile tile = Brain.getTile(position, tiles);

                    if ((tile != null && !tile.canPassThrough) || block != null) {

                        position = position - 1;
                        return position;
                    } else {

                        if (rightCharacterBlock.hasDiamond && tile != null && tile.tileType == Tile.TileType.HOLE || (tile != null && tile.tileType == Tile.TileType.VORTEX)) {
                            return position;
                        }
                        position = position + 1;
                    }
                }

            default:
                return position;
        }
    }

    public static int getColor(ImageView imageView) {
        int color = Color.TRANSPARENT;
        Drawable background = imageView.getBackground();
        if (background instanceof ColorDrawable) color = ((ColorDrawable) background).getColor();
        return color;
    }

    public static void levelCompleted(Context context) {
        Toast.makeText(context, "Level Complete", Toast.LENGTH_SHORT).show();
    }

    public static void fellInVortex(final Context context, final ImageView block, final ImageView tile) {
        block.animate().scaleY(0.1f).scaleX(0.1f).rotation(270).withEndAction(new Runnable() {
            @Override
            public void run() {
                block.setVisibility(View.INVISIBLE);
                block.setClickable(false);
                block.setFocusable(false);
                block.setEnabled(false);

                tile.setBackgroundColor(ContextCompat.getColor(context, R.color.colorStone));
            }
        });
    }
}
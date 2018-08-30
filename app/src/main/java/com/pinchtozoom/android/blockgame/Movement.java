package com.pinchtozoom.android.blockgame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pinchtozoom.android.blockgame.Library.OnSwipeListener;

interface OilCallback {
    void callback(int oldPosition, int position);
}

class Movement {

    private static boolean isMoving = false;

    static void moveCharacter(final Context context, final Tile[][] tiles, final Block[][] blocks,
                              final Block block, final GridLayout blockLayout, final GridLayout tileLayout,
                              final int[] blockHeightWidth, OnSwipeListener.Direction direction,
                              final CallBackListener completedListener, final BlockArrayCallbackListener arrayUpdated) {

        RelativeLayout relativeLayout = (RelativeLayout) blockLayout.getParent();
        final ImageView character = relativeLayout.findViewById(R.id.character);

        final int position = block.characterPosition;

        final int targetPosition;

        switch (block.blockType) {

            case CASTLE:
                targetPosition = calculateCastlePosition(direction, position, blockLayout.getColumnCount(), blocks, tiles);
                move(context, position, targetPosition, tiles, blocks, character, blockHeightWidth, tileLayout, blockLayout, false, completedListener, arrayUpdated);
                break;

            case STONE:
                targetPosition = calculatePosition(direction, position, blockLayout.getColumnCount());
                move(context, position, targetPosition, tiles, blocks, character, blockHeightWidth, tileLayout, blockLayout, false, completedListener, arrayUpdated);
                break;

            case OIL:
                targetPosition = calculatePosition(direction, position, blockLayout.getColumnCount());
                move(context, position, targetPosition, tiles, blocks, character, blockHeightWidth, tileLayout, blockLayout, true, completedListener, arrayUpdated);
                break;

            default:
                targetPosition = calculatePosition(direction, position, blockLayout.getColumnCount());
                move(context, position, targetPosition, tiles, blocks, character, blockHeightWidth, tileLayout, blockLayout, false, completedListener, arrayUpdated);
                break;
        }
    }

    private static int calculatePosition(OnSwipeListener.Direction direction, int position, int columnCount) {

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

    private static int calculateCastlePosition(OnSwipeListener.Direction direction, int position, int columnCount, Block[][] blocks, Tile[][] tiles) {

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

                        if (upCharacterBlock.hasDiamond && tile != null && tile.tileType == TileType.HOLE || (tile != null && tile.tileType == TileType.VORTEX)) {
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

                        if (downCharacterBlock.hasDiamond && tile != null && tile.tileType == TileType.HOLE || (tile != null && tile.tileType == TileType.VORTEX)) {
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

                        if (leftCharacterBlock.hasDiamond && tile != null && tile.tileType == TileType.HOLE || (tile != null && tile.tileType == TileType.VORTEX)) {
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

                        if (rightCharacterBlock.hasDiamond && tile != null && tile.tileType == TileType.HOLE || (tile != null && tile.tileType == TileType.VORTEX)) {
                            return position;
                        }
                        position = position + 1;
                    }
                }

            default:
                return position;
        }
    }

    private static void move(final Context context, int position, final int targetPosition,
                             Tile[][] tiles, final Block[][] blocks,
                             final ImageView character, int[] blockHeightWidth,
                             GridLayout tileLayout, GridLayout blockLayout, final boolean shouldStick,
                             final CallBackListener completedListener, final BlockArrayCallbackListener arrayUpdated) {

        final Tile tile = Brain.getTile(targetPosition, tiles);

        int color;

        ImageView currentChild = (ImageView) blockLayout.getChildAt(position);

        final int originalPosition = position;

        final Block block = Brain.getBlock(position, blocks);

        if (tile == null || tile.canPassThrough) {

            final Block targetBlock = Brain.getBlock(targetPosition, blocks);

            if (targetBlock != null || isMoving) {
                return;
            }

            currentChild.setOnTouchListener(null);

            isMoving = true;

            position = targetPosition;

            final ImageView targetChild = (ImageView) blockLayout.getChildAt(position);
            final ImageView targetTile = (ImageView) tileLayout.getChildAt(position);

            int blockHeight = blockHeightWidth[0];
            int blockWidth = blockHeightWidth[1];

            color = getColor(currentChild);

            final Drawable drawable = currentChild.getDrawable();

            if (!shouldStick) {
                currentChild.setBackgroundColor(Color.TRANSPARENT);
                currentChild.setImageDrawable(null);
            }

            character.setLayoutParams(new RelativeLayout.LayoutParams(blockWidth, blockHeight));
            character.setImageDrawable(drawable);
            character.setBackgroundColor(color);
            character.setVisibility(View.VISIBLE);
            character.setX(currentChild.getX());
            character.setY(currentChild.getY());

            final int finalColor = color;
            character.animate().x(targetChild.getX()).y(targetChild.getY()).withEndAction(new Runnable() {
                @Override
                public void run() {
                    targetChild.setImageDrawable(drawable);
                    targetChild.setBackgroundColor(finalColor);
                    character.setVisibility(View.GONE);

                    final Block[][][] updatedBlocks = {null};

                    Brain.updateArray(blocks, originalPosition, targetPosition, shouldStick,new BlockArrayCallbackListener() {
                        @Override
                        public void callback(Block[][] blocks) {
                            updatedBlocks[0] = blocks;
                            arrayUpdated.callback(blocks);
                        }
                    });

                    Block touchBlock = Brain.getBlock(targetPosition, updatedBlocks[0]);

                    touchBlock.touchListener = block.touchListener;

                    targetChild.setOnTouchListener(touchBlock.touchListener);
                    touchBlock.characterPosition = targetPosition;

                    TileType tileType = Brain.getType(tile);
                    if (tileType != null) {
                        switch (tileType) {
                            case VORTEX:
                                fellInVortex(context, targetChild, targetTile);
                                break;

                            case HOLE:
                                if (touchBlock.hasDiamond) {
                                    completedListener.callback();
                                    levelCompleted(context);
                                }
                                break;

                            default:
                                break;
                        }
                    }
                    isMoving = false;
                }
            });
        }
    }

    private static int getColor(ImageView imageView) {
        int color = Color.TRANSPARENT;
        Drawable background = imageView.getBackground();
        if (background instanceof ColorDrawable) color = ((ColorDrawable) background).getColor();
        return color;
    }

    private static void levelCompleted(Context context) {
        Toast.makeText(context, "Level Complete", Toast.LENGTH_SHORT).show();

    }

    private static void fellInVortex(final Context context, final ImageView block, final ImageView tile) {
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
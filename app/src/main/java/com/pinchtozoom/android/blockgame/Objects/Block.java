package com.pinchtozoom.android.blockgame.Objects;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pinchtozoom.android.blockgame.Interface.BlockArrayCallbackListener;
import com.pinchtozoom.android.blockgame.Interface.CallBackListener;
import com.pinchtozoom.android.blockgame.Library.OnSwipeListener;
import com.pinchtozoom.android.blockgame.MainApp.Brain;
import com.pinchtozoom.android.blockgame.MainApp.Movement;
import com.pinchtozoom.android.blockgame.R;

public class Block {

    public enum BlockType {

        CASTLE,
        PAWN,
        BULLDOZER,
        OIL,
        STONE,
        WALL
    }

    private int column;
    private int row;
    public BlockType blockType;
    public View.OnTouchListener touchListener;
    public GestureDetector gestureDetector;
    public int characterPosition;
    public boolean hasDiamond;

    static Block createBlock(int row, int column, BlockType blockType, boolean hasDiamond) {
        Block block = new Block();
        block.row = row;
        block.column = column;
        block.blockType = blockType;
        block.hasDiamond = hasDiamond;
        return block;
    }

    public void move(final Context context, final Tile[][] tiles, final Block[][] blocks,
                     final GridLayout blockLayout, final GridLayout tileLayout,
                     final int[] blockHeightWidth, OnSwipeListener.Direction direction,
                     final CallBackListener completedListener, final BlockArrayCallbackListener arrayUpdated) {

        final int targetPosition;

        switch (blockType) {

            case CASTLE:
                /** Calculate the position the castle should move to*/
                targetPosition = Movement.calculateCastlePosition(direction, characterPosition, blockLayout.getColumnCount(), blocks, tiles);
                /** Move the block to the target position*/
                move(context, characterPosition, targetPosition, tiles, blocks, blockHeightWidth, tileLayout, blockLayout, completedListener, arrayUpdated);
                break;

            case STONE:
                targetPosition = Movement.calculatePosition(direction, characterPosition, blockLayout.getColumnCount());
                move(context, characterPosition, targetPosition, tiles, blocks, blockHeightWidth, tileLayout, blockLayout, completedListener, arrayUpdated);
            case OIL:
            case BULLDOZER:
//                targetPosition = Movement.calculatePosition(direction, characterPosition, blockLayout.getColumnCount());
//                move(context, characterPosition, targetPosition, tiles, blocks, blockHeightWidth, tileLayout, blockLayout, completedListener, arrayUpdated);
                break;

            default:
                targetPosition = Movement.calculatePosition(direction, characterPosition, blockLayout.getColumnCount());
                move(context, characterPosition, targetPosition, tiles, blocks, blockHeightWidth, tileLayout, blockLayout, completedListener, arrayUpdated);
                break;
        }
    }

    private static boolean isMoving = false;

    private void move(final Context context, final int currentPosition, final int targetPosition,
                      Tile[][] tiles, final Block[][] blocks, int[] blockHeightWidth,
                      GridLayout tileLayout, GridLayout blockLayout,
                      final CallBackListener completedListener,
                      final BlockArrayCallbackListener arrayUpdated) {

        //Current tile
        final Tile tile = Brain.getTile(targetPosition, tiles);

        ImageView currentChild = (ImageView) blockLayout.getChildAt(currentPosition);

        //Current block
        final Block block = Brain.getBlock(currentPosition, blocks);

        final boolean shouldStick = block.blockType == Block.BlockType.OIL;

        // if block can move
        if (tile == null || tile.canPassThrough) {

            final Block targetBlock = Brain.getBlock(targetPosition, blocks);
            // ignore if already moving
            if (targetBlock != null || isMoving) {
                return;
            }

            currentChild.setOnTouchListener(null);

            isMoving = true;

            final ImageView targetChild = (ImageView) blockLayout.getChildAt(targetPosition);
            final ImageView targetTile = (ImageView) tileLayout.getChildAt(targetPosition);

            int blockHeight = blockHeightWidth[0];
            int blockWidth = blockHeightWidth[1];

            if (!shouldStick) {
                currentChild.setBackgroundColor(Color.TRANSPARENT);
                currentChild.setImageDrawable(null);
            }

            // Set up character

            RelativeLayout relativeLayout = (RelativeLayout) blockLayout.getParent();
            final ImageView character = relativeLayout.findViewById(R.id.character);

            final Drawable character1Drawable = currentChild.getDrawable();
            final int character1Color = Movement.getColor(currentChild);

            character.setLayoutParams(new RelativeLayout.LayoutParams(blockWidth, blockHeight));
            character.setImageDrawable(character1Drawable);
            character.setBackgroundColor(character1Color);
            character.setVisibility(View.VISIBLE);
            character.setX(currentChild.getX());
            character.setY(currentChild.getY());

            //Start character animation
            character.animate().x(targetChild.getX()).y(targetChild.getY()).withEndAction(new Runnable() {
                @Override
                public void run() {
                    //Animation finished
                    targetChild.setImageDrawable(character1Drawable);
                    targetChild.setBackgroundColor(character1Color);
                    character.setVisibility(View.GONE);

                    final Block[][][] updatedBlocks = {null};

                    Brain.updateArray(blocks, currentPosition, targetPosition, shouldStick, new BlockArrayCallbackListener() {
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

                    Tile.TileType tileType = Brain.getType(tile);
                    if (tileType != null) {
                        switch (tileType) {
                            case VORTEX:
                                Movement.fellInVortex(context, targetChild, targetTile);
                                break;

                            case HOLE:
                                if (touchBlock.hasDiamond) {
                                    completedListener.callback();
                                    Movement.levelCompleted(context);
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

//    private void moveBulldozer(final Context context, final int currentPosition,
//                               final int targetPosition, final int finishPosition,
//                               Tile[][] tiles, final Block[][] blocks, int[] blockHeightWidth,
//                               GridLayout tileLayout, GridLayout blockLayout,
//                               final CallBackListener completedListener,
//                               final BlockArrayCallbackListener arrayUpdated) {
//
//        //Current tile
//        final Tile tile = Brain.getTile(targetPosition, tiles);
//
//        ImageView currentChild = (ImageView) blockLayout.getChildAt(currentPosition);
//
//        //Original block
//        final Block originalBlock = Brain.getBlock(currentPosition, blocks);
//        final Block originalPushBlock = Brain.getBlock(targetPosition, blocks);
//
//        // if block can move
//        if (tile == null || tile.canPassThrough) {
//
//            final Block targetBlock = Brain.getBlock(targetPosition, blocks);
//            // ignore if already moving
//            if (isMoving) {
//                return;
//            }
//
//            currentChild.setOnTouchListener(null);
//
//            isMoving = true;
//
//            final ImageView targetChildImageView = (ImageView) blockLayout.getChildAt(targetPosition);
//            final ImageView targetTile = (ImageView) tileLayout.getChildAt(targetPosition);
//
//            final ImageView pushChildImageView = (ImageView) blockLayout.getChildAt(finishPosition);
//            final ImageView finalTile = (ImageView) tileLayout.getChildAt(finishPosition);
//
//            int blockHeight = blockHeightWidth[0];
//            int blockWidth = blockHeightWidth[1];
//
//            // Set up character
//
//            RelativeLayout relativeLayout = (RelativeLayout) blockLayout.getParent();
//            final ImageView character = relativeLayout.findViewById(R.id.character);
//            final ImageView pushCharacter = relativeLayout.findViewById(R.id.character2);
//
//            final Drawable character1Drawable = currentChild.getDrawable();
//            final int character1Color = Movement.getColor(currentChild);
//
//            character.setLayoutParams(new RelativeLayout.LayoutParams(blockWidth, blockHeight));
//            character.setImageDrawable(character1Drawable);
//            character.setBackgroundColor(character1Color);
//            character.setVisibility(View.VISIBLE);
//            character.setX(currentChild.getX());
//            character.setY(currentChild.getY());
//
//            currentChild.setBackgroundColor(Color.TRANSPARENT);
//            currentChild.setImageDrawable(null);
//
//            //Start character animation
//            character.animate().x(targetChildImageView.getX()).y(targetChildImageView.getY()).withEndAction(new Runnable() {
//                @Override
//                public void run() {
//                    //Animation finished
//                    targetChildImageView.setImageDrawable(character1Drawable);
//                    targetChildImageView.setBackgroundColor(character1Color);
//                    character.setVisibility(View.GONE);
//
//                    final Block[][][] updatedBlocks = {null};
//
//                    Brain.updateArray(blocks, currentPosition, targetPosition, false, new BlockArrayCallbackListener() {
//                        @Override
//                        public void callback(Block[][] blocks) {
//                            updatedBlocks[0] = blocks;
//                            arrayUpdated.callback(blocks);
//                        }
//                    });
//
//                    Block touchBlock = Brain.getBlock(targetPosition, updatedBlocks[0]);
//
//                    touchBlock.touchListener = originalBlock.touchListener;
//
//                    targetChildImageView.setOnTouchListener(touchBlock.touchListener);
//                    touchBlock.characterPosition = targetPosition;
//
//                    Tile.TileType tileType = Brain.getType(tile);
//                    if (tileType != null) {
//                        switch (tileType) {
//                            case VORTEX:
//                                Movement.fellInVortex(context, targetChildImageView, targetTile);
//                                break;
//
//                            case HOLE:
//                                if (touchBlock.hasDiamond) {
//                                    completedListener.callback();
//                                    Movement.levelCompleted(context);
//                                }
//                                break;
//
//                            default:
//                                break;
//                        }
//                    }
//                    isMoving = false;
//                }
//            });
//
//            if (targetBlock != null) {
//
//                final Drawable pushCharacterDrawable = targetChildImageView.getDrawable();
//                final int pushCharacterColor = Movement.getColor(targetChildImageView);
//
//                pushCharacter.setLayoutParams(new RelativeLayout.LayoutParams(blockWidth, blockHeight));
//                pushCharacter.setImageDrawable(pushCharacterDrawable);
//                pushCharacter.setBackgroundColor(pushCharacterColor);
//                pushCharacter.setVisibility(View.VISIBLE);
//                pushCharacter.setX(targetChildImageView.getX());
//                pushCharacter.setY(targetChildImageView.getY());
//
//                pushCharacter.animate().x(pushChildImageView.getX()).y(pushChildImageView.getY()).withEndAction(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Animation finished
//                        pushChildImageView.setImageDrawable(pushCharacterDrawable);
//                        pushChildImageView.setBackgroundColor(pushCharacterColor);
//                        pushCharacter.setVisibility(View.GONE);
//
//                        final Block[][][] updatedBlocks = {null};
//
//                        Brain.updateArray(blocks, targetPosition, finishPosition, false, new BlockArrayCallbackListener() {
//                            @Override
//                            public void callback(Block[][] blocks) {
//                                updatedBlocks[0] = blocks;
//                                arrayUpdated.callback(blocks);
//                            }
//                        });
//
//                        Block touchBlock = Brain.getBlock(finishPosition, updatedBlocks[0]);
//
//                        touchBlock.touchListener = originalPushBlock.touchListener;
//
//                        pushChildImageView.setOnTouchListener(touchBlock.touchListener);
//                        touchBlock.characterPosition = finishPosition;
//
//                        Tile.TileType tileType = Brain.getType(tile);
//                        if (tileType != null) {
//                            switch (tileType) {
//                                case VORTEX:
//                                    Movement.fellInVortex(context, targetChildImageView, finalTile);
//                                    break;
//
//                                case HOLE:
//                                    if (touchBlock.hasDiamond) {
//                                        completedListener.callback();
//                                        Movement.levelCompleted(context);
//                                    }
//                                    break;
//
//                                default:
//                                    break;
//                            }
//                        }
//                        isMoving = false;
//                    }
//                });
//            }
//        }
//    }
}
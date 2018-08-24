package com.pinchtozoom.android.blockgame;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

class Movement {

    static int moveCharacter(View view, int position, View.OnClickListener listener, GridLayout blockLayout, int[] blockHeightWidth) {

        RelativeLayout relativeLayout = (RelativeLayout) blockLayout.getParent();
        final ImageView character = relativeLayout.findViewById(R.id.character);

        int blockHeight = blockHeightWidth[0];
        int blockWidth = blockHeightWidth[1];

        ImageView currentChild = (ImageView) view;

        int color = Color.TRANSPARENT;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable) color = ((ColorDrawable) background).getColor();

        final Drawable drawable = currentChild.getDrawable();

        currentChild.setBackgroundColor(Color.TRANSPARENT);
        currentChild.setImageDrawable(null);

        character.setLayoutParams(new RelativeLayout.LayoutParams(blockWidth, blockHeight));
        character.setImageDrawable(drawable);
        character.setBackgroundColor(color);
        character.setVisibility(View.VISIBLE);
        character.setX(currentChild.getX());
        character.setY(currentChild.getY());

        final ImageView targetChild = (ImageView) blockLayout.getChildAt(position + 1);

        final int finalColor = color;
        character.animate().x(targetChild.getX()).y(targetChild.getY()).withEndAction(new Runnable() {
            @Override
            public void run() {
                targetChild.setImageDrawable(drawable);
                targetChild.setBackgroundColor(finalColor);

                character.setVisibility(View.GONE);
            }
        });

        position = position + 1;
        currentChild.setOnClickListener(null);
        targetChild.setOnClickListener(listener);
        return position;
    }
}

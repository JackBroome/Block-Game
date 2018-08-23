package com.pinchtozoom.android.blockgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BlockView extends View {

    Paint paint;
    Path path;

    public BlockView(Context context, int color) {
        super(context);
        init(color);
    }

    public BlockView(Context context, @Nullable AttributeSet attrs, int color) {
        super(context, attrs);
        init(color);
    }

    public BlockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int color) {
        super(context, attrs, defStyleAttr);
        init(color);
    }

    private void init(int color){
        paint = new Paint();
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, 250, 250, paint);
    }
}
package com.xwj.xwjnote4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xwj.xwjnote4.R;

/**
 * Created by xwjsd on 2016-01-28.
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Paint paint;

    public MyItemDecoration(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(context.getResources().getColor(R.color.divider_color));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = 1;
        outRect.bottom = 1;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View itemView = parent.getChildAt(i);
            c.drawLine(10, itemView.getY(), itemView.getWidth()-10, itemView.getY(), paint);
        }
    }
}

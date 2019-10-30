package com.moez.QKSMS.feature.compose;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xueyakun on 2019/5/5.
 */

public class ItemSpaceDecoration  extends RecyclerView.ItemDecoration{
    private int mTopSpace ;
    public ItemSpaceDecoration(int top){
        mTopSpace = top;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) != 0){
            outRect.top = mTopSpace;
        }


    }
}

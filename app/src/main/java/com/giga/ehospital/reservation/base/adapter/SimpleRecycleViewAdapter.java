package com.giga.ehospital.reservation.base.adapter;

import android.content.Context;

import com.giga.ehospital.reservation.R;

import java.util.List;

public abstract class SimpleRecycleViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    private final static int SIMPLE_LAYOUT = R.layout.recyclerview_item_simple;
    private final static int SIMPLE_LAYOUT_TEXTVIEW_ID = R.id.text;

    public SimpleRecycleViewAdapter(Context ctx, List<T> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return SIMPLE_LAYOUT;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, T item) {
        holder.setText(SIMPLE_LAYOUT_TEXTVIEW_ID, display(position, item));
    }

    /**
     * 展示信息使用
     * @param position
     * @param item
     * @return
     */
    public abstract String display(int position, T item);
}


package com.giga.ehospital.reservation.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected final List<T> mData;
    protected final Context mContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;

    public BaseRecyclerViewAdapter(Context context, List<T> list) {
        mData = (list != null) ? list : new ArrayList<T>();
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(mContext,
                mInflater.inflate(getItemLayoutId(viewType), parent, false));
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(v -> mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition()));
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> {
                mLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                return true;
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (mData.size() > 0) {
            bindData(holder, position, mData.get(position));  // 生产
        } else {
            // todo 上线前去除
            bindData(holder, position, null); // 测试使用
        }
    }

    public T getItem(int pos) {
        return mData.get(pos);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    @SuppressWarnings("SameReturnValue")
    abstract public int getItemLayoutId(int viewType);

    abstract public void bindData(RecyclerViewHolder holder, int position, T item);

    public interface OnItemClickListener {
        void onItemClick(View itemView, int pos);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int pos);
    }
}

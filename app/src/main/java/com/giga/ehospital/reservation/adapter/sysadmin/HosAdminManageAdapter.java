package com.giga.ehospital.reservation.adapter.sysadmin;

import android.content.Context;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.adapter.BaseRecyclerViewAdapter;
import com.giga.ehospital.reservation.base.adapter.RecyclerViewHolder;
import com.giga.ehospital.reservation.model.system.Buser;

import java.util.List;

public class HosAdminManageAdapter extends BaseRecyclerViewAdapter<Buser> {

    public HosAdminManageAdapter(Context context, List<Buser> list) {
        super(context, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recyclerview_item_hos_admin;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Buser item) {
        holder.getTextView(R.id.tv_item_hos_admin_name).setText(item.getUserName());
        holder.getTextView(R.id.tv_item_hos_admin_id).setText(item.getLoginId());
    }
}

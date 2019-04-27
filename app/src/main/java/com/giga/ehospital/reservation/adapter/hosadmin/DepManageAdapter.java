package com.giga.ehospital.reservation.adapter.hosadmin;

import android.content.Context;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.adapter.BaseRecyclerViewAdapter;
import com.giga.ehospital.reservation.base.adapter.RecyclerViewHolder;
import com.giga.ehospital.reservation.model.hospital.Department;

import java.util.List;

public class DepManageAdapter extends BaseRecyclerViewAdapter<Department> {

    public DepManageAdapter(Context context, List<Department> list) {
        super(context, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recyclerview_item_department;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Department item) {
        holder.getTextView(R.id.tv_item_depname).setText(item.getDepartmentName());
        holder.getTextView(R.id.tv_item_deptypename).setText(item.getTypeName());
    }
}

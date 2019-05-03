package com.giga.ehospital.reservation.adapter.hosadmin;

import android.content.Context;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.adapter.BaseRecyclerViewAdapter;
import com.giga.ehospital.reservation.base.adapter.RecyclerViewHolder;
import com.giga.ehospital.reservation.model.hospital.Calendar;

import java.util.List;

public class CalendarManageAdapter extends BaseRecyclerViewAdapter<Calendar> {

    public CalendarManageAdapter(Context context, List<Calendar> list) {
        super(context, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recyclerview_item_calendar;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Calendar item) {
        String departmentName = item.getDepartmentName();
        String doctorName = item.getDoctorName();
        String displayName = doctorName;
        holder.setText(R.id.tv_item_vdoctorName, displayName);
        holder.setText(R.id.tv_item_vdate, item.getAdmissionDate() + "    剩余人数：" + item.getRemainingNum());
    }
}
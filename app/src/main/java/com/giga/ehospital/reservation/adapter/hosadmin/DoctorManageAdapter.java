package com.giga.ehospital.reservation.adapter.hosadmin;

import android.content.Context;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.adapter.BaseRecyclerViewAdapter;
import com.giga.ehospital.reservation.base.adapter.RecyclerViewHolder;
import com.giga.ehospital.reservation.model.hospital.Doctor;

import java.util.List;

public class DoctorManageAdapter extends BaseRecyclerViewAdapter<Doctor> {

    public DoctorManageAdapter(Context context, List<Doctor> list) {
        super(context, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recyclerview_item_doctor;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Doctor item) {
        holder.getTextView(R.id.tv_item_doctorname).setText(item.getDoctorName());
        holder.getTextView(R.id.tv_item_loginid).setText(item.getLoginId());
    }

}

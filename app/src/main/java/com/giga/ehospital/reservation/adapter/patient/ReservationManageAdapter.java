package com.giga.ehospital.reservation.adapter.patient;

import android.content.Context;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.adapter.BaseRecyclerViewAdapter;
import com.giga.ehospital.reservation.base.adapter.RecyclerViewHolder;
import com.giga.ehospital.reservation.model.Patient;
import com.giga.ehospital.reservation.model.reservation.Reservation;

import java.util.List;

public class ReservationManageAdapter extends BaseRecyclerViewAdapter<Reservation> {

    public ReservationManageAdapter(Context context, List<Reservation> list) {
        super(context, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recyclerview_item_reservation;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Reservation item) {
        holder.getTextView(R.id.tv_item_resid).setText("预约号：" + item.getReservationId());
        String status = "";
        Integer isAdmission = item.getIsAdmission();

        if (isAdmission == 0)
            status = "待就诊";
        else if (isAdmission == 1)
            status = "已就诊";
        else if (isAdmission == 2)
            status = "已取消";
        holder.getTextView(R.id.tv_item_resstatus).setText("预约状态：" + status);
    }
}

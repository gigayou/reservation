package com.giga.ehospital.reservation.adapter.sysadmin;

import android.content.Context;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.adapter.BaseRecyclerViewAdapter;
import com.giga.ehospital.reservation.base.adapter.RecyclerViewHolder;
import com.giga.ehospital.reservation.model.hospital.Hospital;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class HosManageAdapter extends BaseRecyclerViewAdapter<Hospital> {

    public HosManageAdapter(Context context, List<Hospital> list) {
        super(context, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recyclerview_item_hospital;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Hospital item) {
        holder.getTextView(R.id.tv_item_hosname).setText(item.getHospitalName());
        String hospitalAddr = item.getHospitalAddr();
        String detailAddr = item.getDetailAddr();
        if (StringUtils.isNotBlank(hospitalAddr) && StringUtils.isNotBlank(detailAddr))
            holder.getTextView(R.id.tv_item_hosaddr).setText(item.getHospitalAddr() + " " + item.getDetailAddr());
        else if (StringUtils.isNotBlank(hospitalAddr) && !StringUtils.isNotBlank(detailAddr))
            holder.getTextView(R.id.tv_item_hosaddr).setText(item.getHospitalAddr());
        else
            holder.getTextView(R.id.tv_item_hosaddr).setText(item.getDetailAddr());
    }
}


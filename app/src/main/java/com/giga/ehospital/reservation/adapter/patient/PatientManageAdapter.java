package com.giga.ehospital.reservation.adapter.patient;

import android.content.Context;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.adapter.BaseRecyclerViewAdapter;
import com.giga.ehospital.reservation.base.adapter.RecyclerViewHolder;
import com.giga.ehospital.reservation.model.Patient;

import java.util.List;

public class PatientManageAdapter extends BaseRecyclerViewAdapter<Patient> {

    public PatientManageAdapter(Context context, List<Patient> list) {
        super(context, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recyclerview_item_patient;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Patient item) {
        holder.getTextView(R.id.tv_item_patient_card).setText(item.getPickerViewText());
    }
}

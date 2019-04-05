package com.giga.ehospital.reservation.adapter.sysadmin;

import android.content.Context;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.adapter.BaseRecyclerViewAdapter;
import com.giga.ehospital.reservation.base.adapter.RecyclerViewHolder;
import com.giga.ehospital.reservation.model.system.BackupDBFile;

import java.util.List;


public class DBManageAdapter extends BaseRecyclerViewAdapter<BackupDBFile> {

    public DBManageAdapter(Context context, List<BackupDBFile> list) {
        super(context, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recyclerview_item_db_backup_file;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, BackupDBFile item) {
        holder.getTextView(R.id.tv_item_filename).setText(item.getFilename());
        holder.getTextView(R.id.tv_item_filedate).setText(item.getDate());
    }

}

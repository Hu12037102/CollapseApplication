package com.xiaobai.collapseapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<String> mData;
    private Context mContext;

    public DataAdapter(List<String> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_data_view,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTvData.setText(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData == null?0:mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvData;

         ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvData = itemView.findViewById(R.id.tv_data);
        }
    }
}

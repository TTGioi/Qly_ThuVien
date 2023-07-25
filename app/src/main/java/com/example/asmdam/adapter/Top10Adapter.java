package com.example.asmdam.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmdam.R;
import com.example.asmdam.model.Sach;

import java.util.ArrayList;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHodel>{
    private Context context;
    private ArrayList<Sach> list;

    public Top10Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_top10, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.tvMaSach.setText("Mã sách: "+list.get(position).getMaSach());
        holder.tvTenSach.setText("Tên sách: "+list.get(position).getTenSach());
        holder.tvSoLuongMuon.setText("Số lượng mượn: "+list.get(position).getSoluongMuon());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder{
        TextView tvMaSach, tvTenSach, tvSoLuongMuon;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            tvMaSach = itemView.findViewById(R.id.tv_maSach);
            tvTenSach = itemView.findViewById(R.id.tv_tenSach);
            tvSoLuongMuon = itemView.findViewById(R.id.tv_SoLuongMuon);
        }
    }
}

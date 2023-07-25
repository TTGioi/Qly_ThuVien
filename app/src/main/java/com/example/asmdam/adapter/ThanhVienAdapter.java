package com.example.asmdam.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmdam.R;
import com.example.asmdam.dao.ThanhVienDAO;
import com.example.asmdam.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ThanhVien> list;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recyler_tv, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txt_TenTV.setText("Tên: "+list.get(position).getHoTen());
        holder.txt_NamSinhTV.setText("Năm sinh: "+list.get(position).getNamSinh());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                int check = thanhVienDAO.xoaTV(list.get(holder.getAdapterPosition()).getMaTV());
                switch (check){
                    case 1:
                        list.clear();
                        list = thanhVienDAO.getDSThanhVien();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Không thể xóa", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_TenTV, txt_NamSinhTV;
        ImageView iv_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_TenTV = itemView.findViewById(R.id.txt_TenTV);
            txt_NamSinhTV = itemView.findViewById(R.id.txt_NamSinhTV);
            iv_delete = itemView.findViewById(R.id.iv_deleteTV);
        }
    }
}

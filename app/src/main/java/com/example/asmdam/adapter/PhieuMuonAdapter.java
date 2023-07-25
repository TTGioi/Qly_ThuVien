package com.example.asmdam.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmdam.R;
import com.example.asmdam.dao.PhieuMuonDAO;
import com.example.asmdam.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHodel>{

    private ArrayList<PhieuMuon> list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rycycler_phieumuon, parent, false);


        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.tvMaPM.setText("Mã PM: " +list.get(position).getMaPM());
        holder.tvTenTV.setText("Tên TV: " +list.get(position).getTenTV());
        holder.tvTenTT.setText("Tên TT: " +list.get(position).getTenTT());
        holder.tvTenSach.setText("Tên sách: " +list.get(position).getTenSach());
        holder.tvNgay.setText("Ngày: " +list.get(position).getNgay());
        String trangThai = "";
        if (list.get(position).getTraSach() == 1){
            trangThai = "Đã trả sách";
            holder.btnTraSach.setVisibility(View.GONE);
        }else {
            trangThai = "Chưa trả sách";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.tvTrangThai.setText("Trạng Thái: " +trangThai);
        holder.tvTien.setText("Tiền: " +list.get(position).getTienThue());

        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean kiemtra = phieuMuonDAO.thaydoiTrangThai(list.get(holder.getAdapterPosition()).getMaPM());
                if (kiemtra){
                    list.clear();
                    list = phieuMuonDAO.getDSPhieuMuon();
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã trả sách", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder{
        TextView tvMaPM, tvTenTV, tvTenTT, tvTenSach, tvNgay, tvTrangThai, tvTien;
        Button btnTraSach;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            tvMaPM = itemView.findViewById(R.id.tv_MaPM);
            tvTenTV = itemView.findViewById(R.id.tv_TenTV);
            tvTenTT = itemView.findViewById(R.id.tv_TenTT);
            tvTenSach = itemView.findViewById(R.id.tv_TenSach);
            tvNgay = itemView.findViewById(R.id.tv_Ngay);
            tvTrangThai = itemView.findViewById(R.id.tv_TrangThai);
            tvTien = itemView.findViewById(R.id.tv_Tien);
            btnTraSach = itemView.findViewById(R.id.btn_TraSach);

        }
    }
}

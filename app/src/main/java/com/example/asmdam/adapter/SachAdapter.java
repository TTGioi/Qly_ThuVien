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
import com.example.asmdam.dao.SachDAO;
import com.example.asmdam.model.Sach;

import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHodel>{
    private Context context;
    private ArrayList<Sach> list;

    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_sach, parent, false);

        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.txtTenSach.setText("Tên sách: "+list.get(position).getTenSach());
        holder.txtGiaThue.setText("Giá thuê: "+list.get(position).getGiaThue());
        holder.txtTenLoai.setText("Tên loại: "+list.get(position).getTenLoai());

        holder.iv_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SachDAO sachDAO = new SachDAO(context);
                int check = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMaSach());
                if (check == 1) {
                    list.clear();
                    list = sachDAO.getDSDauSach();
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else if(check == -1){
                    Toast.makeText(context, "Không thể xóa", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder{
        TextView txtTenSach, txtGiaThue, txtTenLoai;
        ImageView iv_Xoa;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            txtTenSach = itemView.findViewById(R.id.txt_TenSach);
            txtGiaThue = itemView.findViewById(R.id.txt_GiaThue);
            txtTenLoai = itemView.findViewById(R.id.txt_TenLoai);
            iv_Xoa = itemView.findViewById(R.id.iv_Xoa);

        }
    }
}

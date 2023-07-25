package com.example.asmdam.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmdam.R;
import com.example.asmdam.adapter.LoaiSachAdapter;
import com.example.asmdam.dao.LoaiSachDAO;
import com.example.asmdam.model.LoaiScah;

import java.util.ArrayList;

public class QLLoaiSachFragment extends Fragment {
    RecyclerView recyclerView;
    LoaiSachDAO loaiSachDAO;
    ArrayList<LoaiScah> list;
    LoaiSachAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlloaisach, container, false);

        recyclerView = view.findViewById(R.id.recycler_LS);
        EditText edLoaiSach = view.findViewById(R.id.edLoaisach);
        Button btnThem = view.findViewById(R.id.btnThemLS);

        loaiSachDAO = new LoaiSachDAO(getContext());
        loadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edLoaiSach.getText().toString();

                if (loaiSachDAO.themLoaiSach(tenloai)){
                    loadData();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Thêm loại sách không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private  void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        list = loaiSachDAO.getDSLoaiSach();
        adapter = new LoaiSachAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }
}

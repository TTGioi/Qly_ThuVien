package com.example.asmdam.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmdam.R;
import com.example.asmdam.adapter.ThanhVienAdapter;
import com.example.asmdam.dao.ThanhVienDAO;
import com.example.asmdam.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class QLThanhVienFragment extends Fragment {
    ThanhVienDAO thanhVienDAO;
    ThanhVienAdapter adapter;
    ArrayList<ThanhVien> list;
    RecyclerView recyclerViewTV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qlthanhvien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewTV = view.findViewById(R.id.recycler_TV);
        thanhVienDAO = new ThanhVienDAO(getContext());
        loadData();

        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd_TV);

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }
    private void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewTV.setLayoutManager(linearLayoutManager);

        list = thanhVienDAO.getDSThanhVien();
        adapter = new ThanhVienAdapter(getContext(), list);
        recyclerViewTV.setAdapter(adapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themtv, null);
        EditText edTen = view.findViewById(R.id.ed_ThemTenTV);
        EditText edNS = view.findViewById(R.id.ed_ThemNSTV);
        builder.setView(view);


        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ten = edTen.getText().toString();
                String namsinh = edNS.getText().toString();

                ThanhVien thanhVien = new ThanhVien(ten, namsinh);

                if (thanhVienDAO.themTV(thanhVien)){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }



            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

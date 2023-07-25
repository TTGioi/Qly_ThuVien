package com.example.asmdam.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmdam.R;
import com.example.asmdam.adapter.SachAdapter;
import com.example.asmdam.dao.LoaiSachDAO;
import com.example.asmdam.dao.SachDAO;
import com.example.asmdam.model.LoaiScah;
import com.example.asmdam.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class QLSachFragment extends Fragment {

    SachDAO sachDAO;
    ArrayList<Sach> list;
    SachAdapter adapter;
    RecyclerView recycler_Sach;
    FloatingActionButton floatinng_Sach;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qlsach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler_Sach = view.findViewById(R.id.recycler_Sach);
        floatinng_Sach = view.findViewById(R.id.floatAdd_Sach);

        sachDAO = new SachDAO(getContext());
        loadData();

        floatinng_Sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    private void loadData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_Sach.setLayoutManager(layoutManager);
        list = sachDAO.getDSDauSach();
        adapter = new SachAdapter(getContext(), list);
        recycler_Sach.setAdapter(adapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(view);


        EditText ed_TenSach = view.findViewById(R.id.ed_TenSach);
        EditText ed_GiaThue = view.findViewById(R.id.ed_GiaThue);
        Spinner spn_TenLoai = view.findViewById(R.id.spn_TenLoaiS);
        getDataLoaiSach(spn_TenLoai);


        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenSach = ed_TenSach.getText().toString();
                int giaThue = Integer.parseInt(ed_GiaThue.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spn_TenLoai.getSelectedItem();
                int maloai = (int) hs.get("maloai");
                Sach sach = new Sach(tenSach, giaThue, maloai);

                boolean check = sachDAO.themSach(sach);

                if (check){
                    loadData();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getDataLoaiSach(Spinner spn_TL){
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiScah> list = loaiSachDAO.getDSLoaiSach();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (LoaiScah ls : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maloai", ls.getMaLoai());
            hs.put("tenloai", ls.getTenLoai());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1});
        spn_TL.setAdapter(simpleAdapter);
    }

}

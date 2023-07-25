package com.example.asmdam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmdam.dao.ThuThuDAO;

public class DangNhapActivity extends AppCompatActivity {

    ThuThuDAO thuThuDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        EditText edUser = findViewById(R.id.ed_user);
        EditText edPass = findViewById(R.id.ed_pass);
        Button btnLG = findViewById(R.id.btn_login);
        TextView tvDKy = findViewById(R.id.txt_DKy);

        thuThuDAO = new ThuThuDAO(this);

        edUser.setText("thuthu01");
        edPass.setText("abc123");

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);
        
        btnLG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edUser.getText().toString();
                String pass = edPass.getText().toString();
                if (thuThuDAO.checkDangNhap(user, pass)){

                    startActivity(new Intent(DangNhapActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(DangNhapActivity.this, "Tên đănh nhập hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvDKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_dangky, null);
        EditText edUserDK = view.findViewById(R.id.ed_userDK);
        EditText edPassDK = view.findViewById(R.id.ed_passDK);
        EditText edTenTT = view.findViewById(R.id.ed_tenTT);
        EditText edLoaiTK = view.findViewById(R.id.ed_loaiTK);

        builder.setView(view);

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userDK = edUserDK.getText().toString();
                String tenTT = edTenTT.getText().toString();
                String passDK = edPassDK.getText().toString();
                String loaiTK = edLoaiTK.getText().toString();

                boolean check = thuThuDAO.insertTKTT(userDK,tenTT, passDK, loaiTK);
                if (check){
                    Toast.makeText(DangNhapActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DangNhapActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
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
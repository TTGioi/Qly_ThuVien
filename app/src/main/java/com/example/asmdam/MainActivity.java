package com.example.asmdam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmdam.dao.SachDAO;
import com.example.asmdam.dao.ThuThuDAO;
import com.example.asmdam.fragment.DoanhThuFragment;
import com.example.asmdam.fragment.QLLoaiSachFragment;
import com.example.asmdam.fragment.QLPhieuMuonFragment;
import com.example.asmdam.fragment.QLSachFragment;
import com.example.asmdam.fragment.QLThanhVienFragment;
import com.example.asmdam.fragment.ThongKeTop10Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tooBar);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtTen = headerLayout.findViewById(R.id.txt_Name);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.it_QLPhieuMuon:
                        fragment = new QLPhieuMuonFragment();
                        break;
                    case R.id.it_QLLoaiSach:
                        fragment = new QLLoaiSachFragment();
                        break;
                    case R.id.it_QLThanhVien:
                        fragment = new QLThanhVienFragment();
                        break;
                    case R.id.it_QLSach:
                        fragment = new QLSachFragment();
                        break;
                    case R.id.it_Top:
                        fragment = new ThongKeTop10Fragment();
                        break;
                    case R.id.it_DoanhThu:
                        fragment = new DoanhThuFragment();
                        break;
                    case R.id.it_DangXuat:
                        Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    case R.id.it_DoiMK:
                        showDialogDoiMK();
                        break;
                    default:
                        fragment = new QLPhieuMuonFragment();
                        break;

                }
                if (fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
                    toolbar.setTitle(item.getTitle());
                }

                //tự đóng navigation
                drawerLayout.closeDrawer(GravityCompat.START);


                return false;
            }
        });

        //hiển thị một số chức năng cho Admin
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String loaiTK = sharedPreferences.getString("loaitaikhoan", "");
        if (!loaiTK.equals("admin")){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.it_DoanhThu).setVisible(false);
            menu.findItem(R.id.it_Top).setVisible(false);
        }

        String hoten = sharedPreferences.getString("hoten", "");
        txtTen.setText(hoten);

    }

    //xử lý sk click menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialogDoiMK(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNegativeButton("Cập nhật", null)
                .setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);
        EditText edOldPass = view.findViewById(R.id.ed_PassOld);
        EditText edNewPass = view.findViewById(R.id.ed_NewPass);
        EditText edReNewPass = view.findViewById(R.id.ed_ReNewPass);

        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edOldPass.getText().toString();
                String newPass = edNewPass.getText().toString();
                String  reNewPass = edReNewPass.getText().toString();

                if(oldPass.equals("") || newPass.equals("") || reNewPass.equals("")){
                    Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (newPass.equals(reNewPass)){
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String matt = sharedPreferences.getString("matt", "");
                        ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                        int check = thuThuDAO.capNhatMatKhau(matt, oldPass, newPass);
                        if (check == 1) {
                            Toast.makeText(MainActivity.this, "Cập nhật MK thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else if(check == 0){
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(MainActivity.this, "Nhập mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
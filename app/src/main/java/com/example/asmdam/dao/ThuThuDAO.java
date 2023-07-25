package com.example.asmdam.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmdam.database.DbHelper;

public class ThuThuDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;
    public ThuThuDAO(Context context){
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", MODE_PRIVATE);
    }

    public boolean checkDangNhap(String matt, String matkhau){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from THUTHU where matt = ? and matkhau = ?", new String[]{matt, matkhau});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("matt", cursor.getString(0));
            editor.putString("hoten", cursor.getString(1));
            editor.putString("loaitaikhoan", cursor.getString(3));
            editor.commit();
            return true;
        }else {
            return false;
        }
    }

    public int capNhatMatKhau(String username, String oldPass, String newPass){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from THUTHU where matt = ? and matkhau = ?", new String[]{username, oldPass});
        if (cursor.getCount() > 0){
            ContentValues values = new ContentValues();
            values.put("matkhau", newPass);
            long check =  db.update("THUTHU", values, "matt = ?", new String[]{username});
            if (check == -1){
                return -1;
            }
            return 1;
        }
        return 0;
    }

    public boolean insertTKTT(String matt, String tenTT, String matkhau, String loaiTK){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matt", matt);
        values.put("hoten", tenTT);
        values.put("matkhau", matkhau);
        values.put("loaitaikhoan", loaiTK);
        long check = db.insert("THUTHU", null, values);
        if (check == -1){
            return false;
        }
        return true;
    }
}

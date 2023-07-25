package com.example.asmdam.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmdam.database.DbHelper;
import com.example.asmdam.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDAO {
    DbHelper dbHelper;
    public ThanhVienDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<ThanhVien> getDSThanhVien(){
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from THANHVIEN", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themTV(ThanhVien thanhVien){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", thanhVien.getHoTen());
        values.put("namsinh", thanhVien.getNamSinh());
        long check = db.insert("THANHVIEN", null, values);
        if (check == -1){
            return false;
        }
        return true;
    }

    public boolean suaTV(ThanhVien thanhVien){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", thanhVien.getHoTen());
        values.put("namsinh", thanhVien.getNamSinh());
        long check = db.update("THANHVIEN", values, "matv = ?", new String[]{String.valueOf(thanhVien.getMaTV())});
        if (check == -1){
            return false;
        }
        return true;
    }

    public int xoaTV(int maTV){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from PHIEUMUON where matv = ?", new String[]{String.valueOf(maTV)});
        if (cursor.getCount() != 0){
            return -1;
        }

        int check = db.delete("THANHVIEN", "matv = ?", new String[]{String.valueOf(maTV)});
        if (check == -1){
            return 0;
        }
        return 1;
    }
}

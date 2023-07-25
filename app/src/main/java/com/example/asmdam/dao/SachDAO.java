package com.example.asmdam.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmdam.database.DbHelper;
import com.example.asmdam.model.Sach;

import java.util.ArrayList;

public class SachDAO {
    DbHelper dbHelper;

    public SachDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getDSDauSach(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select sc.masach, sc.tensach, sc.giathue, sc.maloai, ls.tenloai from SACH sc, LOAISACH ls where sc.maloai = ls.maloai", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean themSach(Sach sach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", sach.getTenSach());
        values.put("giathue", sach.getGiaThue());
        values.put("maloai", sach.getMaLoai());
        long check = db.insert("SACH", null, values);
        if (check == -1){
            return false;
        }
        return true;
    }

    public int xoaSach(int maSach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from PHIEUMUON where masach = ?", new String[]{String.valueOf(maSach)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = db.delete("SACH", "masach = ?", new String[]{String.valueOf(maSach)});

        if (check == -1){
            return 0;
        }
        return 1;
    }
}

package com.example.asmdam.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmdam.database.DbHelper;
import com.example.asmdam.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDAO {
    DbHelper dbHelper;

    public PhieuMuonDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<PhieuMuon> getDSPhieuMuon(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc WHERE pm.matv = tv.matv and pm.matt = tt.matt and pm.masach = sc.masach ORDER BY pm.mapm DESC", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean thaydoiTrangThai(int mapm){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trasach", 1);
        long check = db.update("PHIEUMUON", values, "mapm=?", new String[]{String.valueOf(mapm)});
        if (check == -1){
            return false;
        }
        return true;
    }

    public boolean themPhieuMuon(PhieuMuon phieuMuon){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matv", phieuMuon.getMaTV());
        values.put("matt",phieuMuon.getMaTT());
        values.put("masach", phieuMuon.getMaSach());
        values.put("ngay", phieuMuon.getNgay());
        values.put("trasach", phieuMuon.getTraSach());
        values.put("tienthue", phieuMuon.getTienThue());
        long check = db.insert("PHIEUMUON", null, values);
        if (check == -1){
            return false;
        }
        return true;
    }
}

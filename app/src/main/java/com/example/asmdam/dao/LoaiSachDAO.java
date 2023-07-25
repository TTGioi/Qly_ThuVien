package com.example.asmdam.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmdam.database.DbHelper;
import com.example.asmdam.model.LoaiScah;

import java.util.ArrayList;

public class LoaiSachDAO {
    DbHelper dbHelper;
    public LoaiSachDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<LoaiScah> getDSLoaiSach(){
        ArrayList<LoaiScah> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from LOAISACH", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new LoaiScah(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    //thêm loại sách
    public boolean themLoaiSach(String tenloai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenloai", tenloai);
        long check = db.insert("LOAISACH", null, values);
        if (check == -1){
            return false;
        }
        return true;
    }

    //xoá
    // 1 xoaTC, 0 xoaTB, -1 cosach
    public int xoaLoaiSach(int maloai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from SACH where maloai = ?", new String[]{String.valueOf(maloai)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = db.delete("LOAISACH", "maloai=?", new String[]{String.valueOf(maloai)});

        if (check == -1){
            return 0;
        }
        return 1;
    }

}

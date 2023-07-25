package com.example.asmdam.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context, "DBDANGKY3", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table THUTHU(matt text primary key, hoten text, matkhau text, loaitaikhoan text)";
        db.execSQL(sql);
        sql = "create table THANHVIEN(matv integer primary key autoincrement, hoten text, namsinh text)";
        db.execSQL(sql);
        sql = "create table LOAISACH(maloai integer primary key autoincrement, tenloai text)";
        db.execSQL(sql);
        sql = "create table SACH(masach integer primary key autoincrement, tensach text, giathue integer, maloai integer references LOAISACH(maloai))";
        db.execSQL(sql);
        sql = "create table PHIEUMUON(mapm integer primary key autoincrement, matv integer references THANHVIEN(matv), matt text references THUTHU(matt), masach integer references SACH(masach), ngay text, trasach integer, tienthue integer)";
        db.execSQL(sql);

        //data
        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'CNTT'),(2,'TKDH'),(3, 'Maketing')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Học photoshop', 2500, 2), (2, 'Tin học', 1000, 1), (3, 'Lập trình Android', 2000, 1)");
        db.execSQL("INSERT INTO THUTHU VALUES ('thuthu01','Nguyễn Văn An','abc123', 'admin'),('thuthu02','Phạm Văn Tiến','abc123','thuthu')");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Trần Văn Khôi','2008'),(2,'Trần Thảo Nguyên','2001')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'thuthu01', 1, '19/03/2022', 1, 2500),(2,1,'thuthu01', 3, '19/03/2022', 0, 2000),(3,2,'thuthu02', 2, '19/03/2022', 1, 1000)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("drop table if exists THUTHU");
            db.execSQL("drop table if exists THANHVIEN");
            db.execSQL("drop table if exists LOAISACH");
            db.execSQL("drop table if exists SACH");
            db.execSQL("drop table if exists PHIEUMUON");
            onCreate(db);
        }
    }
}

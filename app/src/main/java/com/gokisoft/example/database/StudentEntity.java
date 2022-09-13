package com.gokisoft.example.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gokisoft.example.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diep.Tran on 9/6/22.
 */

public class StudentEntity {
    public final static String TABLE_NAME = "students";
    public final static String SQL_CREATE_TABLE = "create table students (\n" +
            "\t_id integer primary key autoincrement,\n" +
            "\trollno varchar(20),\n" +
            "\tfullname varchar(50),\n" +
            "\temail varchar(150),\n" +
            "\taddress varchar(200)\n" +
            ")";

    public static List<Student> list() {
        List<Student> dataList = new ArrayList<>();

        SQLiteDatabase db = DBHelper.getInstance(null).getReadableDatabase();

        String sql = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()) {
            Student std = new Student(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("rollno")),
                    cursor.getString(cursor.getColumnIndex("fullname")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("address"))
            );

            dataList.add(std);
        }

        return dataList;
    }

    public static void insert(Student std) {
        SQLiteDatabase db = DBHelper.getInstance(null).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("rollno", std.getRollno());
        values.put("fullname", std.getFullname());
        values.put("email", std.getEmail());
        values.put("address", std.getAddress());

        db.insert(TABLE_NAME, null, values);
    }

    public static void update(Student std) {
        SQLiteDatabase db = DBHelper.getInstance(null).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("rollno", std.getRollno());
        values.put("fullname", std.getFullname());
        values.put("email", std.getEmail());
        values.put("address", std.getAddress());

        db.update(TABLE_NAME, values, "_id = " + std.get_id(), null);
    }

    public static void delete(int id) {
        SQLiteDatabase db = DBHelper.getInstance(null).getWritableDatabase();

        db.delete(TABLE_NAME, "_id = " + id, null);
    }

    public static Cursor getCursor() {
        List<Student> dataList = new ArrayList<>();

        SQLiteDatabase db = DBHelper.getInstance(null).getReadableDatabase();

        String sql = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }
}

package com.gokisoft.example.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gokisoft.example.models.NewNote;

public class NewNoteEntity {
    public static final String TABLE_NAME = "note";
    public static final String SQL_CREATE_TABLE = "create table note (\n" +
            "\t_id integer primary key autoincrement,\n" +
            "\tnoidung text,\n" +
            "\tquantrong integer,\n" +
            "\tngaytao datetime\n" +
            ")";

    public static void insert(NewNote note) {
        SQLiteDatabase db = DBHelper.getInstance(null).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("noidung", note.getNoidung());
        values.put("quantrong", note.isQuantrong()?1:0);
        values.put("ngaytao", note.getNgaytaoStr());

        db.insert(TABLE_NAME, null, values);
    }

    public static void update(NewNote note) {
        SQLiteDatabase db = DBHelper.getInstance(null).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("noidung", note.getNoidung());
        values.put("quantrong", note.isQuantrong()?1:0);
        values.put("ngaytao", note.getNgaytaoStr());

        db.update(TABLE_NAME, values, "_id = " + note.get_id(), null);
    }

    public static void delete(int id) {
        SQLiteDatabase db = DBHelper.getInstance(null).getWritableDatabase();

        db.delete(TABLE_NAME, "_id = " + id, null);
    }

    public static Cursor getCursor() {
        SQLiteDatabase db = DBHelper.getInstance(null).getReadableDatabase();

        String sql = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }
}

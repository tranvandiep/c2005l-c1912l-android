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

    public static Cursor getCursor() {
        SQLiteDatabase db = DBHelper.getInstance(null).getReadableDatabase();

        String sql = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }
}

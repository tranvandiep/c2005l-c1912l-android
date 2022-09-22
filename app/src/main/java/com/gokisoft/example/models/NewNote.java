package com.gokisoft.example.models;

import android.database.Cursor;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNote {
    int _id;
    String noidung;
    boolean quantrong;
    Date ngaytao;

    public NewNote() {
    }

    public NewNote(int _id, String noidung, boolean quantrong, Date ngaytao) {
        this._id = _id;
        this.noidung = noidung;
        this.quantrong = quantrong;
        this.ngaytao = ngaytao;
    }

    public NewNote(String noidung, boolean quantrong, Date ngaytao) {
        this.noidung = noidung;
        this.quantrong = quantrong;
        this.ngaytao = ngaytao;
    }

    public void setData(Cursor cursor) {
        this._id = cursor.getInt(cursor.getColumnIndex("_id"));
        this.noidung = cursor.getString(cursor.getColumnIndex("noidung"));
        this.quantrong = cursor.getInt(cursor.getColumnIndex("quantrong")) == 1?true:false;
        String ntao = cursor.getString(cursor.getColumnIndex("ngaytao"));
        try {
            this.ngaytao = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ntao);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public boolean isQuantrong() {
        return quantrong;
    }

    public void setQuantrong(boolean quantrong) {
        this.quantrong = quantrong;
    }

    public Date getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Date ngaytao) {
        this.ngaytao = ngaytao;
    }

    public String getNgaytaoStr() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = formatter.format(ngaytao);
        return s;
    }
}

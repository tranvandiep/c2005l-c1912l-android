package com.gokisoft.example.models;

import android.database.Cursor;

import java.io.Serializable;

/**
 * Created by Diep.Tran on 8/30/22.
 */

public class Student implements Serializable{
    int _id;
    String rollno, fullname, email, address;

    public Student() {
    }

    public Student(String rollno, String fullname, String email, String address) {
        this.rollno = rollno;
        this.fullname = fullname;
        this.email = email;
        this.address = address;
    }

    public Student(int _id, String rollno, String fullname, String email, String address) {
        this._id = _id;
        this.rollno = rollno;
        this.fullname = fullname;
        this.email = email;
        this.address = address;
    }

    public void setData(Cursor cursor) {
        this._id = cursor.getInt(cursor.getColumnIndex("_id"));
        this.rollno = cursor.getString(cursor.getColumnIndex("rollno"));
        this.fullname = cursor.getString(cursor.getColumnIndex("fullname"));
        this.email = cursor.getString(cursor.getColumnIndex("email"));
        this.address = cursor.getString(cursor.getColumnIndex("address"));
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

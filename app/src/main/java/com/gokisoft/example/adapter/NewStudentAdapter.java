package com.gokisoft.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.gokisoft.example.R;
import com.gokisoft.example.models.Student;

/**
 * Created by Diep.Tran on 9/8/22.
 */

public class NewStudentAdapter extends CursorAdapter {
    Activity activity;

    public NewStudentAdapter(Activity activity, Cursor c) {
        super(activity, c);

        this.activity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_student, null);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Student std = new Student();
        std.setData(cursor);

        TextView fullnameView = view.findViewById(R.id.is_fullname);
        TextView rollnoView = view.findViewById(R.id.is_rollno);
        TextView emailView = view.findViewById(R.id.is_email);
        TextView addressView = view.findViewById(R.id.is_address);

        fullnameView.setText(std.getFullname());
        rollnoView.setText("RollNo: " + std.getRollno());
        emailView.setText("Email: " + std.getEmail());
        addressView.setText("Address: " + std.getAddress());
    }
}

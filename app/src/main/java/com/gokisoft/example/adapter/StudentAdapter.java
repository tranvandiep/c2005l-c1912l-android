package com.gokisoft.example.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gokisoft.example.R;
import com.gokisoft.example.models.Student;

import java.util.List;

/**
 * Created by Diep.Tran on 8/30/22.
 */

public class StudentAdapter extends BaseAdapter{
    List<Student> dataList;
    Activity activity;

    public StudentAdapter(List<Student> dataList, Activity activity) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(activity).inflate(R.layout.item_student, null);

        Student std = dataList.get(position);

        TextView fullnameView = convertView.findViewById(R.id.is_fullname);
        TextView rollnoView = convertView.findViewById(R.id.is_rollno);
        TextView emailView = convertView.findViewById(R.id.is_email);
        TextView addressView = convertView.findViewById(R.id.is_address);

        fullnameView.setText(std.getFullname());
        rollnoView.setText("RollNo: " + std.getRollno());
        emailView.setText("Email: " + std.getEmail());
        addressView.setText("Address: " + std.getAddress());

        return convertView;
    }
}

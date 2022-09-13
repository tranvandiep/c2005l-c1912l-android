package com.gokisoft.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SubjectActivity extends AppCompatActivity {
    Button addBtn;
    ListView listView;

    List<String> subjectList = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        addBtn = findViewById(R.id.as_add_btn);
        listView = findViewById(R.id.as_listview);

        //Fake data
        subjectList.add("Lap Trinh C");
        subjectList.add("HTML/CSS/JS");
        subjectList.add("Bootstrap/jQuery");
        subjectList.add("SQL Server");
        subjectList.add("PHP/Laravel");
        subjectList.add("eProject I");

        adapter = new ArrayAdapter(this, R.layout.item_subject, R.id.is_subject_name, subjectList);

        listView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubjectActivity.this, InputSubjectActivity.class);

                startActivityForResult(i, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 100:
                String subjectName = data.getStringExtra("subjectname");

                subjectList.add(subjectName);

                adapter.notifyDataSetChanged();
                break;
        }
    }
}

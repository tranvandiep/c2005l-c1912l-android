package com.gokisoft.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InputSubjectActivity extends AppCompatActivity {
    EditText subjectTxt;
    Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_subject);

        subjectTxt = findViewById(R.id.ais_subjectname);
        saveBtn = findViewById(R.id.ais_save);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gui dc du lieu ve SubjectActivity
                Intent i = new Intent();
                i.putExtra("subjectname", subjectTxt.getText().toString());

                setResult(100, i);

                finish();
            }
        });
    }
}

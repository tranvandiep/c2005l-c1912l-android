package com.gokisoft.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditorStudentActivity extends AppCompatActivity {
    EditText fullnameTxt, rollnoTxt, emailTxt, addressTxt;
    Button cancelBtn, saveBtn;

    int currPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_student);

        fullnameTxt = findViewById(R.id.aes_fullname);
        rollnoTxt = findViewById(R.id.aes_rollno);
        emailTxt = findViewById(R.id.aes_email);
        addressTxt = findViewById(R.id.aes_address);

        cancelBtn = findViewById(R.id.aes_cancel_btn);
        saveBtn = findViewById(R.id.aes_save_btn);

        String fullname = getIntent().getStringExtra("fullname");
        String rollno = getIntent().getStringExtra("rollno");
        String email = getIntent().getStringExtra("email");
        String address = getIntent().getStringExtra("address");
        currPos = getIntent().getIntExtra("position", -1);

        fullnameTxt.setText(fullname);
        emailTxt.setText(email);
        rollnoTxt.setText(rollno);
        addressTxt.setText(address);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = fullnameTxt.getText().toString();
                String rollno = rollnoTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String address = addressTxt.getText().toString();

                Intent i = new Intent();
                i.putExtra("fullname", fullname);
                i.putExtra("rollno", rollno);
                i.putExtra("email", email);
                i.putExtra("address", address);
                i.putExtra("position", currPos);

                setResult(100, i);

                finish();
            }
        });
    }
}

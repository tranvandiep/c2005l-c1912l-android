package com.gokisoft.example;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailStudentActivity extends AppCompatActivity {
    TextView fullnameView, emailView, rollnoView, addressView;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_student);

        fullnameView = findViewById(R.id.ads_fullname);
        emailView = findViewById(R.id.ads_email);
        rollnoView = findViewById(R.id.ads_rollno);
        addressView = findViewById(R.id.ads_address);

        backBtn = findViewById(R.id.ads_back_btn);

        String fullname = getIntent().getStringExtra("fullname");
        String rollno = getIntent().getStringExtra("rollno");
        String email = getIntent().getStringExtra("email");
        String address = getIntent().getStringExtra("address");

        fullnameView.setText(fullname);
        emailView.setText(rollno);
        rollnoView.setText(email);
        addressView.setText(address);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

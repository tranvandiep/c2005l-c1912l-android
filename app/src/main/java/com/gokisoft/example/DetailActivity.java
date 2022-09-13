package com.gokisoft.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    TextView fullnameView, emailView, addressView;
    Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fullnameView = findViewById(R.id.ad_fullname);
        emailView = findViewById(R.id.ad_email);
        addressView = findViewById(R.id.ad_address);

        closeBtn = findViewById(R.id.ad_close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dong activity lai
                finish();
            }
        });

        Intent i = getIntent();
        String fullname = i.getStringExtra("fullname");
        String email = i.getStringExtra("email");
        String address = i.getStringExtra("address");

        fullnameView.setText(fullname);
        emailView.setText(email);
        addressView.setText(address);
    }
}

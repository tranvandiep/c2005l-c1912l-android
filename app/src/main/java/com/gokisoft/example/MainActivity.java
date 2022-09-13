package com.gokisoft.example;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText fullnameTxt, emailTxt, addressTxt;
    Button saveBtn, resetbtn, showBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullnameTxt = findViewById(R.id.am_fullname);
        emailTxt = findViewById(R.id.am_email);
        addressTxt = findViewById(R.id.am_address);
        saveBtn = findViewById(R.id.am_save_btn);
        resetbtn = findViewById(R.id.am_reset_btn);
        showBtn = findViewById(R.id.am_show_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Goi vao day
                String fullname = fullnameTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String address = addressTxt.getText().toString();

                //OKOKOK
                Toast.makeText(MainActivity.this, "Lay form du lieu", Toast.LENGTH_SHORT).show();
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Goi vao day
                fullnameTxt.setText("");
                emailTxt.setText("");
                addressTxt.setText("");

                Toast.makeText(MainActivity.this, "Xoa form du lieu", Toast.LENGTH_SHORT).show();
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);

                String fullname = fullnameTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String address = addressTxt.getText().toString();

                i.putExtra("fullname", fullname);
                i.putExtra("email", email);
                i.putExtra("address", address);

                startActivity(i);
            }
        });

        Log.d(MainActivity.class.getName(), "onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.class.getName(), "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.class.getName(), "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.class.getName(), "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.class.getName(), "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.class.getName(), "onDestroy()");
    }
}

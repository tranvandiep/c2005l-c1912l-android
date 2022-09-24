package com.gokisoft.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class NewNoteDetailActivity extends AppCompatActivity {
    TextView noidungView;
    CheckBox importantCb;

    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note_detail);

        noidungView = findViewById(R.id.annd_title);
        importantCb = findViewById(R.id.annd_important);

        backBtn = findViewById(R.id.annd_back);

        String noidung = getIntent().getStringExtra("noidung");
        int important = getIntent().getIntExtra("important", 0);

        noidungView.setText(noidung);
        importantCb.setChecked(important == 1);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
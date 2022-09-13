package com.gokisoft.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookEditorActivity extends AppCompatActivity {
    EditText bookNameTxt, authorNameTxt, nxbTxt, priceTxt;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_editor);

        bookNameTxt = findViewById(R.id.abe_bookname);
        authorNameTxt = findViewById(R.id.abe_authorname);
        nxbTxt = findViewById(R.id.abe_nxb);
        priceTxt = findViewById(R.id.abe_price);

        saveBtn = findViewById(R.id.abe_save_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookName = bookNameTxt.getText().toString();
                String authorName = authorNameTxt.getText().toString();
                String nxb = nxbTxt.getText().toString();
                String price = priceTxt.getText().toString();

                ///Gui du lieu ve BookActivity
                Intent i = new Intent();
                i.setAction(BookActivity.ACTION_ADD_BOOK);
                i.putExtra("bookName", bookName);
                i.putExtra("authorName", authorName);
                i.putExtra("nxb", nxb);
                i.putExtra("price", price);
                sendBroadcast(i);

                finish();
            }
        });
    }
}
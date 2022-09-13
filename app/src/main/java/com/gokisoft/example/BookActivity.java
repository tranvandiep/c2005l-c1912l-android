package com.gokisoft.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gokisoft.example.adapter.BookAdapter;
import com.gokisoft.example.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    public final static String ACTION_ADD_BOOK = "ACTION_ADD_BOOK";

    RecyclerView recyclerView;
    List<Book> dataList;
    BookAdapter adapter;

    Button addBtn;

    class MyBroadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Noi nhan du lieu dc gui ve
            String action = intent.getAction();

            switch (action) {
                case ACTION_ADD_BOOK:
                    String bookName = intent.getStringExtra("bookName");
                    String authorName = intent.getStringExtra("authorName");
                    String nxb = intent.getStringExtra("nxb");
                    String priceStr = intent.getStringExtra("price");
                    float price = Float.parseFloat(priceStr);

                    Book book = new Book(bookName, authorName, nxb, price);
                    dataList.add(book);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
    MyBroadReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        recyclerView = findViewById(R.id.ab_recycler_view);
        addBtn = findViewById(R.id.ab_add_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BookActivity.this, BookEditorActivity.class);
                startActivity(i);
            }
        });

        dataList = new ArrayList<>();
        dataList.add(new Book("Lap Trinh C", "Tran Van A", "Ha Noi", 10000));
        dataList.add(new Book("HTML/CSS/JS", "Tran Van B", "Ha Noi", 20000));
        dataList.add(new Book("SQL Server", "Tran Van C", "Ha Noi", 30000));

        adapter = new BookAdapter(this, dataList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //Dang ky lang nghe action gui
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ADD_BOOK);

        receiver = new MyBroadReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            unregisterReceiver(receiver);
        } catch(Exception e) {}
    }
}
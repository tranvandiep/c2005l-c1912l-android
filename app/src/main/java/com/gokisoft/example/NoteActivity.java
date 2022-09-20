package com.gokisoft.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.gokisoft.example.adapter.NoteAdapter;
import com.gokisoft.example.models.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    List<Note> dataList = new ArrayList<>();

    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mRecyclerView = findViewById(R.id.an_listview);

//        dataList.add(new Note(1, "Ghi chu 1", "https://gokisoft.com/uploads/stores/49/2021/10/bai-tap-android.jpg", "Noi dung 1", "2022-09-15 06:00:00", "2022-09-15 06:00:00"));
//        dataList.add(new Note(2, "Ghi chu 2", "https://gokisoft.com/uploads/stores/49/2021/10/bai-tap-android.jpg", "Noi dung 2", "2022-09-15 06:00:00", "2022-09-15 06:00:00"));
//        dataList.add(new Note(3, "Ghi chu 3", "https://gokisoft.com/uploads/stores/49/2021/10/bai-tap-android.jpg", "Noi dung 3", "2022-09-15 06:00:00", "2022-09-15 06:00:00"));

        adapter = new NoteAdapter(this, dataList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(adapter);

        loadData();

    }

    void loadData() {
        //Tai dc tai nguyen tren mang
        Request request = new Request.Builder()
                .url("https://gokisoft.com/api/fake/49//note/list")
                .build();
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //code => tai thanh cong
                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Note>>() {}.getType();
                            dataList = gson.fromJson(json, listType);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.setDataList(dataList);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
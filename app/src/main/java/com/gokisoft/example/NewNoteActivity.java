package com.gokisoft.example;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.gokisoft.example.adapter.NewNoteAdapter;
import com.gokisoft.example.database.DBHelper;
import com.gokisoft.example.database.NewNoteEntity;
import com.gokisoft.example.models.NewNote;

import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {
    ListView mListView;
    Cursor currCursor;
    NewNoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        DBHelper.getInstance(this);

//        NewNoteEntity.insert(new NewNote("Vi du 1", true, new Date()));
//        NewNoteEntity.insert(new NewNote("Vi du 2", false, new Date()));
//        NewNoteEntity.insert(new NewNote("Vi du 3", false, new Date()));
//        NewNoteEntity.insert(new NewNote("Vi du 4", true, new Date()));
//        NewNoteEntity.insert(new NewNote("Vi du 5", true, new Date()));

        mListView = findViewById(R.id.ann_listview);
        currCursor = NewNoteEntity.getCursor();

        adapter = new NewNoteAdapter(this, currCursor);
        mListView.setAdapter(adapter);
    }
}
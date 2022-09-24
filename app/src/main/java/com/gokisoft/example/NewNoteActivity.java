package com.gokisoft.example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

        registerForContextMenu(mListView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currCursor.moveToPosition(position);
                NewNote currentNote = new NewNote();
                currentNote.setData(currCursor);

                Intent i = new Intent(NewNoteActivity.this, NewNoteDetailActivity.class);
                i.putExtra("noidung", currentNote.getNoidung());
                i.putExtra("important", currentNote.isQuantrong()?1:0);

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_add_note:
                showNoteDialog(null);
                return true;
            case R.id.menu_exit_app:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        currCursor.moveToPosition(info.position);
        NewNote currentNote = new NewNote();
        currentNote.setData(currCursor);

        switch (item.getItemId()) {
            case R.id.menu_edit_note:
                showNoteDialog(currentNote);
                return true;
            case R.id.menu_delete_note:
                NewNoteEntity.delete(currentNote.get_id());
                updateCursor();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showNoteDialog(NewNote note) {
        View v = getLayoutInflater().inflate(R.layout.new_note_dialog, null);

        TextView headerView = v.findViewById(R.id.nnd_header);
        EditText titleView = v.findViewById(R.id.nnd_title);
        Button cancelBtn = v.findViewById(R.id.nnd_cancel);
        Button commitBtn = v.findViewById(R.id.nnd_commit);
        CheckBox importantCb = v.findViewById(R.id.nnd_important);

        if(note != null) {
            headerView.setBackgroundColor(Color.MAGENTA);
            headerView.setText("Edit Note");

            titleView.setText(note.getNoidung());
            importantCb.setChecked(note.isQuantrong());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v);
        Dialog dialog = builder.show();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                String title = titleView.getText().toString();
                boolean important = importantCb.isChecked();

                if(note != null) {
                    note.setNoidung(title);
                    note.setQuantrong(important);

                    NewNoteEntity.update(note);
                } else {
                    NewNote newNote = new NewNote(title, important, new Date());

                    NewNoteEntity.insert(newNote);
                }


                updateCursor();
            }
        });
    }

    private void updateCursor() {
        currCursor = NewNoteEntity.getCursor();
        adapter.changeCursor(currCursor);

        adapter.notifyDataSetChanged();
    }
}
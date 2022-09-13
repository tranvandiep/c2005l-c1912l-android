package com.gokisoft.example;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.gokisoft.example.adapter.NewStudentAdapter;
import com.gokisoft.example.database.DBHelper;
import com.gokisoft.example.database.StudentEntity;
import com.gokisoft.example.models.Student;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NewStudentActivity extends AppCompatActivity {
    ListView listView;
    NewStudentAdapter adapter;
    Cursor currentCursor;
    Student currentStudent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        DBHelper.getInstance(this);

        listView = findViewById(R.id.ans_listview);

        currentCursor = StudentEntity.getCursor();
        adapter = new NewStudentAdapter(this, currentCursor);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentCursor.moveToPosition(position);
                currentStudent = new Student();
                currentStudent.setData(currentCursor);

                Intent i = new Intent(NewStudentActivity.this, DetailStudentActivity.class);
                i.putExtra("fullname", currentStudent.getFullname());
                i.putExtra("email", currentStudent.getEmail());
                i.putExtra("rollno", currentStudent.getRollno());
                i.putExtra("address", currentStudent.getAddress());

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_add_student:
                showStudentEditor(null);
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
        inflater.inflate(R.menu.student_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int currentPos = info.position;
        currentCursor.moveToPosition(currentPos);
        currentStudent = new Student();
        currentStudent.setData(currentCursor);

        switch (item.getItemId()) {
            case R.id.menu_edit_student:
                showStudentEditor(currentStudent);
                return true;
            case R.id.menu_delete_student:
                showConfirmDialog();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showStudentEditor(final Student currStudent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.activity_editor_student, null);

        builder.setView(view);

        final EditText fullnameTxt = view.findViewById(R.id.aes_fullname);
        final EditText rollnoTxt = view.findViewById(R.id.aes_rollno);
        final EditText emailTxt = view.findViewById(R.id.aes_email);
        final EditText addressTxt = view.findViewById(R.id.aes_address);

        if(currStudent != null) {
            fullnameTxt.setText(currStudent.getFullname());
            rollnoTxt.setText(currStudent.getRollno());
            emailTxt.setText(currStudent.getEmail());
            addressTxt.setText(currStudent.getAddress());
        }

        Button cancelBtn = view.findViewById(R.id.aes_cancel_btn);
        Button saveBtn = view.findViewById(R.id.aes_save_btn);

        final AlertDialog dialog = builder.show();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = fullnameTxt.getText().toString();
                String rollno = rollnoTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String address = addressTxt.getText().toString();

                if(currStudent == null) {
                    Student std = new Student(rollno, fullname, email, address);

                    StudentEntity.insert(std);
                } else {
                    currStudent.setAddress(address);
                    currStudent.setRollno(rollno);
                    currStudent.setEmail(email);
                    currStudent.setFullname(fullname);

                    StudentEntity.update(currStudent);
                }

                updateCursor();

                dialog.dismiss();
            }
        });
    }

    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this student")
                .setMessage("Are you sure to delete this student?")
                .setPositiveButton("Confirm Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StudentEntity.delete(currentStudent.get_id());

                        updateCursor();

                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void updateCursor() {
        currentCursor = StudentEntity.getCursor();
        adapter.changeCursor(currentCursor);
        adapter.notifyDataSetChanged();
        currentStudent = null;
    }
}

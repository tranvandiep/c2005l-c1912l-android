package com.gokisoft.example;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gokisoft.example.adapter.StudentAdapter;
import com.gokisoft.example.database.DBHelper;
import com.gokisoft.example.database.StudentEntity;
import com.gokisoft.example.models.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class StudentActivity extends AppCompatActivity {
    ListView listView;
    List<Student> dataList;
    StudentAdapter adapter;
    int currentPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //Khoi tao database & tables
        DBHelper.getInstance(this);

        listView = findViewById(R.id.as_listview);

        //Fake data
        dataList = StudentEntity.list();
//        dataList = new ArrayList<>();
//        dataList.add(new Student("R001", "Tran Van A", "a@gmail.com", "Ha Noi"));
//        dataList.add(new Student("R002", "Tran Van B", "b@gmail.com", "Ha Noi"));
//        dataList.add(new Student("R003", "Tran Van C", "c@gmail.com", "Ha Noi"));
//        Fake data -> tu chen du lieu vao database
//        for (int i=0;i<5;i++) {
//            Student std = new Student("R00"+i, "Ten " + i, i+"@gmail.com", "Ha Noi");
//            StudentEntity.insert(std);
//        }

        adapter = new StudentAdapter(dataList, this);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        readData();
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
                Intent i = new Intent(this, EditorStudentActivity.class);
                startActivityForResult(i, 100);

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

        currentPos = info.position;

        switch (item.getItemId()) {
            case R.id.menu_edit_student:
                Student std = dataList.get(currentPos);
                Intent i = new Intent(this, EditorStudentActivity.class);
                i.putExtra("fullname", std.getFullname());
                i.putExtra("rollno", std.getRollno());
                i.putExtra("email", std.getEmail());
                i.putExtra("address", std.getAddress());
                i.putExtra("position", currentPos);

                currentPos = -1;

                startActivityForResult(i, 100);
                return true;
            case R.id.menu_delete_student:
                showConfirmDialog();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this student")
                .setMessage("Are you sure to delete this student?")
                .setPositiveButton("Confirm Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataList.remove(currentPos);
                currentPos = -1;

                adapter.notifyDataSetChanged();

                dialog.dismiss();

                saveData();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 100:
                String fullname = data.getStringExtra("fullname");
                String rollno = data.getStringExtra("rollno");
                String email = data.getStringExtra("email");
                String address = data.getStringExtra("address");
                currentPos = data.getIntExtra("position", -1);

                Student std = new Student(rollno, fullname, email, address);

                if(currentPos >= 0) {
                    dataList.set(currentPos, std);
                } else {
                    dataList.add(std);
                }

                adapter.notifyDataSetChanged();

                saveData();
                break;
        }
    }

    void saveData() {
        //Luu du lieu
        //saveSharedPreferences();
//        saveFile();
    }

    void readData() {
//        readSharedPreferences();
//        readFile();
    }

    //Shared Preferences - START
    void saveSharedPreferences() {
        //Mo connection -> myapp.xml
        SharedPreferences sharedPreferences = getSharedPreferences("myapp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //text -> expect: dataList (Array) -> save, solution: dataList (Array) -> text (json string)
        Gson gson = new Gson();
        String json = gson.toJson(dataList);

        //Save -> SharedPreferences
        editor.putString("dataList", json);

        //Close
        editor.commit();
    }

    void readSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("myapp", MODE_PRIVATE);

        String json = sharedPreferences.getString("dataList", "");
        if(!json.isEmpty()) {
            //convert json string -> array (object) in Java
            Type type = new TypeToken<List<Student>>(){}.getType();
            Gson gson = new Gson();

            List<Student> list = gson.fromJson(json, type);
            for(Student std : list) {
                dataList.add(std);
            }

            adapter.notifyDataSetChanged();
        }
    }

    //Shared Preferences - END

    //Storage
    void saveFile() {
        //Lay duong dan luu file pathFile
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = openFileOutput("student.dat", MODE_PRIVATE);

            //Luu vao File (Internal Storage & External Storage
            oos = new ObjectOutputStream(fos);
            oos.writeObject(dataList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void readFile() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = openFileInput("student.dat");
            ois = new ObjectInputStream(fis);

            List<Student> list = (List<Student>) ois.readObject();

            for(Student std : list) {
                dataList.add(std);
            }

            adapter.notifyDataSetChanged();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}

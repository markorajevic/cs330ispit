package com.metropolitan.cs330juna.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.cs330juna.MainActivity;
import com.metropolitan.cs330juna.R;
import com.metropolitan.cs330juna.adapters.DBAdapter;
import com.metropolitan.cs330juna.adapters.EditStudentAdapter;
import com.metropolitan.cs330juna.entities.Student;

import java.util.List;

public class EditStudentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DBAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        db = new DBAdapter(this);
        recyclerView = findViewById(R.id.recyclerview_tasks2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getItems();
    }

    private void getItems() {
        class GetItems extends AsyncTask<Void, Void, List<Student>> {

            @Override
            protected List<Student> doInBackground(Void... voids) {
                db.open();
                List<Student> items = db.getAllStudents();
                return items;
            }

            @Override
            protected void onPostExecute(List<Student> tasks) {
                super.onPostExecute(tasks);
                EditStudentAdapter adapter = new EditStudentAdapter(getApplicationContext(), tasks);
                recyclerView.setAdapter(adapter);
            }
        }
        GetItems gt = new GetItems();
        gt.execute();
    }
}
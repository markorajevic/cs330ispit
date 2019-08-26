package com.metropolitan.cs330juna.activities;

import android.os.AsyncTask;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.cs330juna.R;
import com.metropolitan.cs330juna.adapters.DBAdapter;
import com.metropolitan.cs330juna.adapters.StudentAdapter;
import com.metropolitan.cs330juna.entities.Student;

import java.util.List;

public class DeleteStudentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBAdapter(this);
        setContentView(R.layout.activity_delete_student);
        recyclerView = findViewById(R.id.recyclerview_tasks);
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
                StudentAdapter adapter = new StudentAdapter(getApplicationContext(), tasks);
                recyclerView.setAdapter(adapter);
            }
        }
        GetItems gt = new GetItems();
        gt.execute();
    }
}

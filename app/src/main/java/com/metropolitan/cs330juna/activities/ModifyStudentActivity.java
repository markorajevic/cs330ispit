package com.metropolitan.cs330juna.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.metropolitan.cs330juna.MainActivity;
import com.metropolitan.cs330juna.R;
import com.metropolitan.cs330juna.adapters.DBAdapter;
import com.metropolitan.cs330juna.entities.Student;

public class ModifyStudentActivity extends AppCompatActivity {

    private Student toEdit;
    EditText ime,prezime;
    Button azuriraj;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_student);

        final String id = getIntent().getStringExtra("indeks");
        db = new DBAdapter(this);
        ime = findViewById(R.id.firstName);
        prezime = findViewById(R.id.lastName);
        azuriraj = findViewById(R.id.editStudent);

        getStudentById(Long.parseLong(id));

        azuriraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem(new Student(Long.parseLong(id), ime.getText().toString(),prezime.getText().toString()));
            }
        });
    }

    private void saveItem(final Student student) {

        class SaveItem extends AsyncTask<Void, Void, Void> {

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                db.open();
                toEdit.setFirstName(ime.getText().toString());
                toEdit.setLastName(prezime.getText().toString());
                db.updateStudent(toEdit.getIndeks(), toEdit.getFirstName(), toEdit.getLastName());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }

        SaveItem st = new SaveItem();
        st.execute();
    }

    private void getStudentById(final Long indeks) {

        class GetItems extends AsyncTask<Void, Void, Student> {

            @Override
            protected Student doInBackground(Void... voids) {
                db.open();
                Student item = db.getStudent(indeks);
                return item;
            }

            @Override
            protected void onPostExecute(Student item) {
                db.open();
                super.onPostExecute(item);
                toEdit = item;
                ime.setText(toEdit.getFirstName());
                prezime.setText(toEdit.getLastName());
            }
        }
        GetItems gt = new GetItems();
        gt.execute();
    }
}

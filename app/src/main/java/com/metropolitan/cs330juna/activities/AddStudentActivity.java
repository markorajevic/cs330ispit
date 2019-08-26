package com.metropolitan.cs330juna.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.metropolitan.cs330juna.R;
import com.metropolitan.cs330juna.adapters.DBAdapter;
import com.metropolitan.cs330juna.entities.Student;

public class AddStudentActivity extends AppCompatActivity {
    EditText ime,prezime, indeks;
    Button dodaj;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        db = new DBAdapter(this);
        ime = findViewById(R.id.firstName);
        indeks = findViewById(R.id.indeks);
        prezime = findViewById(R.id.lastName);
        dodaj = findViewById(R.id.dodaj);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student(Long.parseLong(indeks.getText().toString()),ime.getText().toString(),prezime.getText().toString());
                saveItem(student);
            }
        });

    }

    private void saveItem(final Student student) {
        db.open();
        long id = db.insertStudent(
                student.getIndeks(),
                student.getFirstName(),
                student.getLastName()
                );
        if(id != -1){
            Toast.makeText(getApplicationContext(),
                    "USPEH", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(getApplicationContext(), "Greska pirlikom dodavanja!", Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}

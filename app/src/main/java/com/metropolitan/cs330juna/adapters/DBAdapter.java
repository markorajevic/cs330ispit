package com.metropolitan.cs330juna.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.metropolitan.cs330juna.entities.Student;


public class DBAdapter {
    static final String KEY_INDEX = "indexNum";
    static final String KEY_FIRST = "firstName";
    static final String KEY_LAST = "lastName";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "fit";
    static final String DATABASE_TABLE = "student";
    static final int DATABASE_VERSION = 3;

    static final String DATABASE_CREATE = "create table student " +
            "(indexNum integer primary key, firstName text not null, " +
            "lastName text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Updating database version from " + oldVersion + " to version " +
                    newVersion + ", that will destroy the existing data.");
            db.execSQL("DROP TABLE IF EXISTS student");
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertStudent(Long indexNum, String firstName, String lastName) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_INDEX, indexNum);
        initialValues.put(KEY_FIRST, firstName);
        initialValues.put(KEY_LAST, lastName);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteStudent(long index) {
        return db.delete(DATABASE_TABLE, KEY_INDEX + "=" + index, null) > 0;
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<Student>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_INDEX, KEY_FIRST, KEY_LAST},
                null, null, null, null, null);
        while (cursor.moveToNext()){
            String firstName = cursor.getString(cursor.getColumnIndex(KEY_FIRST));
            String lastName = cursor.getString(cursor.getColumnIndex(KEY_LAST));
            Long indeks = Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_INDEX)));
            list.add(new Student(indeks, firstName, lastName));
        }
        return list;
    }

    public Student getStudent(long index) throws SQLException {
        Student item = new Student();
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]{KEY_INDEX, KEY_FIRST, KEY_LAST},
                        KEY_INDEX + "=" + index, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            String firstName = mCursor.getString(mCursor.getColumnIndex(KEY_FIRST));
            String lastName = mCursor.getString(mCursor.getColumnIndex(KEY_LAST));
            Long indeks = Long.parseLong(mCursor.getString(mCursor.getColumnIndex(KEY_INDEX)));
            item = new Student(indeks, firstName, lastName);
        }


        return item;
    }

    public boolean updateStudent(long index, String firstName, String lastName) {
        ContentValues args = new ContentValues();
        args.put(KEY_INDEX, index);
        args.put(KEY_FIRST, firstName);
        args.put(KEY_LAST, lastName);

        return db.update(DATABASE_TABLE, args, KEY_INDEX + "=" + index, null) > 0;
    }

}
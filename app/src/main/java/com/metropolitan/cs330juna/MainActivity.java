package com.metropolitan.cs330juna;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.metropolitan.cs330juna.activities.AddStudentActivity;
import com.metropolitan.cs330juna.activities.DeleteStudentActivity;
import com.metropolitan.cs330juna.activities.EditStudentActivity;
import com.metropolitan.cs330juna.activities.FamActivity;
import com.metropolitan.cs330juna.activities.FduActivity;
import com.metropolitan.cs330juna.activities.FitActivity;
import com.metropolitan.cs330juna.activities.MapsActivity;
import com.metropolitan.cs330juna.adapters.DBAdapter;
import com.metropolitan.cs330juna.fragments.Fragment1;
import com.metropolitan.cs330juna.fragments.Fragment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment1 fragment1 = new Fragment1();

        fragmentTransaction.replace(R.id.content, fragment1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        try {
            String destPath = "/data/data/" + getPackageName() + "/databases";
            File f = new File(destPath);
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();
                CopyDB(getBaseContext().getAssets().open("fit"),
                        new FileOutputStream(destPath + "/MyDB"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Fragment1 fragment1 = new Fragment1();
//        Fragment2 fragment2 = new Fragment2();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_fragment1) {
//            fragmentTransaction.replace(R.id.content, fragment1);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            return true;
//        }
//        if (id == R.id.action_fragment2) {
//            fragmentTransaction.replace(R.id.content, fragment2);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            return true;
//        }
        switch (item.getItemId()) {
            case R.id.menuObrisi:
                startActivity(new Intent(getApplicationContext(), DeleteStudentActivity.class));
                return true;
            case R.id.menuAzuriraj:
                startActivity(new Intent(getApplicationContext(), EditStudentActivity.class));
                return true;
            case R.id.menuDodaj:
                startActivity(new Intent(getApplicationContext(), AddStudentActivity.class));
                return true;
            case R.id.menuPozovi:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+381656451048"));
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {
                    try {
                        startActivity(intent);
                    } catch(SecurityException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            case R.id.menuLokacija:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                return true;
            case R.id.menuFit:
                startActivity(new Intent(getApplicationContext(), FitActivity.class));
                return true;
            case R.id.menuFam:
                startActivity(new Intent(getApplicationContext(), FamActivity.class));
                return true;
            case R.id.menuFdu:
                startActivity(new Intent(getApplicationContext(), FduActivity.class));
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}

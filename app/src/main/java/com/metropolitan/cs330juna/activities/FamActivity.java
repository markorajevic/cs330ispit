package com.metropolitan.cs330juna.activities;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.metropolitan.cs330juna.R;

public class FamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fam);

        WebView myWebView = findViewById(R.id.webviewFam);
        myWebView.loadUrl("https://www.metropolitan.ac.rs/osnovne-studije/fakultet-za-menadzment/");
    }
}

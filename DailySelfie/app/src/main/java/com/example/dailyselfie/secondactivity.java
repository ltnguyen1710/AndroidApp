package com.example.dailyselfie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class secondactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.MESSAGE);
        System.out.println(message);
        Uri uri = Uri.parse(message);
        ImageView imgV = findViewById(R.id.BigImage);
        imgV.setImageURI(uri);

    }
}
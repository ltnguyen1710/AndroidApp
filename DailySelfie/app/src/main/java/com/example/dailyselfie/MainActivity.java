package com.example.dailyselfie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a arraylist of the type NumbersView
        final ArrayList<Image> arrayList = new ArrayList<Image>();
         arrayList.add(new Image(R.drawable.ic_launcher_background));
        arrayList.add(new Image(R.drawable.ic_launcher_background));
        arrayList.add(new Image(R.drawable.ic_launcher_background));
        arrayList.add(new Image(R.drawable.ic_launcher_background));
        arrayList.add(new Image(R.drawable.ic_launcher_background));
        arrayList.add(new Image(R.drawable.ic_launcher_background));
        arrayList.add(new Image(R.drawable.ic_launcher_background));
        arrayList.add(new Image(R.drawable.ic_launcher_background));

         // the context and arrayList created above
         ImageAdapter numbersArrayAdapter = new ImageAdapter(this, arrayList);

         // create the instance of the ListView to set the numbersViewAdapter
         ListView numbersListView = findViewById(R.id.listView);

         // set the numbersViewAdapter for ListView
         numbersListView.setAdapter(numbersArrayAdapter);

         camera = (ImageButton) findViewById(R.id.camera);
         camera.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dispatchTakePictureIntent();
             }});
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }
}
package com.example.dailyselfie;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //Lớp Images tự định nghĩa
    class Images {
        private final Uri uri;
        private final String name;

        public Images(Uri uri, String name) {
            this.uri = uri;
            this.name = name;
        }
    }
    private ImageButton camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayImgList();
        //Thêm nút Camera và sự kiện cho nút Camera
        camera = (ImageButton) findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }});

    }
    private void displayImgList(){
        // Kiểm tra quyền và thông báo
        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            getAllImage();
        }
        //Lấy tất cả hình từ thư mục /DCIM/Camera
        ArrayList<Images>imgList = getAllImage();
        //Thêm các hình vào giao diện
        ArrayList<ImagesView>imgs=new ArrayList<>();
        for (Images img: imgList
        ) {
            imgs.add(new ImagesView(img.uri, img.name));
        }
        //Tạo Adapter
        ImgAdapter imgAdapter = new ImgAdapter(this,imgs);
        //Tạo ListView
        ListView imageListview = findViewById(R.id.listView);
        //Thêm Adapter cho ListView
        imageListview.setAdapter(imgAdapter);
    }
    //Hàm cho phép dùng Camera và thêm hình vào DCIM sau khi chụp và chấp nhận hình bằng fileprovider
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider"
                ,photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                displayImgList();
            }
        }
    }
    //Hàm tạo đường dẫn cho file hình được gọi lại ở dispatchTakePictureIntent()
    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //Hàm lấy tất cả hình trong /DCIM hoặc /Picture
    public  ArrayList<Images> getAllImage() {
        //Mảng Images mà mình định nghĩa
        ArrayList<Images> imgList = new ArrayList<Images>();
        //Set up các truy vấn
        //Lấy đường dẫn Uri của /DCIM hoặc /Picture, chỗ này tương tự From table trong SQL
        Uri collection= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        //Chọn nhũng cột cần lấy -- tương tự Select collumn trong SQL
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
        };
        //Truy vấn -- tương tự Where trong SQL
        String selection = MediaStore.Images.Media.DISPLAY_NAME +">=?";
        //Cái này có thể để rỗng
        String[] selectionArgs = {""};
        //Lấy ra theo thứ tự như thế nào
        String sortOrder = MediaStore.Images.Media.DISPLAY_NAME + " DESC";
        //Thực hiện truy vấn
        try (Cursor cursor = getApplicationContext().getContentResolver().query(
                collection,
                projection,
                selection,
                selectionArgs,
                sortOrder
        )) {
            // Cache column indices.
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            while (cursor.moveToNext()) {
                // Get values of columns for a given Image.
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                // Stores column values and the contentUri in a local object
                // that represents the media file.
                imgList.add(new Images(contentUri, name));
            }
        }
        return imgList;
    }
    //Hàm kiểm tra quyền
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    //Hàm hiện thông báo cho phép truy cập hay không
    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
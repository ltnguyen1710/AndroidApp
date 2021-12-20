package com.example.dailyselfie;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.text.Transliterator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.io.File;
import java.io.FileOutputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ImageButton button;
    private Button buttonMinute;
    private Button selectTime;
    private Button buttonsetMinute;
    private EditText editTime;

    private TextView textTime;

    private Button cancelBtn;
    private Button returnBtn;
    private Button returnBtn2;

     Spinner spinner;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    String choose_type_time;


    int REQUEST_IMAGE_CAPTURE = 1;
    int REQUEST_CODE_PERMISSION_ALL = 101;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public void setPendingIntent(PendingIntent pendingIntent){
        this.pendingIntent=pendingIntent;
    }

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
                ActivityCompat.requestPermissions(MainActivity.this,
                        PERMISSIONS, REQUEST_CODE_PERMISSION_ALL);
            }});
        button = (ImageButton) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm();
            }
        });


    }

    private static SimpleDateFormat DBFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());


    private void setAlarm_every(Editable minute,String Timetype) {
        if(Timetype.equals("Minutes")) {
            int TimesetofMinutes =Integer.parseInt(String.valueOf(minute));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            Date date = null;
            try {
                date = sdf.parse(currentDateandTime);
            } catch (Exception e) {
                System.out.println(e);
            }
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE,TimesetofMinutes);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60, pendingIntent);
            Toast.makeText(this, "Alarm set Minutes "+TimesetofMinutes+" Successfully", Toast.LENGTH_SHORT).show();
        }else{
            int TimesetofMinutes =Integer.parseInt(String.valueOf(minute));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            Date date = null;
            try {
                date = sdf.parse(currentDateandTime);
            } catch (Exception e) {
                System.out.println(e);
            }
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR,TimesetofMinutes);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60, pendingIntent);
            Toast.makeText(this, "Alarm set Hours" + TimesetofMinutes*60 + " Successfully", Toast.LENGTH_SHORT).show();

        }

    }


    private void alarm(){
        createNotificationChannel();
        setContentView(R.layout.alarm);

        buttonMinute = (Button) findViewById(R.id.selectTimeofMinuteBtn);
        buttonMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmtoMinute();
            }
        });

        //Nút cancel
        cancelBtn = (Button) findViewById(R.id.buttonCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textTime = (TextView) findViewById(R.id.textViewTime);

                if(pendingIntent!= null ) {
                    alarmManager.cancel(pendingIntent);
                    textTime.setText("Canceled");

                }else{
                    Toast.makeText(v.getContext(), "Can't cancel", Toast.LENGTH_SHORT).show();
                }

            }
        });
        returnBtn2 = (Button) findViewById(R.id.buttonReturn2);
        returnBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                displayImgList();
                //Thêm nút Camera và sự kiện cho nút Camera
                camera = (ImageButton) findViewById(R.id.camera);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                PERMISSIONS, REQUEST_CODE_PERMISSION_ALL);
                    }});
                button = (ImageButton) findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alarm();
                    }
                });
            }
        });
    }


    private void AlarmtoMinute() {
        createNotificationChannel();
        setContentView(R.layout.timeofminute);

        editTime = (EditText) findViewById(R.id.editTextTime);
        spinner = (Spinner) findViewById(R.id.spinnerOption);
        ArrayList<String> arrayTime = new ArrayList<String>();
        arrayTime.add("Minutes");
        arrayTime.add("Hour");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayTime);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:

                        Toast.makeText(MainActivity.this , arrayTime.get(position), Toast.LENGTH_SHORT).show();
                        choose_type_time ="Minutes";
                        break;
                    case 1:

                        Toast.makeText(MainActivity.this , arrayTime.get(position), Toast.LENGTH_SHORT).show();
                        choose_type_time ="Hour";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonsetMinute = (Button) findViewById(R.id.setAlarm2Btn);
        //Nút confirm
        buttonsetMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTime.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this , "Please enter your option", Toast.LENGTH_SHORT).show();
                }
                else{
                    setAlarm_every(editTime.getText(),choose_type_time);
                    alarm();
                    textTime = (TextView) findViewById(R.id.textViewTime);
                    textTime.setText(calendar.getTime().toString());
                }
            }
        });
        //Nút return
        returnBtn = (Button)  findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alarm();

            }
        });

    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "foxandroidReminderChannel";
            String descripttion = "Channel for alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid", name, importance);
            channel.setDescription(descripttion);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
        imageListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImagesView currentNumberPosition = imgAdapter.getItem(position);
                sendImage(currentNumberPosition.getUri());

            }
        });
    }
    public static final String MESSAGE = "com.example.dailyselfie.MESSAGE";
    public void sendImage(Uri uri){
        Intent intent = new Intent(this, secondactivity.class);
        intent.putExtra(MESSAGE,uri.toString());
        startActivity(intent);

    }

    //Hàm cho phép dùng Camera và thêm hình vào DCIM sau khi chụp và chấp nhận hình bằng fileprovider
    private void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/DCIM/Camera");
        //myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "IMG_"+ timeStamp +".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //imghinh.setImageBitmap(bitmap);
            //saveTempBitmap(bitmap);
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "yourTitle" , "yourDescription");
            //saveImage(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
        displayImgList();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_PERMISSION_ALL && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
        }else{
            Toast.makeText(this, "Bạn không cho phép đủ quyền cho ứng dụng!", Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                //System.out.println(contentUri);
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
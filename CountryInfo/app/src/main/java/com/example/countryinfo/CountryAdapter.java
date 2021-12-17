package com.example.countryinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<Country> {
    public CountryAdapter(@NonNull Context context, ArrayList<Country> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.itemoflistview, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Country currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView numbersImage = currentItemView.findViewById(R.id.imageView2);
        assert currentNumberPosition != null;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        URL url = null;
        try {
            url = new URL(currentNumberPosition.getHinhquockiURL());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            numbersImage.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.textView4);
        textView1.setText("Tên nước: "+currentNumberPosition.getTennuoc());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.textView5);
        textView2.setText("Dân số: "+currentNumberPosition.getDanso()+" triệu người");

        TextView textView3 = currentItemView.findViewById(R.id.textView6);
        textView3.setText("Diện tích: "+currentNumberPosition.getDientich()+" km vuông");


        // then return the recyclable view
        return currentItemView;
    }
}

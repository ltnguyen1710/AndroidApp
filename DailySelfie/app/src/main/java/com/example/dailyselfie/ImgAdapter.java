package com.example.dailyselfie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ImgAdapter extends ArrayAdapter<ImagesView> {
    // invoke the suitable constructor of the ArrayAdapter class
    public ImgAdapter(@NonNull Context context, ArrayList<ImagesView> arrayList) {

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
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        ImagesView currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView numbersImage = currentItemView.findViewById(R.id.imageView3);
        numbersImage.setImageURI(currentNumberPosition.getUri());
        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        textView2.setText(currentNumberPosition.getImgText());

        // then return the recyclable view
        return currentItemView;
    }
}

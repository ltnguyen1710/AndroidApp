package com.example.dailyselfie;

import android.net.Uri;


public class ImagesView {
    // TextView2
    private String ImgText;
    private Uri uri;
    // create constructor to set the values for all the parameters of the each single view
    public ImagesView(Uri uri_id,String txt) {
        uri=uri_id;
        ImgText=txt;
    }

    //getter method for ImgText
    public String getImgText() {
        return ImgText;
    }

    public Uri getUri() {
        return uri;
    }
}

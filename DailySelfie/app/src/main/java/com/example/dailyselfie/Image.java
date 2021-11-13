package com.example.dailyselfie;

public class Image {
    // the resource ID for the imageView
    private int ivNumbersImageId;

    // create constructor to set the values for all the parameters of the each single view
    public Image(int NumbersImageId) {
        ivNumbersImageId = NumbersImageId;
    }

    // getter method for returning the ID of the imageview
    public int getNumbersImageId() {
        return ivNumbersImageId;
    }

}

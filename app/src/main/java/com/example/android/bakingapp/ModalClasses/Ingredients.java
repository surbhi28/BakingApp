package com.example.android.bakingapp.ModalClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredients implements Parcelable {

    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredients(Parcel source){

        quantity = source.readFloat();
        measure = source.readString();
        ingredient = source.readString();
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel parcel) {
            return new Ingredients(parcel);
        }

        @Override
        public Ingredients[] newArray(int i) {
            return new Ingredients[i];
        }
    };


    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeFloat(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);

    }
}

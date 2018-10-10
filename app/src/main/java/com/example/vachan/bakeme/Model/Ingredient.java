package com.example.vachan.bakeme.Model;

/*
 {
        "quantity": 2,
        "measure": "CUP",
        "ingredient": "Graham Cracker crumbs"
      }

 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {
    @SerializedName("quantity")
    @Expose
    private double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public static final Parcelable.Creator CREATOR = new
            Parcelable.Creator(){
                public Ingredient createFromParcel(Parcel in){
                    return new Ingredient(in);
                }

                public Ingredient[] newArray(int size){
                    return new Ingredient[size];
                }
            };

    public Ingredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getIngredientInfo(){
        return "\u2022" + " " + getIngredient() + " " + "(" + getQuantity() + " " + getMeasure() + ")";
    }

    public Ingredient(Parcel in){
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
       dest.writeDouble(this.quantity);
       dest.writeString(this.measure);
       dest.writeString(this.ingredient);
    }

}

package com.example.android.bakingapp.ModalClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {

    private int id;
    private String name;
    private List<Ingredients> ingredients ;
    private List<Steps> steps;
    private int servings;


    public Recipe(Parcel source){
        id = source.readInt();
        name = source.readString();
        servings = source.readInt();
        //this.ingredients = new ArrayList<Ingredients>();
        //source.readList(this.ingredients,Ingredients.class.getClassLoader());
        //this.steps = new ArrayList<Steps>();
        //source.readList(this.steps,Steps.class.getClassLoader());

    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>(){

        @Override
        public Recipe createFromParcel(Parcel parcel){
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i){
            return new Recipe[i];
        }

    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(servings);
        //parcel.writeList(ingredients);
        //parcel.writeList(steps);



    }
}

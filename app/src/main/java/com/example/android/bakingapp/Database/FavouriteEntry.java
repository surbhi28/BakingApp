package com.example.android.bakingapp.Database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favourite")
public class FavouriteEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "recipe_id")
    private int recipeId;

    @ColumnInfo(name = "is_fav")
    private String isFav;

    @Ignore
    public FavouriteEntry(String userId, int recipeId, String isFav) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.isFav = isFav;

    }

    public FavouriteEntry(int id, String userId, int recipeId, String isFav) {
        this.id = id;
        this.userId = userId;
        this.recipeId = recipeId;
        this.isFav = isFav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRecipeId() {
        // if(this.recipeId == null)return 0;
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }
}

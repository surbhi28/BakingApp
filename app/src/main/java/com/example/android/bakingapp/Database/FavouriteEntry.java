package com.example.android.bakingapp.Database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favourite")
public class FavouriteEntry implements Parcelable {

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

    public static final Creator<FavouriteEntry> CREATOR = new Creator<FavouriteEntry>() {
        @Override
        public FavouriteEntry createFromParcel(Parcel parcel) {
            return new FavouriteEntry(parcel);
        }

        @Override
        public FavouriteEntry[] newArray(int i) {
            return new FavouriteEntry[i];
        }
    };

    public FavouriteEntry(Parcel source) {
        id = source.readInt();
        userId = source.readString();
        recipeId = source.readInt();
        isFav = source.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(userId);
        parcel.writeInt(recipeId);
        parcel.writeString(isFav);
    }
}

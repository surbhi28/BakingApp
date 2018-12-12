package com.example.android.bakingapp.Database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFav(FavouriteEntry favouriteEntry);

    @Query("SELECT * FROM favouriteRecipes WHERE user_id LIKE :userId AND is_fav LIKE :fav")
    LiveData<List<FavouriteEntry>> isFav(String userId, String fav);

    @Query("DELETE FROM favouriteRecipes WHERE user_id LIKE :userId AND recipe_id = :recipeId")
    void deleteFav(String userId, int recipeId);
}

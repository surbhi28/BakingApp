package com.example.android.bakingapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.bakingapp.Database.FavouriteDatabase;
import com.example.android.bakingapp.Database.FavouriteEntry;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {

    private static final String LOG_TAG = FavouriteViewModel.class.getName();

    private LiveData<List<FavouriteEntry>> favourites;

    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        FavouriteDatabase database = FavouriteDatabase.getInstance(this.getApplication());
        FirebaseAuth mFireBaseAuth = FirebaseAuth.getInstance();
        if (mFireBaseAuth != null) {
            String id = mFireBaseAuth.getCurrentUser().getUid();
            Log.d(LOG_TAG, "User Present" + id);
            Log.d(LOG_TAG, "Actively retrieving favourites from database");
            favourites = database.dao().isFav(id, "true");
        } else {
            Log.d(LOG_TAG, "No User");
        }
    }

    public LiveData<List<FavouriteEntry>> getFavourites() {
        return favourites;
    }
}

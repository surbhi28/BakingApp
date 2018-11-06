package com.example.android.bakingapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.bakingapp.ModalClasses.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeViewModel extends ViewModel {

    private static final String LOG_TAG = RecipeViewModel.class.getName();

    public MutableLiveData<List<Recipe>> recipes;

    public LiveData<List<Recipe>> getRecipes() {
        Log.d(LOG_TAG, "Getting data from the Api");

        if (recipes == null) {
            recipes = new MutableLiveData<List<Recipe>>();
            loadRecipes();
        }
        return recipes;
    }

    public void loadRecipes() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BakingApp.BAKE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.BakingApp api = retrofit.create(Api.BakingApp.class);

        Call<List<Recipe>> call = api.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.body() != null) {
                    recipes.setValue(response.body());
                } else {
                    Log.d(LOG_TAG, "No Movies");
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });


    }
}

package com.example.android.bakingapp;

import com.example.android.bakingapp.ModalClasses.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class Api {

    public interface BakingApp{

        String BAKE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

        @GET("baking.json")
        Call<List<Recipe>> getRecipes();

    }
}

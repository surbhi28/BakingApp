package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class RecipeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);

        RecipeDetailFragment fragment = new RecipeDetailFragment();

        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.container_one,fragment).commit();
    }
}

package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.ModalClasses.Ingredients;
import com.example.android.bakingapp.ModalClasses.Steps;

import java.util.List;

public class RecipeDetail extends AppCompatActivity implements RecipeDetailFragment.StepClickListener {

    private boolean mTwoPane;
    private List<Steps> stepsList;
    private List<Ingredients> ingredientsList;
    int position;
    FragmentManager manager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ingredientsList = getIntent().getParcelableArrayListExtra("IngredientsList");

        stepsList = getIntent().getParcelableArrayListExtra("StepsList");

        position = getIntent().getIntExtra("Position",0);
        manager = getSupportFragmentManager();

        if(findViewById(R.id.tablet_layout) != null){
            mTwoPane = true;

            if(savedInstanceState == null) {

                RecipeDetailFragment fragment = new RecipeDetailFragment();

                fragment.setIngredientsList(ingredientsList);
                fragment.setStepsList(stepsList);
                fragment.setTwoPane(mTwoPane);
                manager.beginTransaction().add(R.id.container_one, fragment).commit();

                StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
                stepsDetailFragment.setDescription(stepsList.get(position).getDescription());
                stepsDetailFragment.setVideoURL(stepsList.get(position).getVideoURL());
                manager.beginTransaction().add(R.id.container_two, stepsDetailFragment).commit();
            }
        }else {
            mTwoPane = false;
            if(savedInstanceState == null){

                RecipeDetailFragment fragment = new RecipeDetailFragment();
                fragment.setIngredientsList(ingredientsList);
                fragment.setStepsList(stepsList);
                fragment.setTwoPane(mTwoPane);
                manager.beginTransaction().add(R.id.container_one, fragment).commit();

            }
        }
    }

    @Override
    public void onStepClick(int position) {
        if(mTwoPane){
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
            stepsDetailFragment.setDescription(stepsList.get(position).getDescription());
            stepsDetailFragment.setVideoURL(stepsList.get(position).getVideoURL());
            manager.beginTransaction().replace(R.id.container_two, stepsDetailFragment).commit();

        }
    }
}

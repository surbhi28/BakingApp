package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.bakingapp.ModalClasses.Ingredients;
import com.example.android.bakingapp.ModalClasses.Recipe;
import com.example.android.bakingapp.ModalClasses.Steps;
import com.example.android.bakingapp.widget.IngredientListService;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetail extends AppCompatActivity implements RecipeDetailFragment.StepClickListener {

    private boolean mTwoPane;
    private List<Steps> stepsList;
    public static List<Ingredients> ingredients = new ArrayList<Ingredients>();
    public static String name = "Recipe List";
    private List<Ingredients> ingredientsList;
    private List<Recipe> recipeList;
    int position;
    FragmentManager manager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipeList = getIntent().getParcelableArrayListExtra("RecipeList");

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

             /*   StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
                stepsDetailFragment.setDescription(stepsList.get(position).getDescription());
                stepsDetailFragment.setVideoURL(stepsList.get(position).getVideoURL());
                manager.beginTransaction().add(R.id.container_two, stepsDetailFragment).commit();
            */
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipe_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean addToWidget;
        int itemId = item.getItemId();
        if (itemId == R.id.menu) {
            name = recipeList.get(position).getName();
            ingredients = ingredientsList;
            addToWidget = IngredientListService.startActionChangeIngredient(this);
            if (addToWidget) {
                Toast.makeText(this, "Added to Widget", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Not Added to Widget", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

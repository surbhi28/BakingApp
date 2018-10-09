package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.bakingapp.Adapters.RecipeAdapter;
import com.example.android.bakingapp.ModalClasses.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_recipes)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        loadRecipes();


    }

    public void loadRecipes(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BakingApp.BAKE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.BakingApp api =  retrofit.create(Api.BakingApp.class);

        Call<List<Recipe>> call =  api.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.body() != null){
                    List<Recipe> recipes = response.body();
                    recyclerView.setAdapter(new RecipeAdapter(getApplicationContext(),recipes));
                }else {
                    Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });


    }
}

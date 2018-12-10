package com.example.android.bakingapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.android.bakingapp.Adapters.RecipeAdapter;
import com.example.android.bakingapp.Database.FavouriteDatabase;
import com.example.android.bakingapp.Database.FavouriteEntry;
import com.example.android.bakingapp.ModalClasses.Recipe;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mFirebaseAuth;
    private RecipeAdapter recipeAdapter;
    private FavouriteDatabase database;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;

    public static String userName;
    public static String userId;
    public static List<FavouriteEntry> favouriteEntryListSaved;
    public static List<Recipe> recipeListSaved;

    @BindView(R.id.recycler_view_recipes)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        database = FavouriteDatabase.getInstance(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(getApplicationContext());

        boolean isConnected = Network.getConnectivityStatus(MainActivity.this);
        if (isConnected) {
            if (savedInstanceState == null) {
                signInMethod();
            } else {
                recipeListSaved = savedInstanceState.getParcelableArrayList("RecipeList");
                favouriteEntryListSaved = savedInstanceState.getParcelableArrayList("FavouriteList");
                userId = savedInstanceState.getString("UserId");
                userName = savedInstanceState.getString("UserName");
                recyclerView.setAdapter(recipeAdapter);
                recipeAdapter.favouriteFetchedList(favouriteEntryListSaved);
                recipeAdapter.setRecipeList(recipeListSaved, userId);
            }
        } else {
            Toast.makeText(this, "Check Network Connection", Toast.LENGTH_SHORT).show();
        }
    }


    public void signInMethod() {

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    String name = user.getDisplayName();
                    userName = name;
                    toolbar.setTitle(getString(R.string.message) + " " + userName);
                    String id = user.getUid();
                    userId = id;
                    viewModal();
                    checkIfFavourite();
                }else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN );

                }
            }
        };

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode , Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                String name = mFirebaseAuth.getCurrentUser().getDisplayName();
                Toast.makeText(this, getResources().getString(R.string.sign_in), Toast.LENGTH_SHORT).show();
                mFirebaseAnalytics.setUserProperty("user_name",name);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, getResources().getString(R.string.sign_cancel), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void viewModal() {
        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipeList) {
                recipeListSaved = recipeList;
                recyclerView.setAdapter(recipeAdapter);
                recipeAdapter.setRecipeList(recipeList, userId);

            }
        });
    }

    public void checkIfFavourite() {
        final LiveData<List<FavouriteEntry>> recipeIds = database.dao().isFav(userId, "true");
        recipeIds.observe(this, new Observer<List<FavouriteEntry>>() {

            @Override
            public void onChanged(@Nullable List<FavouriteEntry> favouriteEntries) {
                favouriteEntryListSaved = favouriteEntries;
                recipeAdapter.favouriteFetchedList(favouriteEntries);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("FavouriteList", (ArrayList<FavouriteEntry>) favouriteEntryListSaved);
        outState.putParcelableArrayList("RecipeList", (ArrayList<Recipe>) recipeListSaved);
        outState.putString("UserName", userName);
        outState.putString("UserId", userId);

    }

    @Override
    public void onResume(){
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    /*@Override
    public void onPause(){
        super.onPause();
        if(mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
    */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

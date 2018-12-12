package com.example.android.bakingapp;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.bakingapp.Adapters.RecipeAdapter;
import com.example.android.bakingapp.Database.FavouriteDatabase;
import com.example.android.bakingapp.Database.FavouriteEntry;
import com.example.android.bakingapp.ModalClasses.Recipe;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 1;
    private static final String LOG_TAG = MainActivity.class.getName();


    public static String userName;
    public String userId;

    @BindView(R.id.recycler_view_recipes)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private static FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mFirebaseAuth;
    private RecipeAdapter recipeAdapter;
    private FavouriteDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        database = FavouriteDatabase.getInstance(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(getApplicationContext());


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userName = user.getDisplayName();
                    userId = user.getUid();
                    toolbar.setTitle(getString(R.string.message) + " " + userName);
                    recipeAdapter.setUserId(userId);
                    viewModal();
                    checkIfFavourite();
                } else {
                    signOutClean();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);

                }
            }
        };

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, getResources().getString(R.string.sign_in), Toast.LENGTH_SHORT).show();
                mFirebaseAnalytics.setUserProperty("user_name", userName);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, getResources().getString(R.string.sign_cancel), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    public void viewModal() {
        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipeList) {
                recyclerView.setAdapter(recipeAdapter);
                recipeAdapter.setRecipeList(recipeList);

            }
        });
    }

    public void checkIfFavourite() {
        FavouriteViewModel favouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        favouriteViewModel.getFavourites().observe(this, new Observer<List<FavouriteEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteEntry> favouriteEntries) {
                recipeAdapter.favouriteFetchedList(favouriteEntries);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    public void signOutClean() {
        recipeAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sign_out, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                if (mFirebaseAuth.getCurrentUser() != null) {
                    AuthUI.getInstance().signOut(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

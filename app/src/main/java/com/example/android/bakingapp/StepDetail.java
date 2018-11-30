package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.bakingapp.ModalClasses.Steps;
import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;

public class StepDetail extends AppCompatActivity {

    private static final String LOG_TAG = StepDetail.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_detail_activity);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ArrayList<Steps> list = getIntent().getParcelableArrayListExtra("StepsList");
        int position = getIntent().getIntExtra("Position", 0);

        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
            stepsDetailFragment.setDescription(list.get(position).getDescription());
            stepsDetailFragment.setVideoURL(list.get(position).getVideoURL());

            Log.d(LOG_TAG, "VideoUrl: " + list.get(position).getVideoURL());

            fragmentManager.beginTransaction().add(R.id.container_two, stepsDetailFragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sign_out,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}

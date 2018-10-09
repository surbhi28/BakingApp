package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapters.IngredientsAdapter;
import com.example.android.bakingapp.Adapters.StepsAdapter;
import com.example.android.bakingapp.ModalClasses.Ingredients;
import com.example.android.bakingapp.ModalClasses.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {

    @BindView(R.id.ingredients_recycler_view)
    RecyclerView recyclerViewIngredients;
    @BindView(R.id.steps_recycler_view)
    RecyclerView recyclerViewSteps;
    IngredientsAdapter ingredientsAdapter;
    StepsAdapter stepsAdapter;

    public RecipeDetailFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_detail_fragment,container,false);

        ButterKnife.bind(this,rootView);

        List<Ingredients> list = getActivity().getIntent().getParcelableArrayListExtra("IngredientsList");
        List<Steps> stepsList = getActivity().getIntent().getParcelableArrayListExtra("StepsList");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewIngredients.setLayoutManager(linearLayoutManager);
        ingredientsAdapter = new IngredientsAdapter(getContext(),list);
        recyclerViewIngredients.setAdapter(ingredientsAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        recyclerViewSteps.setLayoutManager(linearLayoutManager1);
        stepsAdapter = new StepsAdapter(getContext(),stepsList);
        recyclerViewSteps.setAdapter(stepsAdapter);

        return rootView;


    }
}

package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapters.IngredientsAdapter;
import com.example.android.bakingapp.Adapters.StepsAdapter;
import com.example.android.bakingapp.ModalClasses.Ingredients;
import com.example.android.bakingapp.ModalClasses.Steps;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements StepsAdapter.StepItemClickListener {

    private static final String LOG_TAG = RecipeDetailFragment.class.getName();

    @BindView(R.id.ingredients_recycler_view)
    RecyclerView recyclerViewIngredients;
    @BindView(R.id.steps_recycler_view)
    RecyclerView recyclerViewSteps;
    IngredientsAdapter ingredientsAdapter;
    StepsAdapter stepsAdapter;

    public static List<Ingredients> ingredientsList;
    public static List<Steps> stepsList;
    public static boolean mTwoPane;

    private StepClickListener mStepClickListener;

    public RecipeDetailFragment(){}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            setIngredientsAdapter();
            setStepsAdapter();
        } else {
            stepsList = savedInstanceState.getParcelableArrayList("StepsList");
            ingredientsList = savedInstanceState.getParcelableArrayList("IngredientsList");
            mTwoPane = savedInstanceState.getBoolean("TwoPane");
            setIngredientsAdapter();
            setStepsAdapter();
        }
    }


    @Override
        public void onAttach(Context context){
        super.onAttach(context);
        try {
            mStepClickListener = (StepClickListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement StepClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_detail_fragment,container,false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    public void setIngredientsList(List<Ingredients> list){
        ingredientsList = list;
    }

    public void setStepsList(List<Steps> list) {
        stepsList = list;
    }

    public void setTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

    public interface StepClickListener {
        void onStepClick(int position);
    }

    @Override
    public void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("StepsList" , (ArrayList<Steps>) stepsList);
        outState.putParcelableArrayList("IngredientsList", (ArrayList<Ingredients>) ingredientsList);
        outState.putBoolean("TwoPane",mTwoPane);
    }

    @Override
    public void onStepItemClicked(int clickedItemIndex) {
        if(mTwoPane){
            mStepClickListener.onStepClick(clickedItemIndex);
        }else {
            Intent intent = new Intent(getContext(), StepDetail.class);
            intent.putParcelableArrayListExtra("StepsList",(ArrayList<Steps>)stepsList);
            intent.putExtra("Position",clickedItemIndex);
            startActivity(intent);
        }
    }

    public void setIngredientsAdapter(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewIngredients.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerViewIngredients.addItemDecoration(decoration);
        ingredientsAdapter = new IngredientsAdapter(getContext(),ingredientsList);
        recyclerViewIngredients.setAdapter(ingredientsAdapter);
    }

    public void setStepsAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSteps.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerViewSteps.addItemDecoration(decoration);
        stepsAdapter = new StepsAdapter(stepsList,this);
        recyclerViewSteps.setAdapter(stepsAdapter);
    }
}

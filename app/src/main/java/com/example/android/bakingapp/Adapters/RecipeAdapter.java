package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.ModalClasses.Recipe;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    public static final String LOG_TAG = RecipeAdapter.class.getName();

    private Context context;
    List<Recipe> recipeList;

    public RecipeAdapter(Context context , List<Recipe> recipes){
        this.context = context;
        recipeList = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.all_recipes,parent,false);
        return new RecipeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, final int position) {
        final String name = recipeList.get(position).getName();
        int servings = recipeList.get(position).getServings();
        holder.recipeName.setText(name);
        holder.noOfServings.setText("No of Servings " +String.valueOf(servings));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RecipeDetail.class);
                intent.putExtra("Position", holder.getAdapterPosition());
                intent.putParcelableArrayListExtra("RecipeList", new ArrayList<Parcelable>(recipeList));
                intent.putParcelableArrayListExtra("IngredientsList",new ArrayList<Parcelable>(recipeList.get(holder.getAdapterPosition()).getIngredients()));
                intent.putParcelableArrayListExtra("StepsList",new ArrayList<Parcelable>(recipeList.get(holder.getAdapterPosition()).getSteps()));
                Log.d(LOG_TAG,"Value of StepsList " +recipeList.get(position).getSteps().get(1).getShortDescription());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(recipeList == null)return 0;
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name)
        TextView recipeName;
        @BindView(R.id.no_ofServings)
        TextView noOfServings;
        @BindView(R.id.card_view)
        CardView cardView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

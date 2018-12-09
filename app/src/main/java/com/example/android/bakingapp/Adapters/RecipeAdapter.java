package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.AppExecutor;
import com.example.android.bakingapp.Database.FavouriteDatabase;
import com.example.android.bakingapp.Database.FavouriteEntry;
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
    private FavouriteDatabase database;
    private Boolean favourite;
    private String userId;
    private List<FavouriteEntry> list;

    public RecipeAdapter(Context context) {
        this.context = context;
    }

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

        final int recipeId = recipeList.get(position).getId();
        isFavourite(holder, recipeId);

        holder.fav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (favourite) {
                    favourite = false;
                    deleteFavouriteRecipe(recipeId);
                    holder.fav_button.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_off));
                    Toast.makeText(context, "Unmarked as Favourite", Toast.LENGTH_SHORT).show();
                } else {
                    favourite = true;
                    saveFavouriteRecipe(recipeId);
                    holder.fav_button.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_on));
                    Toast.makeText(context, "Marked as Favourite", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public void setRecipeList(List<Recipe> recipeLists, String id) {
        recipeList = recipeLists;
        userId = id;
    }

    public void favouriteFetchedList(List<FavouriteEntry> data) {
        list = data;
    }

    public void isFavourite(RecipeViewHolder holder, int recipeId) {


        if (list.size() == 0) {
            favourite = false;
            holder.fav_button.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_off));
        } else {
            int no = list.size();
            for (int i = 0; i < no; i++) {
                if (list.get(i).getRecipeId() == recipeId) {
                    favourite = true;
                    holder.fav_button.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_on));
                } else {
                    favourite = false;
                    holder.fav_button.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_off));
                }
            }
        }
    }

    public void saveFavouriteRecipe(final int recipeId) {
        database = FavouriteDatabase.getInstance(context);
        final FavouriteEntry entry = new FavouriteEntry(userId, recipeId, "true");
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.dao().insertFav(entry);
                Log.d(LOG_TAG, "Recipe Id Saved" + recipeId);
            }
        });

    }

    public void deleteFavouriteRecipe(final int recipeNo) {
        database = FavouriteDatabase.getInstance(context);
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.dao().deleteFav(userId, recipeNo);
                Log.d(LOG_TAG, "Recipe Deleted" + recipeNo);
            }
        });

    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name)
        TextView recipeName;
        @BindView(R.id.no_ofServings)
        TextView noOfServings;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.button)
        Button fav_button;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}

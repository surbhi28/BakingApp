package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.ModalClasses.Ingredients;
import com.example.android.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    Context context;
    List<Ingredients> ingredientsList;

    public IngredientsAdapter(Context context , List<Ingredients> list){
        this.context = context;
        ingredientsList = list;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
       context = parent.getContext();
       View view = LayoutInflater.from(context).inflate(R.layout.ingredients,parent,false);
       return new IngredientsViewHolder(view);

    }


    @Override
    public void onBindViewHolder( IngredientsViewHolder holder, int position) {
        float quantity = ingredientsList.get(position).getQuantity();
        String measure = ingredientsList.get(position).getMeasure();
        String ingredients = ingredientsList.get(position).getIngredient();
        holder.ingredientsView.setText(String.valueOf(quantity) +"" +measure +"of " +ingredients);

    }


    @Override
    public int getItemCount() {
        if(ingredientsList == null) {return 0;}
        return ingredientsList.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients_view)
        TextView ingredientsView;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

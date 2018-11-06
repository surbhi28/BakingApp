package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.ModalClasses.Ingredients;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeDetail;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredients> ingredients;

    public ListRemoteViewsFactory(Context context) {
        mContext = context;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredients = RecipeDetail.ingredients;

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        float quantity = ingredients.get(i).getQuantity();
        String measure = ingredients.get(i).getMeasure();
        String ingredient = ingredients.get(i).getIngredient();
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view);
        remoteViews.setTextViewText(R.id.ingredients_widget_list, String.valueOf(quantity) + " " + measure + " " + ingredient);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

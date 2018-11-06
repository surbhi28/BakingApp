package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeDetail;

public class IngredientListService extends IntentService {

    public static final String ACTION_INGREDIENT_CHANGE = "com.example.android.bakingapp.widget.action.change_ingredient";

    public IngredientListService() {
        super("IngredientListService");

    }

    public static boolean startActionChangeIngredient(Context context) {
        Intent intent = new Intent(context, IngredientListService.class);
        intent.setAction(ACTION_INGREDIENT_CHANGE);
        try {
            context.startService(intent);
            return true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_INGREDIENT_CHANGE.equals(action)) {
                handleActionIngredientChange();
            }
        }
    }

    public void handleActionIngredientChange() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsId = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakeWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetsId, R.id.list_view);
        BakeWidget.updateIngredientsWidget(this, appWidgetManager, RecipeDetail.name, appWidgetsId);
    }
}

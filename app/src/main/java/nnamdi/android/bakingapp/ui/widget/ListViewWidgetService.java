package nnamdi.android.bakingapp.ui.widget;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import nnamdi.android.bakingapp.R;
import nnamdi.android.bakingapp.models.Ingredient;
import nnamdi.android.bakingapp.models.Recipe;
import nnamdi.android.bakingapp.utils.LoadRecipeJsonUtils;

/**
 * Created by Nnamdi on 3/9/2018.
 */


public class ListViewWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetListProvider(this.getApplicationContext());
    }
}

class BakingWidgetListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<Ingredient> mIngredientsList;
    private final String RECIPE_PREFERENCE_KEY = "recipe_key";

    public BakingWidgetListProvider(Context context){
        mContext = context;

    }


    @Override
    public void onCreate() {
        mIngredientsList = new ArrayList<>();
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String recipeJsonString =  pref.getString(RECIPE_PREFERENCE_KEY, null);
        Recipe recipe = new LoadRecipeJsonUtils().loadRecipeFromString(recipeJsonString);
        mIngredientsList = recipe.getIngredients();


    }


    @Override
    public void onDestroy() {
        mIngredientsList.clear();
    }

    @Override
    public int getCount() {
        return mIngredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget_item);

        Ingredient ingredient = mIngredientsList.get(position);

        views.setTextViewText(R.id.tv_widget_list_title, ingredient.getIngredient());
        views.setTextViewText(R.id.tv_widget_list_measure, ingredient.getQuantity() + ""+ ingredient.getMeasure());


        //Intent fillInIntent = new Intent();
        //fillInIntent.putExtra("IngredientTitile", ingredient.getIngredient());
        //fillInIntent.putExtra("IngredientMeasure", ingredient.getQuantity() + ""+ ingredient.getMeasure());

        //views.setOnClickFillInIntent(R.id.parentView, fillInIntent);


        return views;
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
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

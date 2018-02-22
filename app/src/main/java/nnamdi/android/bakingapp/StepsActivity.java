package nnamdi.android.bakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Adapter;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.models.Recipe;
import nnamdi.android.bakingapp.models.Step;
import nnamdi.android.bakingapp.utils.LoadRecipeData;

public class StepsActivity extends AppCompatActivity implements StepsAdapter.StepsAdapterOnClickHandler {

    private IngredientsAdapter ingredientAdapter;
    @BindView(R.id.recycler_view_ingredients) RecyclerView  ingredientRecyclerView;
    private RecyclerView.LayoutManager ingredientLayoutManger;

    private StepsAdapter stepsAdapter;
    @BindView(R.id.recycler_view_steps) RecyclerView stepsRecyclerView;
    private RecyclerView.LayoutManager stepsLayoutManager;

    private final String STEPS_EXTRA = "step";
    private final String RECIPE_PREFERENCE_KEY = "recipe_key";
    private final String STEPS_SIZE_KEY = "step_size";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("Recipe");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        if(recipe == null){
            String recipeJsonString =  pref.getString(RECIPE_PREFERENCE_KEY, null);
            recipe = new LoadRecipeData().loadRecipeFromSharedPreferences(recipeJsonString);
        }else {
            Gson gson = new Gson();
            String json = gson.toJson(recipe);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString(RECIPE_PREFERENCE_KEY, json);
            editor.apply();

            editor.putInt(STEPS_SIZE_KEY, recipe.getSteps().size());
            editor.apply();
        }



        String recipeName = recipe.getName();
        setTitle(recipeName);

        ingredientLayoutManger = new LinearLayoutManager(this);
        ingredientRecyclerView.setLayoutManager(ingredientLayoutManger);
        ingredientRecyclerView.setHasFixedSize(true);

        ingredientAdapter = new IngredientsAdapter(this);
        ingredientRecyclerView.setAdapter(ingredientAdapter);
        ingredientAdapter.setIngredientsData(recipe.getIngredients());

        stepsLayoutManager = new LinearLayoutManager(this);
        stepsRecyclerView.setLayoutManager(stepsLayoutManager);
        stepsRecyclerView.setHasFixedSize(true);

        stepsAdapter = new StepsAdapter(this);
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsAdapter.setStepsData(recipe.getSteps());
    }

    @Override
    public void click(Step step) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra(STEPS_EXTRA, step);
        this.startActivity(intent);

    }
}

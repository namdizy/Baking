package nnamdi.android.bakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.models.Recipe;
import nnamdi.android.bakingapp.models.Step;
import nnamdi.android.bakingapp.utils.LoadRecipeData;


public class StepsActivity extends AppCompatActivity implements StepsFragment.OnStepsFragmentInteractionListener {

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

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        IngredientsFragment ingredientsFragment = new IngredientsFragment().newInstance(recipe.getIngredients());
        fragmentTransaction
                .add(R.id.ingredients_container, ingredientsFragment);

        StepsFragment stepsFragment = new StepsFragment().newInstance(recipe.getSteps());

        fragmentTransaction
                .add(R.id.steps_container, stepsFragment)
                .commit();


        if(findViewById(R.id.details_container) != null){


        }


    }

    @Override
    public void onStepFragmentInteraction(Step step) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra(STEPS_EXTRA, step);
        this.startActivity(intent);
    }
}

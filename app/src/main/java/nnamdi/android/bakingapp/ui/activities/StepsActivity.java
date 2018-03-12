package nnamdi.android.bakingapp.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.R;
import nnamdi.android.bakingapp.ui.fragments.StepsFragment;
import nnamdi.android.bakingapp.models.Recipe;
import nnamdi.android.bakingapp.models.Step;
import nnamdi.android.bakingapp.ui.fragments.DetailsFragment;
import nnamdi.android.bakingapp.ui.fragments.IngredientsFragment;
import nnamdi.android.bakingapp.utils.LoadRecipeJsonUtils;


public class StepsActivity extends AppCompatActivity implements StepsFragment.OnStepsFragmentInteractionListener {

    private final String STEPS_EXTRA = "step";
    private final String RECIPE_PREFERENCE_KEY = "recipe_key";
    private final String STEPS_SIZE_KEY = "step_size";

    private boolean TABLET_LAYOUT = false;

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
            recipe = new LoadRecipeJsonUtils().loadRecipeFromString(recipeJsonString);
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
            TABLET_LAYOUT = true;
        }


    }

    @Override
    public void onStepFragmentInteraction(Step step) {

        if(TABLET_LAYOUT){

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setmStepItem(step);
            detailsFragment.setPlayerPosition(0);
            fragmentTransaction
                    .replace(R.id.details_container, detailsFragment)
                    .commit();

        }else{
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra(STEPS_EXTRA, step);
            this.startActivity(intent);
        }

    }
}

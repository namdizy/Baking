package nnamdi.android.bakingapp;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.models.Recipe;
import nnamdi.android.bakingapp.models.Step;
import nnamdi.android.bakingapp.utils.LoadRecipeData;

public class StepDetailsActivity extends AppCompatActivity {


    @BindView(R.id.btn_step_details_back) ImageButton mBackBtn;
    @BindView(R.id.btn_step_details_forward) ImageButton mForwardBtn;

    private Step mStep;
    private SharedPreferences pref;
    private ArrayList<Step> stepsArray;

    private final String STEPS_EXTRA = "step";
    private final String RECIPE_PREFERENCE_KEY = "recipe_key";

    private final String STEPS_SIZE_KEY = "step_size";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        mStep = intent.getParcelableExtra(STEPS_EXTRA);
        setTitle(mStep.getShortDescription());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        DetailsFragment detailsFragment = new DetailsFragment().newInstance(mStep);
        detailsFragment.setContext(this);
        fragmentTransaction
                .add(R.id.details_container, detailsFragment)
                .commit();


        pref = PreferenceManager.getDefaultSharedPreferences(this);
        int stepsListSize  =  pref.getInt(STEPS_SIZE_KEY, 0);

        if(mStep.getId() == 0){
            mBackBtn.setVisibility(View.INVISIBLE);
        }

        if(stepsListSize-1 == mStep.getId()){
            mForwardBtn.setVisibility(View.INVISIBLE);
        }

        String recipeJsonString =  pref.getString(RECIPE_PREFERENCE_KEY, null);
        Recipe recipe = new LoadRecipeData().loadRecipeFromSharedPreferences(recipeJsonString);
        stepsArray = recipe.getSteps();

    }

    public void onForwardClick(View v){
        for(Step s: stepsArray){
            if(s.getId() == mStep.getId() +1){
                Intent intent = new Intent(this, StepDetailsActivity.class);
                intent.putExtra(STEPS_EXTRA, s);
                this.startActivity(intent);
            }
        }
    }

    public void onBackClick(View v){
        for(Step s: stepsArray){
            if(s.getId() == mStep.getId() -1){
                Intent intent = new Intent(this, StepDetailsActivity.class);
                intent.putExtra(STEPS_EXTRA, s);
                this.startActivity(intent);
            }
        }
    }
}

package nnamdi.android.bakingapp.ui.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.R;
import nnamdi.android.bakingapp.ui.adapter.RecipeAdapter;
import nnamdi.android.bakingapp.models.Recipe;
import nnamdi.android.bakingapp.utils.FetchRecipeDataTask;
import nnamdi.android.bakingapp.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>, RecipeAdapter.RecipeAdapterOnclickHandler {

    private RecyclerView.LayoutManager mLayoutManager;
    private final String TAG = getClass().getName();

    private static final int RECIPE_LOADER_ID = 22;

    @BindView(R.id.recycler_view_recipe) RecyclerView mRecyclerView;
    @BindView(R.id.tv_recipe_error_message)
    TextView mErrorMessage;

    private RecipeAdapter mRecipeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(this, this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        getLoaderManager().initLoader(RECIPE_LOADER_ID, null, this);

    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int i, Bundle bundle){

        if(NetworkUtils.checkConnection(this)){
            mRecyclerView.setVisibility(View.VISIBLE);
            mErrorMessage.setVisibility(View.INVISIBLE);
            return new FetchRecipeDataTask(this);
        }
        else{
            mRecyclerView.setVisibility(View.INVISIBLE);
            mErrorMessage.setVisibility(View.VISIBLE);
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> recipes) {

        if(recipes != null){
            mRecipeAdapter.setmRecipe(recipes);
        } else{

        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {
        mRecipeAdapter.setmRecipe(null);
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, StepsActivity.class);
        intent.putExtra("Recipe",recipe);
        this.startActivity(intent);
    }
}

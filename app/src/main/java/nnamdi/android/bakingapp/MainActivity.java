package nnamdi.android.bakingapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.models.Recipe;
import nnamdi.android.bakingapp.utils.FetchRecipeDataTask;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>, RecipeAdapter.RecipeAdapterOnclickHandler{

    private RecyclerView.LayoutManager mLayoutManager;

    private static final int RECIPE_LOADER_ID = 22;

    @BindView(R.id.recycler_view_recipe) RecyclerView mRecyclerView;

    private RecipeAdapter mRecipeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        getLoaderManager().initLoader(RECIPE_LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int i, Bundle bundle){

        return new FetchRecipeDataTask(this);
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

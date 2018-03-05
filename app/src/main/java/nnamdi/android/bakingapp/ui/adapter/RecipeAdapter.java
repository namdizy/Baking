package nnamdi.android.bakingapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
;
import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.R;
import nnamdi.android.bakingapp.models.Recipe;

/**
 * Created by Nnamdi on 2/5/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private ArrayList<Recipe> mRecipe;
    private final RecipeAdapterOnclickHandler mClickHandler;

    public interface RecipeAdapterOnclickHandler{
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(RecipeAdapterOnclickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipe.get(position);
        holder.mRecipeNameTV.setText(recipe.getName());
        holder.mRecipeServingsTV.setText("Servings: "+recipe.getServings());
        String image = recipe.getImage();

        if(image.isEmpty()){
            holder.mImageView.setImageResource(R.drawable.cupcake);
        }
    }

    @Override
    public int getItemCount() {
        if(mRecipe == null) return 0;
        return mRecipe.size();
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.imv_recipe_image) ImageView mImageView;
        @BindView(R.id.tv_recipe_name) TextView mRecipeNameTV;
        @BindView(R.id.tv_recipe_servings) TextView mRecipeServingsTV;

        public RecipeAdapterViewHolder(View view){
            super(view);

            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipe.get(adapterPosition);
            mClickHandler.onClick(recipe);
        }
    }

    public void setmRecipe(ArrayList<Recipe> data) {
        this.mRecipe = data;
        notifyDataSetChanged();
    }
}

package nnamdi.android.bakingapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.models.Ingredient;

/**
 * Created by Nnamdi on 2/11/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private ArrayList<Ingredient> mIngredients;
    private Context mContext;

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutId = R.layout.ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);
        return new IngredientViewHolder(view);
    }

    public IngredientsAdapter(Context context){
        mContext = context;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);

        holder.mIngredientMeasuerTV.setText(ingredient.getQuantity()+ ""+ ingredient.getMeasure() );
        holder.mIngredientTitleTV.setText(ingredient.getIngredient());

        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryLight));
        }
    }

    @Override
    public int getItemCount() {
        if(mIngredients == null) return 0;
        else return mIngredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.tv_ingredient_title) TextView mIngredientTitleTV;
        @BindView(R.id.tv_ingredient_measure) TextView mIngredientMeasuerTV;


        public IngredientViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }



    public void setIngredientsData(ArrayList<Ingredient> data){
        mIngredients = data;
    }
}

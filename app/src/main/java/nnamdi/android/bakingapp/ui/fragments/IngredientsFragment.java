package nnamdi.android.bakingapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.R;
import nnamdi.android.bakingapp.models.Ingredient;
import nnamdi.android.bakingapp.ui.adapter.IngredientsAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link IngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsFragment extends Fragment {

    private IngredientsAdapter ingredientAdapter;
    @BindView(R.id.recycler_view_ingredients) RecyclerView ingredientRecyclerView;
    private RecyclerView.LayoutManager ingredientLayoutManger;

    private static final String INGREDIENTS_LIST = "ingredients_list";
    private ArrayList<Ingredient> mIngredientsList;


    public IngredientsFragment() {
        // Required empty public constructor
    }

    public IngredientsFragment newInstance(ArrayList<Ingredient> ingredients) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(INGREDIENTS_LIST, ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIngredientsList = getArguments().getParcelableArrayList(INGREDIENTS_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, view);

        if(mIngredientsList != null){
            ingredientLayoutManger = new LinearLayoutManager(getContext());
            ingredientRecyclerView.setLayoutManager(ingredientLayoutManger);
            ingredientRecyclerView.setHasFixedSize(true);
            ingredientAdapter = new IngredientsAdapter();
            ingredientRecyclerView.setAdapter(ingredientAdapter);
            ingredientAdapter.setIngredientsData(mIngredientsList);
        }

        return view;
    }


}

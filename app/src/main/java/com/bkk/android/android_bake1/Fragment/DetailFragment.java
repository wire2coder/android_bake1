package com.bkk.android.android_bake1.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bkk.android.android_bake1.Adapter.DetailAdapter;
import com.bkk.android.android_bake1.DetailActivity;
import com.bkk.android.android_bake1.Model.Ingredient;
import com.bkk.android.android_bake1.Model.Recipe;
import com.bkk.android.android_bake1.R;

import java.util.ArrayList;
import java.util.List;

import static com.bkk.android.android_bake1.Util.KeyUtil.SELECTED_RECIPES;

public class DetailFragment extends Fragment {

    ArrayList<Recipe> al_recipe;
    ArrayList<String> al_ingredients_widget;
    String recipe_name;

    public DetailFragment() {
        // required empty constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        al_recipe = new ArrayList<>();

        RecyclerView rv_recipe_detail;
        TextView tv_recipe_detail;


        if(savedInstanceState != null) {
            al_recipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);
        } else {
            al_recipe =getArguments().getParcelableArrayList(SELECTED_RECIPES);
        }


        // for Widget
        al_ingredients_widget = new ArrayList<>();

        List<Ingredient> l_ingredients = al_recipe.get(0).getIngredients();
//        recipe_name = al_recipe.get(0).getName();

        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        tv_recipe_detail = (TextView)rootView.findViewById(R.id.tv_recipe_detail);



        for(int i=0; i < l_ingredients.size(); i++) {

            String ingredient = l_ingredients.get(i).getIngredient();
            String quantity =  String.valueOf( l_ingredients.get(i).getQuantity() );
            String measurement = l_ingredients.get(i).getMeasure();

            tv_recipe_detail.append(ingredient + "\n" + quantity + " " + measurement + "\n\n");

            // Adding data for Widget
            al_ingredients_widget.add(ingredient + "\n" + quantity + " " + measurement + "\n\n");
        }


        // Logic for user interface
        rv_recipe_detail = (RecyclerView)rootView.findViewById(R.id.rv_recipe_detail);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_recipe_detail.setLayoutManager(linearLayoutManager);

        DetailAdapter detailAdapter = new DetailAdapter( (DetailActivity)getActivity() );
        rv_recipe_detail.setAdapter(detailAdapter);

        detailAdapter.swapData(al_recipe, getContext());

        // logic for updating the Widget
        // TODO: widget
//        UpdateBakingService.startBakingService( getContext(), al_ingredients_widget);

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putParcelableArrayList(SELECTED_RECIPES, al_recipe);
        currentState.putString("Title", recipe_name);
    } // onSaveInstanceState()


} // class DetailFragment

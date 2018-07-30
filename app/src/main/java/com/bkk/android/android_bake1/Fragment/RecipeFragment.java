package com.bkk.android.android_bake1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bkk.android.android_bake1.Adapter.RecipeAdapter;
import com.bkk.android.android_bake1.MainActivity;
import com.bkk.android.android_bake1.Model.Recipe;
import com.bkk.android.android_bake1.R;
import com.bkk.android.android_bake1.Retrofit.RecipeInterface;
import com.bkk.android.android_bake1.Retrofit.RetrofitBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bkk.android.android_bake1.Util.KeyUtil.ALL_RECIPES;

public class RecipeFragment extends Fragment {


    public RecipeFragment() {
        // required empty constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_fragment, container, false);

        RecyclerView recipe_recycler;

        final RecipeAdapter recipesAdapter = new RecipeAdapter((MainActivity)getActivity());
        recipe_recycler = (RecyclerView) rootView.findViewById(R.id.recipe_recycler);
        recipe_recycler.setAdapter(recipesAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recipe_recycler.setLayoutManager(mLayoutManager);

        RecipeInterface recipeInterface = RetrofitBuilder.Retrieve();
        Call< ArrayList<Recipe> > recipe = recipeInterface.getRecipe();


//        SimpleIdlingResource idlingResource = (SimpleIdlingResource)((MainActivity)getActivity()).getIdlingResource();
//
//        if (idlingResource != null) {
//            idlingResource.setIdleState(false);
//        }


        recipe.enqueue(new Callback<ArrayList<Recipe>>() {

            @Override
            public void onResponse(Call<ArrayList<Recipe>> request, Response<ArrayList<Recipe>> response) {

                ArrayList<Recipe> al_recipe = response.body();

                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList(ALL_RECIPES, al_recipe);

                recipesAdapter.swapData(al_recipe,getContext());

//                if (idlingResource != null) {
//                    idlingResource.setIdleState(true);
//                }

            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> request, Throwable throwable) {
                Log.v("Retrofit Error ", throwable.getMessage());
            }
        });

        return rootView;

    } // onCreateView()


} // class RecipeFragment

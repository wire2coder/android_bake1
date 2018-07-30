package com.bkk.android.android_bake1.Retrofit;

import com.bkk.android.android_bake1.Model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;



public interface RecipeInterface {

    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();

} // interface RecipeInterface

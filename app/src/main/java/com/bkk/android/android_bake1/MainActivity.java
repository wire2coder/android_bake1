package com.bkk.android.android_bake1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bkk.android.android_bake1.Adapter.RecipeAdapter;
import com.bkk.android.android_bake1.Model.Recipe;
import com.bkk.android.android_bake1.Util.KeyUtil;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity
        implements RecipeAdapter.ListItemClickListener {



    // TODO: Expresso test
//    @Nullable
//    private SimpleIdlingResource mIdlingResource;


//    @VisibleForTesting
//    @NonNull
//    public IdlingResource getIdlingResource() {
//
//        if (idling_resource == null) {
//            idling_resource = new SimpleIdlingResource();
//        }
//
//        return idling_resource;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // adding Toolbar for requirement
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar1); << this line causes error
//        getSupportActionBar().setHomeButtonEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Bake it For Me");
        // TODO: here, july 31


        // TODO: Expresso test
//        getIdlingResource();
    }



    //    implements RecipeAdapter.ListItemClickListener
    @Override
    public void onListItemClick(Recipe recipe_object) {

        ArrayList<Recipe> al_selected_recipe = new ArrayList<>();
        al_selected_recipe.add(recipe_object);


        Bundle bundle_selected_recipe = new Bundle();
        bundle_selected_recipe.putParcelableArrayList(KeyUtil.SELECTED_RECIPES, al_selected_recipe);


        final Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtras(bundle_selected_recipe);
        startActivity(intent);

    } // onListItemClick()




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


} // class MainActivity

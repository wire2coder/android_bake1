package com.bkk.android.android_bake1;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bkk.android.android_bake1.Adapter.DetailAdapter;
import com.bkk.android.android_bake1.Fragment.DetailFragment;
import com.bkk.android.android_bake1.Fragment.StepVideoFragment;
import com.bkk.android.android_bake1.Model.Recipe;
import com.bkk.android.android_bake1.Model.Step;

import java.util.ArrayList;
import java.util.List;

import static com.bkk.android.android_bake1.Util.KeyUtil.SELECTED_INDEX;
import static com.bkk.android.android_bake1.Util.KeyUtil.SELECTED_RECIPES;
import static com.bkk.android.android_bake1.Util.KeyUtil.SELECTED_STEPS;
import static com.bkk.android.android_bake1.Util.KeyUtil.STACK_DETAIL_ACTIVITY;
import static com.bkk.android.android_bake1.Util.KeyUtil.TITLE;

public class DetailActivity extends AppCompatActivity
        implements DetailAdapter.ListItemClickListener,
        StepVideoFragment.ListItemClickListener
{



    ArrayList<Recipe> al_recipe;
    public String recipe_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {

            Bundle al_recipe_bundle = getIntent().getExtras();

            al_recipe = new ArrayList<>();
            al_recipe = al_recipe_bundle.getParcelableArrayList(SELECTED_RECIPES);

            recipe_name = al_recipe.get(0).getName(); // >> Nutella Pie, for Navigation bar

            FragmentManager fragmentManager = getSupportFragmentManager();

            final DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(al_recipe_bundle);

            // NOT A VIDEO Player Fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_fragment, detailFragment)
                    .addToBackStack(STACK_DETAIL_ACTIVITY)
                    .commit();


            // tablet layout, activity_main.xml.xml
            if ( findViewById(R.id.recipe_linear_layout).getTag() != null
                    && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {


                // Video Player Fragment
                final StepVideoFragment stepVideoFragment = new StepVideoFragment();

                stepVideoFragment.setArguments(al_recipe_bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.step_vid_fragment, stepVideoFragment)
                        .addToBackStack(null)
                        .commit();

            }


        } else {
            recipe_name = savedInstanceState.getString(TITLE); // for Navigation bar, Recipe name
//            Log.d("tag >>>", recipe_name);
        }


        // adding Toolbar for requirement, don't forget to add toolbar in xml file
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // this line show the back <- arrow
        getSupportActionBar().setTitle( recipe_name );

        toolbar1.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm1 = getSupportFragmentManager();

                    if (findViewById(R.id.step_vid_fragment) == null) {

                        if (fm1.getBackStackEntryCount() > 1) {

                            Toast.makeText(getApplication(), "if", Toast.LENGTH_SHORT).show();
//                            fm1.popBackStackImmediate();
                            // go to back DetailActivity
                            // marked the location with << .addToBackStack(STACK_DETAIL_ACTIVITY)
                            fm1.popBackStack(STACK_DETAIL_ACTIVITY, 0);
//                            finish();
                        }
                        else if(fm1.getBackStackEntryCount() > 0) {
                            Toast.makeText(getApplication(), "else if", Toast.LENGTH_SHORT).show();
                            finish(); //go back to "MainActivity" screen
                        }

                    }
                    else {

                        //go back to "MainActivity" screen
                        Toast.makeText(getApplication(), "else", Toast.LENGTH_SHORT).show();

                        finish(); // go back to MainActivity
                    }

            } // onClick()

        }); // toolbar1.setNavigationOnClickListener



    } // onCreate()




    //    implements DetailAdapter.ListItemClickListener, StepVideoFragment.ListItemClickListener
    @Override
    public void onListItemClick(List<Step> l_step, int item_index, String recipe_name_in) {

//        getSupportActionBar().setTitle(recipeName);
        FragmentManager fragmentManager = getSupportFragmentManager();

        final StepVideoFragment stepVideoFragment = new StepVideoFragment();

        Bundle bundle1 = new Bundle();

        // keys, value
        bundle1.putParcelableArrayList( SELECTED_STEPS, (ArrayList<Step>) l_step );
        bundle1.putInt( SELECTED_INDEX, item_index );

        bundle1.putString( "Title" , recipe_name_in );

        stepVideoFragment.setArguments(bundle1);

        // landscape mode
        if (findViewById(R.id.recipe_linear_layout).getTag()!= null
                && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {

            fragmentManager.beginTransaction()
                    .replace(R.id.step_vid_fragment, stepVideoFragment)
                    .addToBackStack(null) // for the Navigation bar
                    .commit();

        }
        // phone mode
        else {

            fragmentManager.beginTransaction()
                    .replace(R.id.detail_fragment, stepVideoFragment)
                    .addToBackStack(null) // for the Navigation bar
                    .commit();

        }

    } // onListItemClick


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(TITLE, recipe_name); // for Navigation bar, Recipe name
    } // onSaveInstanceState()


} // class DetailActivity

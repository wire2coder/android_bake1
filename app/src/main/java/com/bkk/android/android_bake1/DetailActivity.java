package com.bkk.android.android_bake1;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

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

public class DetailActivity extends AppCompatActivity
        implements DetailAdapter.ListItemClickListener,
        StepVideoFragment.ListItemClickListener
{



    ArrayList<Recipe> al_recipe;
    public String recipe_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);


        Bundle al_recipe_bundle = getIntent().getExtras();

        al_recipe = new ArrayList<>();
        al_recipe = al_recipe_bundle.getParcelableArrayList(SELECTED_RECIPES);


        FragmentManager fragmentManager = getSupportFragmentManager();

        final DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(al_recipe_bundle);

        // NOT A VIDEO Player Fragment
        fragmentManager.beginTransaction()
                .replace(R.id.detail_fragment, detailFragment)
                .commit();


        // tablet layout, activity_main.xml.xml
        if ( findViewById(R.id.recipe_linear_layout).getTag()!=null
                && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {


            // Video Player Fragment
            final StepVideoFragment stepVideoFragment = new StepVideoFragment();

            stepVideoFragment.setArguments(al_recipe_bundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.step_vid_fragment, stepVideoFragment)
                    .commit();

        }

    } // onCreate()




    //    implements DetailAdapter.ListItemClickListener, StepVideoFragment.ListItemClickListener
    @Override
    public void onListItemClick(List<Step> l_step, int item_index, String recipe_name_in) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        final StepVideoFragment stepVideoFragment = new StepVideoFragment();

        Bundle bundle1 = new Bundle();

        // keys, value
        bundle1.putParcelableArrayList( SELECTED_STEPS, (ArrayList<Step>) l_step );
        bundle1.putInt( SELECTED_INDEX, item_index );

        bundle1.putString( "Title" , recipe_name_in );

        stepVideoFragment.setArguments(bundle1);

        // landscape mode
        if (findViewById(R.id.recipe_linear_layout).getTag()!=null
                && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {

            fragmentManager.beginTransaction()
                    .replace(R.id.step_vid_fragment, stepVideoFragment)
                    .commit();

        } else { // phone mode

            fragmentManager.beginTransaction()
                    .replace(R.id.detail_fragment, stepVideoFragment)
                    .commit();

        }

    } // onListItemClick


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    } // onSaveInstanceState()


} // class DetailActivity

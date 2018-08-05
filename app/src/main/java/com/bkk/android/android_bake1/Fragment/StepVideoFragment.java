package com.bkk.android.android_bake1.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bkk.android.android_bake1.DetailActivity;
import com.bkk.android.android_bake1.Model.Recipe;
import com.bkk.android.android_bake1.Model.Step;
import com.bkk.android.android_bake1.R;
import com.bkk.android.android_bake1.Util.KeyUtil;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.bkk.android.android_bake1.Util.KeyUtil.SELECTED_INDEX;
import static com.bkk.android.android_bake1.Util.KeyUtil.SELECTED_RECIPES;
import static com.bkk.android.android_bake1.Util.KeyUtil.SELECTED_STEPS;
import static com.bkk.android.android_bake1.Util.KeyUtil.TITLE;
import static com.bkk.android.android_bake1.Util.KeyUtil.VIDEO_POSITION;
import static com.bkk.android.android_bake1.Util.KeyUtil.PLAYBACK_READY;


public class StepVideoFragment extends Fragment {

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private BandwidthMeter bandwidthMeter;
    private Handler mainHandler;
    private Long videoPosition;
    private boolean playBackReady;

    private ArrayList<Step> al_steps = new ArrayList<>();
    private int selected_index;


    ArrayList<Recipe> al_recipes;
    String recipe_name;
    Bundle mOutState = new Bundle();

    public StepVideoFragment() {
        // required empty constructor
    }

    private ListItemClickListener click_inteface3;


    public interface ListItemClickListener {
        void onListItemClick(List<Step> allSteps, int Index, String recipeName );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_video_fragment, container, false);

        TextView tv_step_video_fragment;
        tv_step_video_fragment = rootView.findViewById(R.id.tv_step_video_fragment);
        Button backButton = rootView.findViewById(R.id.previousStep);
        Button nextButton = rootView.findViewById(R.id.nextStep);

        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();
        click_inteface3 = (DetailActivity) getActivity();
        al_recipes = new ArrayList<>();



        if(savedInstanceState != null) {

            al_steps = savedInstanceState.getParcelableArrayList(SELECTED_STEPS);
            selected_index = savedInstanceState.getInt(SELECTED_INDEX);
            recipe_name = savedInstanceState.getString(TITLE);
            videoPosition = savedInstanceState.getLong(VIDEO_POSITION);
            playBackReady = savedInstanceState.getBoolean(PLAYBACK_READY);


        }
        // no saved data before Activity got destroyed
        // if(savedInstanceState == null)
        else {

            al_steps = getArguments().getParcelableArrayList(SELECTED_STEPS);

            if (al_steps != null) {
                al_steps = getArguments().getParcelableArrayList(SELECTED_STEPS);
                selected_index = getArguments().getInt(SELECTED_INDEX);
                recipe_name = getArguments().getString(TITLE);
                videoPosition = 0L;
                playBackReady = true;
            } else {
                al_recipes = getArguments().getParcelableArrayList(SELECTED_RECIPES);
                al_steps = (ArrayList<Step>) al_recipes.get(0).getSteps();
                selected_index = 0;
                videoPosition = 0L;
                playBackReady = true;
            }

        }



        tv_step_video_fragment.setText( al_steps.get(selected_index ).getDescription());
        tv_step_video_fragment.setVisibility(View.VISIBLE);

        simpleExoPlayerView = rootView.findViewById(R.id.playerView);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String videoURL = al_steps.get(selected_index).getVideoURL();

        // add for project requirement
        String imageURL = al_steps.get(selected_index).getThumbnailURL();

        // check for null String
        if (imageURL != null) {
            Uri uri1 = Uri.parse(imageURL).buildUpon().build();
            ImageView thumbImage = rootView.findViewById(R.id.thumbImage);
            Picasso.with(getContext()).load(uri1).into(thumbImage);
        }


        // sw600dp-port-recipe_step_detail >> sw600dp-port/step_video_fragment.xml
        if (rootView.findViewWithTag("sw600dp-port-recipe_step_detail") != null) {

            recipe_name = ((DetailActivity) getActivity()).recipe_name;
//           ((DetailActivity) getActivity()).getSupportActionBar().setTitle(recipeName);

        }





        if (!videoURL.isEmpty()) {

            makeVideoPlayer( Uri.parse( al_steps.get(selected_index).getVideoURL() ), videoPosition );

            if (rootView.findViewWithTag("sw600dp-land-recipe_step_detail")!=null) {

                getActivity().findViewById(R.id.step_vid_fragment).setLayoutParams( new LinearLayout.LayoutParams(-1,-2) );
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            }


        } else {

            simpleExoPlayer = null;
            simpleExoPlayerView.setForeground(ContextCompat.getDrawable(
                    getContext(), R.drawable.novideopic));

            simpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        }
        // YOU WANT THIS




        backButton.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view) {

                if ( al_steps.get(selected_index).getId() > 0 ) {

                    if (simpleExoPlayer != null){
                        simpleExoPlayer.stop();
                    }

                    // void onListItemClick( List<Step> allSteps, int Index, String recipeName);
                    click_inteface3.onListItemClick(al_steps
                            ,al_steps.get(selected_index).getId() - 1
                            , recipe_name); // >> Nutella Pie
                }
                else {
                    Toast.makeText(getActivity(),"You are at the First step", Toast.LENGTH_SHORT).show();

                }

            }});


        nextButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                int lastIndex = al_steps.size()-1;

                if (al_steps.get(selected_index).getId() < al_steps.get(lastIndex).getId()) {

                    if (simpleExoPlayer != null){
                        simpleExoPlayer.stop();
                    }

                    click_inteface3.onListItemClick(al_steps
                            ,al_steps.get(selected_index).getId() + 1
                            , recipe_name);

                } else {

                    Toast.makeText(getContext(),"You are at the Last step", Toast.LENGTH_SHORT).show();

                }

            }});


        return rootView;
    }




    private void makeVideoPlayer(Uri uri1, Long videoPosition1) {

        if (simpleExoPlayer == null) {

            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "Bake");

            MediaSource mediaSource = new ExtractorMediaSource(uri1
                    , new DefaultDataSourceFactory(getContext()
                    , userAgent)
                    , new DefaultExtractorsFactory()
                    , null
                    , null);

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.seekTo(videoPosition1);
            simpleExoPlayer.setPlayWhenReady(playBackReady); // >> playBackReady is FALSE when you pause the Video
//            simpleExoPlayer.setPlayWhenReady(true); // this makes the player plays right away
        }

    }

    private void releasePlayer() {
        videoPosition = simpleExoPlayer.getCurrentPosition();
        playBackReady = simpleExoPlayer.getPlayWhenReady();

        simpleExoPlayer.stop();
        simpleExoPlayer.release();

        if(simpleExoPlayer !=null) {
            simpleExoPlayer = null;
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();

        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (simpleExoPlayer != null) {

            videoPosition = simpleExoPlayer.getCurrentPosition();
            playBackReady = simpleExoPlayer.getPlayWhenReady();

            simpleExoPlayer.stop();
//            simpleExoPlayer.release(); // remove per Reviewer suggestion
            simpleExoPlayer = null;
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        if (simpleExoPlayer!=null) {

            videoPosition = simpleExoPlayer.getCurrentPosition();
            playBackReady = simpleExoPlayer.getPlayWhenReady();

            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }


    @Override
    public void onPause() {
        super.onPause();


        if (simpleExoPlayer!=null) {

            videoPosition = simpleExoPlayer.getCurrentPosition();
            playBackReady = simpleExoPlayer.getPlayWhenReady();

            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }



    /*
    @Override
    public void onDestroy(){
        super.onDestroy();

        onSaveInstanceState(mOutState);

        if ( simpleExoPlayer != null ) {
            releasePlayer();
        }

    }


    @Override
    public void onStop() {
        super.onStop();

        if ( simpleExoPlayer != null && Util.SDK_INT <= 23 ) {
            releasePlayer();
        }

    }


    @Override
    public void onPause(){
        super.onPause();

        onSaveInstanceState(mOutState);

        if ( Util.SDK_INT <= 23 ) {
            releasePlayer();
        }

    }



    @Override
    public void onResume() {
        super.onResume();

        if( (Util.SDK_INT <=23 || simpleExoPlayer ==null) ) {

            String videoURL = al_steps.get(selected_index).getVideoURL();

            if(videoURL !=null){
                makeVideoPlayer(Uri.parse(videoURL), videoPosition);

            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        if( Util.SDK_INT> 23 ) {

            String videoURL = al_steps.get(selected_index).getVideoURL();

            if(videoURL != null) {
                makeVideoPlayer(Uri.parse(videoURL), videoPosition);
            }

        }

    }
    */


    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putParcelableArrayList(SELECTED_STEPS, al_steps);
        currentState.putInt(SELECTED_INDEX, selected_index);
        currentState.putString(TITLE, recipe_name);

        currentState.putLong(VIDEO_POSITION, videoPosition);
        currentState.putBoolean(PLAYBACK_READY, playBackReady);

    } // onSaveInstanceState()


} // class StepVideoFragment

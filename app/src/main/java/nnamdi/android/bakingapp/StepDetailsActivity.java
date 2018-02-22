package nnamdi.android.bakingapp;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.models.Recipe;
import nnamdi.android.bakingapp.models.Step;
import nnamdi.android.bakingapp.utils.LoadRecipeData;

public class StepDetailsActivity extends AppCompatActivity {

    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tv_steps_full_description) TextView mDescriptionTV;
    @BindView(R.id.btn_step_details_back) ImageButton mBackBtn;
    @BindView(R.id.btn_step_details_forward) ImageButton mForwardBtn;

    private SimpleExoPlayer mExoPlayer;
    private Boolean IS_PLAYER_VISIBLE ;
    private Step mStep;
    private SharedPreferences pref;
    private ArrayList<Step> stepsArray;


    private final String STEPS_EXTRA = "step";
    private final String RECIPE_PREFERENCE_KEY = "recipe_key";

    private final String STEPS_SIZE_KEY = "step_size";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        mStep = intent.getParcelableExtra(STEPS_EXTRA);

        setTitle(mStep.getShortDescription());

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mDescriptionTV.getLayoutParams();

        mDescriptionTV.setText(mStep.getDescription());


        if(mStep.getVideoURL().isEmpty()){
            mPlayerView.setVisibility(View.INVISIBLE);
            layoutParams.setMargins(0, 0, 0, 0);
            mDescriptionTV.setLayoutParams(layoutParams);
            IS_PLAYER_VISIBLE = false;

        }else{
            initializePlayer(Uri.parse(mStep.getVideoURL()));
            IS_PLAYER_VISIBLE = true;
        }

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        int stepsListSize  =  pref.getInt(STEPS_SIZE_KEY, 0);

        if(mStep.getId() == 0){
            mBackBtn.setVisibility(View.INVISIBLE);
        }

        if(stepsListSize-1 == mStep.getId()){
            mForwardBtn.setVisibility(View.INVISIBLE);
        }

        String recipeJsonString =  pref.getString(RECIPE_PREFERENCE_KEY, null);
        Recipe recipe = new LoadRecipeData().loadRecipeFromSharedPreferences(recipeJsonString);
        stepsArray = recipe.getSteps();

    }

    public void initializePlayer(Uri mediaUri){
        if(mExoPlayer == null){
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            mExoPlayer =
                    ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            mPlayerView.setPlayer(mExoPlayer);


            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "BakingApp"), defaultBandwidthMeter);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(IS_PLAYER_VISIBLE){
            releasePlayer();
        }
    }

    public void onForwardClick(View v){
        for(Step s: stepsArray){
            if(s.getId() == mStep.getId() +1){
                Intent intent = new Intent(this, StepDetailsActivity.class);
                intent.putExtra(STEPS_EXTRA, s);
                this.startActivity(intent);
            }
        }


    }

    public void onBackClick(View v){
        for(Step s: stepsArray){
            if(s.getId() == mStep.getId() -1){
                Intent intent = new Intent(this, StepDetailsActivity.class);
                intent.putExtra(STEPS_EXTRA, s);
                this.startActivity(intent);
            }
        }
    }
}

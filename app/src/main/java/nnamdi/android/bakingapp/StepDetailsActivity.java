package nnamdi.android.bakingapp;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.models.Step;

public class StepDetailsActivity extends AppCompatActivity {

    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tv_steps_full_description) TextView mDescriptionTV;

    private SimpleExoPlayer mExoplayer;
    private Boolean IS_PLAYER_VISIBLE ;


    private final String STEPS_EXTRA = "step";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Step step = intent.getParcelableExtra(STEPS_EXTRA);

        setTitle(step.getShortDescription());

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mDescriptionTV.getLayoutParams();

        mDescriptionTV.setText(step.getDescription());

        if(step.getVideoURL().isEmpty()){
            mPlayerView.setVisibility(View.INVISIBLE);
            layoutParams.setMargins(0, 0, 0, 0);
            mDescriptionTV.setLayoutParams(layoutParams);
            IS_PLAYER_VISIBLE = false;

        }else{
            initializePlayer(Uri.parse(step.getVideoURL()));
            IS_PLAYER_VISIBLE = true;
        }

    }

    public void initializePlayer(Uri mediaUri){
        if(mExoplayer == null){
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            mExoplayer =
                    ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            mPlayerView.setPlayer(mExoplayer);


            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "BakingApp"), defaultBandwidthMeter);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            mExoplayer.prepare(videoSource);
            mExoplayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mExoplayer.stop();
        mExoplayer.release();
        mExoplayer = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(IS_PLAYER_VISIBLE){
            releasePlayer();
        }
    }
}

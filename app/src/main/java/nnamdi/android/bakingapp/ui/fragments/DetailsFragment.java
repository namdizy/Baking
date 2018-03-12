package nnamdi.android.bakingapp.ui.fragments;

import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.R;
import nnamdi.android.bakingapp.models.Step;


public class DetailsFragment extends Fragment {

    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tv_steps_full_description) TextView mDescriptionTV;

    private static final String STEP_ITEM = "step_item";
    private static final String PLAYER_POSITION = "player_position";
    private Boolean IS_PLAYER_VISIBLE ;
    private SimpleExoPlayer mExoPlayer;


    private Step mStepItem;
    private long playerPosition;


    public DetailsFragment(){

    }


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);


        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mDescriptionTV.getLayoutParams();
        mDescriptionTV.setText(mStepItem.getDescription());
        if(mStepItem.getVideoURL().isEmpty()){
            mPlayerView.setVisibility(View.INVISIBLE);
            layoutParams.setMargins(0, 0, 0, 0);
            mDescriptionTV.setLayoutParams(layoutParams);
            IS_PLAYER_VISIBLE = false;

        }else{
            if (getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE) {
                mDescriptionTV.setVisibility(View.GONE);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mPlayerView.getLayoutParams();
                params.width=params.MATCH_PARENT;
                params.height=params.MATCH_PARENT;
                mPlayerView.setLayoutParams(params);
            }

            initializePlayer(Uri.parse(mStepItem.getVideoURL()));
            IS_PLAYER_VISIBLE = true;
        }


        return view;
    }


    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(IS_PLAYER_VISIBLE){
            playerPosition = mExoPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    public void initializePlayer(Uri mediaUri){
        if(mExoPlayer == null){
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            mExoPlayer =
                    ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            mPlayerView.setPlayer(mExoPlayer);

            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), "BakingApp"), defaultBandwidthMeter);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            mExoPlayer.seekTo(playerPosition);
            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    public Step getmStepItem() {
        return mStepItem;
    }

    public void setmStepItem(Step mStepItem) {
        this.mStepItem = mStepItem;
    }

    public long getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(long playerPosition) {
        this.playerPosition = playerPosition;
    }

}

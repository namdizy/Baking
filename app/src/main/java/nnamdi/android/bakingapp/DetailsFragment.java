package nnamdi.android.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.models.Step;


public class DetailsFragment extends Fragment {

    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tv_steps_full_description) TextView mDescriptionTV;

    private static final String STEP_ITEM = "step_item";
    private Boolean IS_PLAYER_VISIBLE ;
    private SimpleExoPlayer mExoPlayer;
    private Context mContext;

    private Step mStepItem;

    public DetailsFragment(){

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param step Step .
     * @return A new instance of fragment DetailsFragment.
     */
    public static DetailsFragment newInstance(Step step) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(STEP_ITEM, step);
        fragment.setArguments(args);
        return fragment;
    }

    public void setContext(Context context){
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepItem = getArguments().getParcelable(STEP_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
    public void onDestroyView() {
        super.onDestroyView();
        if(IS_PLAYER_VISIBLE){
            releasePlayer();
        }
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
                    ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
            mPlayerView.setPlayer(mExoPlayer);

            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                    Util.getUserAgent(mContext, "BakingApp"), defaultBandwidthMeter);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
}

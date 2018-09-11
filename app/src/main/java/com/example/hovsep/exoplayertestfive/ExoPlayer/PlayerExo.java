package com.example.hovsep.exoplayertestfive.ExoPlayer;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.example.hovsep.exoplayertestfive.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.example.hovsep.exoplayertestfive.ExoPlayer.Utils.DismissDialog;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.Utils.DismissPopup;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.Utils.ErrorPlayingVideo;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.Utils.LanguagesPopUp;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.Utils.closeFullscreenDialog;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.Utils.initFullscreenButton;

public class PlayerExo {

    private static final String TAG = "PlayerExo";
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final LoadControl loadControl = new DefaultLoadControl();

    private static PlayerExo ourInstance;

    private int userLayoutId;
    private int userPlayerViewId;
    private int userProgressBarId;

    static SimpleExoPlayer player;
    static PlayerView playerView;
    static LoopingMediaSource loopingSource;
    static Activity mainActivity;
    static ConstraintLayout mainLayout;
    static PlayerAnalyticsListener mExoPlayerAnalyticsListener;
    static String bufferingCompleted;
    static DefaultTrackSelector trackSelector;
    private ArrayList<String > hlsStreamLanguages;
    private Set<String> hlsStreamLanguageSet = new HashSet<>();

    public ArrayList<String> getHlsStreamLanguages() {
        return hlsStreamLanguages;
    }

    public void setHlsStreamLanguages(ArrayList<String> hlsStreamLanguages) {
        this.hlsStreamLanguages = hlsStreamLanguages;
    }

    public Set<String> getHlsStreamLanguageSet() {
        return hlsStreamLanguageSet;
    }

    public void setHlsStreamLanguageSet(Set<String> hlsStreamLanguageSet) {
        this.hlsStreamLanguageSet = hlsStreamLanguageSet;
    }

    static long mExoPlayerPosition;

    static int userAlertDialogStyle;
    static int userPopupLayout;
    static int userPopupTextViewId;
    static int languagesPopupId;
    static int languagesListViewId;
    static int userIconFullscreenSkrink;
    static int userIconFullscreenExpand;

    static View viewPopup;
    static View viewLanguages;
    static PopupWindow popupExit;
    static ProgressBar progressBar;
    static ProgressDialog dialog;

    static PopupWindow popupLanguages;

    static Dialog mFullScreenDialog;
    static boolean mExoPlayerFullscreen = false;
    static ImageButton mFullScreenIcon;
    static FrameLayout.LayoutParams mainLayoutDefaultParams;

    static int userLanguageModelLayoutId;
    static int userLanguageModelTextViewId;
    static int currentVideoSegmentDuration = 0;
    static boolean changedInternetConnection = false;

    static int currentVideoHeightSize;
    static int currentVideoWidthSize;

    public static PlayerExo newInstance() {
        if (ourInstance == null) {
            ourInstance = new PlayerExo();
        }
        return ourInstance;
    }

    public void initiatePlayer(int yourLayoutId, int yourPlayerViewId, int yourProgressId,
                               int yourAlertDialogStyle, int yourPopupLayout, int yourPopupTextViewId,
                               int yourLanguagesPopupId, int yourLanguagesListViewId,
                               int yourIconFullscreenSkrink,
                               int yourIconFullscreenExpand, int yourLanguageModelLayoutId,
                               int yourLanguageModelTextViewId, Activity yourActivity) {
        userLayoutId = yourLayoutId;
        userPlayerViewId = yourPlayerViewId;
        userProgressBarId = yourProgressId;
        userAlertDialogStyle = yourAlertDialogStyle;
        userPopupLayout = yourPopupLayout;
        userPopupTextViewId = yourPopupTextViewId;
        mainActivity = yourActivity;
        languagesPopupId = yourLanguagesPopupId;
        languagesListViewId = yourLanguagesListViewId;
        userIconFullscreenSkrink = yourIconFullscreenSkrink;
        userIconFullscreenExpand = yourIconFullscreenExpand;
        userLanguageModelLayoutId = yourLanguageModelLayoutId;
        userLanguageModelTextViewId = yourLanguageModelTextViewId;
        CreatePlayer();
    }

    private void CreatePlayer() {
        playerView = new PlayerView(mainActivity);
        playerView = (PlayerView) mainActivity.findViewById(userPlayerViewId);
        mainLayout = (ConstraintLayout) mainActivity.findViewById(userLayoutId);
        progressBar = (ProgressBar) mainLayout.findViewById(userProgressBarId);
        mExoPlayerAnalyticsListener = new PlayerAnalyticsListener();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(mainActivity, trackSelector);
        playerView.setUseController(true);
        playerView.requestFocus();
        playerView.setPlayer(player);


        PlaybackControlView controlView = playerView.findViewById(R.id.exo_controller);
        ImageButton languagesBtn = controlView.findViewById(R.id.exo_languages_btn);
        languagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguagesPopUp();
            }
        });

        initFullscreenButton();

        mFullScreenDialog = new Dialog(mainActivity, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    public void initiateNewVideo(String mContentUri, long position) {
        DismissPopup();
        DismissDialog();
        if (player != null) {
            player.stop();
            if (position != 0L) {
                player.seekTo(position);
            }
            PlayVideoHls(mContentUri);
        } else {
            Log.d(TAG, "Your exo player is null");
        }
    }

    private void PlayVideoHls(String uri) {
        player.stop();
        progressBar.setVisibility(View.VISIBLE);
        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(mainActivity,
                Util.getUserAgent(mainActivity, "exoplayer2example"), bandwidthMeterA);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource;
        try{
            videoSource = MediaSourceHls.newInstance().CreateMediaSource(
                    dataSourceFactory,uri);
            final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
            LoopingVideoStream(loopingSource);
        } catch (UnsupportedOperationException e){
            progressBar.setVisibility(View.GONE);
            DismissDialog();
            ErrorPlayingVideo("" + e.getMessage());
        }
    }

    public void LoopingVideoStream(LoopingMediaSource loopingMediaSource) {
        loopingSource = loopingMediaSource;
        player.prepare(loopingSource);
        player.setPlayWhenReady(true);
        player.addAnalyticsListener(mExoPlayerAnalyticsListener);
    }

    public void initiatePlayerControls(int controlButtonsColorId,int controlButtonsBacgroundDrawable){
        PlaybackControlView controlView = playerView.findViewById(R.id.exo_controller);
        ImageButton playExoPlayerBtn = controlView.findViewById(R.id.exo_play);
        ImageButton pauseExoPlayerBtn = controlView.findViewById(R.id.exo_pause);
        ImageButton ffwdExoPlayerBtn = controlView.findViewById(R.id.exo_ffwd);
        ImageButton nextExoPlayerBtn = controlView.findViewById(R.id.exo_next);
        ImageButton prewExoPlayerBtn = controlView.findViewById(R.id.exo_prev);
        ImageButton rewExoPlayerBtn = controlView.findViewById(R.id.exo_rew);

        playExoPlayerBtn.setColorFilter(controlButtonsColorId);
        playExoPlayerBtn.setBackgroundResource(controlButtonsBacgroundDrawable);

        pauseExoPlayerBtn.setColorFilter(controlButtonsColorId);
        pauseExoPlayerBtn.setBackgroundResource(controlButtonsBacgroundDrawable);

        ffwdExoPlayerBtn.setColorFilter(controlButtonsColorId);
        ffwdExoPlayerBtn.setBackgroundResource(controlButtonsBacgroundDrawable);

        nextExoPlayerBtn.setColorFilter(controlButtonsColorId);
        nextExoPlayerBtn.setBackgroundResource(controlButtonsBacgroundDrawable);

        prewExoPlayerBtn.setColorFilter(controlButtonsColorId);
        prewExoPlayerBtn.setBackgroundResource(controlButtonsBacgroundDrawable);

        rewExoPlayerBtn.setColorFilter(controlButtonsColorId);
        rewExoPlayerBtn.setBackgroundResource(controlButtonsBacgroundDrawable);
    }
}
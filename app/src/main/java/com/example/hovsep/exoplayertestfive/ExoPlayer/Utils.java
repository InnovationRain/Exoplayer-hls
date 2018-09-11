package com.example.hovsep.exoplayertestfive.ExoPlayer;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.hovsep.exoplayertestfive.ExoPlayer.InterfacesPackage.VideoUtils;
import com.example.hovsep.exoplayertestfive.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.bufferingCompleted;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.currentVideoHeightSize;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.currentVideoSegmentDuration;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.currentVideoWidthSize;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.dialog;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.languagesListViewId;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.languagesPopupId;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.mExoPlayerAnalyticsListener;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.mExoPlayerFullscreen;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.mExoPlayerPosition;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.mFullScreenIcon;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.mainActivity;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.mainLayout;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.mainLayoutDefaultParams;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.player;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.playerView;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.popupExit;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.popupLanguages;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.trackSelector;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.userIconFullscreenExpand;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.userIconFullscreenSkrink;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.userLanguageModelLayoutId;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.userLanguageModelTextViewId;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.userPopupLayout;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.userPopupTextViewId;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.viewLanguages;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.viewPopup;

public class Utils implements VideoUtils{

    private static final String TAG = "Utils";
    private static final String SetPlaybackParametersException = "Attempt to invoke virtual method " +
            "'void com.google.android.exoplayer2.ui.PlayerView.SetPlaybackParameters(" +
            "com.google.android.exoplayer2.PlaybackParameters playbackParameters)' on a null object reference";
    private static final String SetAudioAttributesException = "Attempt to invoke virtual method " +
            "'void com.google.android.exoplayer2.ui.PlayerView.SetAudioAttributes(" +
            "com.google.android.exoplayer2.audio.AudioAttributes audioAttributes)' on a null object reference";
    private static final String SendMessagesException = "Attempt to invoke virtual method " +
            "'void com.google.android.exoplayer2.ui.PlayerView.SendMessages(" +
            "com.google.android.exoplayer2.ExoPlayer.ExoPlayerMessage... messages)' on a null object reference";
    private static final String SeekToDefaultPositionTwoException = "Attempt to invoke virtual method " +
            "'void com.google.android.exoplayer2.ui.PlayerView.SeekToDefaultPosition(" +
            "int windowIndex)' on a null object reference";
    private static final String SeekToDefaultPositionOneException = "Attempt to invoke virtual method " +
            "'void com.google.android.exoplayer2.ui.PlayerView.SeekToDefaultPosition()' " +
            "on a null object reference";
    private static final String SeekToOneException = "Attempt to invoke virtual method " +
            "'void com.google.android.exoplayer2.ui.PlayerView.SeekTo(int windowIndex, long positionMs)'" +
            " on a null object reference";
    private static final String SeekToTwoException = "Attempt to invoke virtual method " +
            "'void com.google.android.exoplayer2.ui.PlayerView.SeekTo(long positionMs)' " +
            "on a null object reference";
    private static final String SetPlayWhenReadyException = "Attempt to invoke virtual method " +
            "'void com.google.android.exoplayer2.ui.PlayerView.SetPlayWhenReady(boolean playWhenReady)'" +
            " on a null object reference";
    private static final String SetRepeatModeException = "Attempt to invoke virtual method " +
            "'void com.google.android.exoplayer2.ui.PlayerView.SetRepeatMode(int repeatMode)' " +
            "on a null object reference";
    private static final String SetSeekParametersException = "Attempt to invoke virtual method " +
            "'void com.google.android.exoplayer2.ui.PlayerView.SetSeekParameters(@Nullable " +
            "com.google.android.exoplayer2.SeekParameters seekParameters)' on a null object reference";
    private static final String SetShuffleModeEnabledException = "Attempt to invoke virtual method 'void " +
            "com.google.android.exoplayer2.ui.PlayerView.SetShuffleModeEnabled(" +
            "boolean shuffleModeEnabled)' on a null object reference";
    private static final String SetVideoScalingModeException = "Attempt to invoke virtual method 'void " +
            "com.google.android.exoplayer2.ui.PlayerView.SetVideoScalingMode(" +
            "int videoScalingMode)' on a null object reference";
    private static final String SetVideoSurfaceException = "Attempt to invoke virtual method 'void " +
            "com.google.android.exoplayer2.ui.PlayerView.SetVideoSurface(" +
            "android.view.Surface surface)' on a null object reference";
    private static final String SetVideoSurfaceHolderException = "Attempt to invoke virtual method 'void " +
            "com.google.android.exoplayer2.ui.PlayerView.SetVideoSurfaceHolder(" +
            "android.view.SurfaceHolder surfaceHolder)' on a null object reference";
    private static final String SetVideoSurfaceViewException = "Attempt to invoke virtual method 'void " +
            "com.google.android.exoplayer2.ui.PlayerView.SetVideoSurfaceHolder(" +
            "android.view.SurfaceView surfaceView)' on a null object reference";
    private static final String SetVideoTextureViewException = "Attempt to invoke virtual method 'void " +
            "com.google.android.exoplayer2.ui.PlayerView.SetVideoTextureView(" +
            "android.view.TextureView textureView)' on a null object reference";
    private static final String SetVolumeException = "Attempt to invoke virtual method 'void " +
            "com.google.android.exoplayer2.ui.PlayerView.SetVolume(" +
            "float audioVolume)' on a null object reference";
    private static final String StopOneException = "Attempt to invoke virtual method 'void " +
            "com.google.android.exoplayer2.ui.PlayerView.Stop()' on a null object reference";
    private static final String StopTwoException = "Attempt to invoke virtual method 'void " +
            "com.google.android.exoplayer2.ui.PlayerView.Stop(boolean reset)' on a null object reference";
    private static Utils ourInstance;
    private static final FrameLayout.LayoutParams fullScreanParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
    );

    public static Utils newInstance(){
        if(ourInstance == null){
            ourInstance = new Utils();
        }
        return ourInstance;
    }

    public void releasePlayer() {
        if (player != null) {
            if (player.getPlayWhenReady() && player.getPlaybackState() != Player.STATE_IDLE) {
                mExoPlayerPosition = player.getCurrentPosition();
            }
            if(mExoPlayerAnalyticsListener !=null){
                player.removeAnalyticsListener(mExoPlayerAnalyticsListener);
            }
            player.stop();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    public ArrayList<String> GetLanguages(){
        if(bufferingCompleted != null && bufferingCompleted.equals("Complete")) {
            Log.d(TAG, "Languages - " + PlayerExo.newInstance().getHlsStreamLanguages().toString());
            if(PlayerExo.newInstance().getHlsStreamLanguages() != null &&
                    PlayerExo.newInstance().getHlsStreamLanguages().size() > 0){
                return PlayerExo.newInstance().getHlsStreamLanguages();
            } else{
                return null;
            }
        } else {
            throw new RuntimeException("Buffering Not Ended !!!, Wait");
        }
    }

    public void SetLanguage(String language){
        if(PlayerExo.newInstance().getHlsStreamLanguages().size() > 0){
            if(searchLanguage(language)){
                trackSelector.setParameters(
                        trackSelector
                                .buildUponParameters()
                                .setMaxVideoSizeSd()
                                .setPreferredAudioLanguage(language));
            } else {
                throw new NullPointerException("Not Found");
            }
        }

    }

    public static boolean searchLanguage(String searchStr) {
        if(PlayerExo.newInstance().getHlsStreamLanguageSet().contains(searchStr)){
            return true;
        } else {
            return false;
        }
    }

    static void ErrorPlayingVideo(String message) {
        LayoutInflater inflater = (LayoutInflater) mainLayout.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewPopup = inflater.inflate(userPopupLayout, null);
        TextView textView = viewPopup.findViewById(userPopupTextViewId);
        textView.setText(message);
        DismissPopup();
        Point size = new Point();
        Display display = mainActivity.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        popupExit = new PopupWindow(viewPopup, width, height, false);
        popupExit.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DismissPopup();
            }
        }, 3000);
    }

    static void LanguagesPopUp() {
        if(PlayerExo.newInstance().getHlsStreamLanguageSet() != null &&
                PlayerExo.newInstance().getHlsStreamLanguageSet().size() > 0){
            DismissPopup();
            LayoutInflater inflater = (LayoutInflater) mainLayout.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewLanguages = inflater.inflate(languagesPopupId, null);
            ListView listView = viewLanguages.findViewById(languagesListViewId);
            ArrayList<String> tempLanguages = new ArrayList<>();
            tempLanguages.addAll(PlayerExo.newInstance().getHlsStreamLanguageSet());
            PlayerExo.newInstance().setHlsStreamLanguages(tempLanguages);
            ArrayList<LanguagesModel> languagesModels = new ArrayList<>();
            for(int i = 0;i < PlayerExo.newInstance().getHlsStreamLanguages().size();i++){
                languagesModels.add(new LanguagesModel(
                        PlayerExo.newInstance().getHlsStreamLanguages().get(i)
                ));
            }
            PopupLanguagesAdapter adapter = new PopupLanguagesAdapter(
                    languagesModels, mainActivity,
                    userLanguageModelLayoutId,userLanguageModelTextViewId);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String language = PlayerExo.newInstance().getHlsStreamLanguages().get(i);
                    Utils.newInstance().SetLanguage(language);
                    DismissPopup();
                }
            });
            popupLanguages = new PopupWindow(viewLanguages, 200, 200, false);
            popupLanguages.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
            popupLanguages.setFocusable(true);
            popupLanguages.update();
        }
    }

    static void initFullscreenButton() {
        PlaybackControlView controlView = playerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }

    static void openFullscreenDialog() {
        mainLayoutDefaultParams = (FrameLayout.LayoutParams) mainLayout.getLayoutParams();
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        mainLayout.setLayoutParams(fullScreanParams);
        mExoPlayerFullscreen = true;
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(mainActivity, userIconFullscreenSkrink));
    }

    static void closeFullscreenDialog() {
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        mainLayout.setLayoutParams(mainLayoutDefaultParams);
        mExoPlayerFullscreen = false;
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(mainActivity, userIconFullscreenExpand));
    }

    static void DismissPopup() {
        if (popupExit != null) {
            popupExit.dismiss();
        }
        if(popupLanguages != null){
            popupLanguages.dismiss();
        }
    }

    static void DismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public int AudioSessionId() {
        if (player != null) {
            return player.getAudioSessionId();
        } else {
            return -1;
        }
    }

    @Override
    public int BufferedPercentage() {
        if (player != null) {
            return player.getBufferedPercentage();
        } else {
            return -1;
        }
    }

    @Override
    public int CurrentAdGroupIndex() {
        if (player != null) {
            return player.getCurrentAdGroupIndex();
        } else {
            return -1;
        }
    }

    @Override
    public int CurrentAdIndexInAdGroup() {
        if (player != null) {
            return player.getCurrentAdIndexInAdGroup();
        } else {
            return -1;
        }
    }

    @Override
    public int PreviousWindowIndex() {
        if (player != null) {
            return player.getPreviousWindowIndex();
        } else {
            return -1;
        }
    }

    @Override
    public int RendererCount() {
        if (player != null) {
            return player.getRendererCount();
        } else {
            return -1;
        }
    }

    @Override
    public int RendererType(int index) {
        if (player != null) {
            return player.getRendererType(index);
        } else {
            return -1;
        }
    }

    @Override
    public int RepeatMode() {
        if (player != null) {
            return player.getRepeatMode();
        } else {
            return -1;
        }
    }

    @Override
    public int CurrentPeriodIndex() {
        if (player != null) {
            return player.getCurrentPeriodIndex();
        } else {
            return -1;
        }
    }

    @Override
    public int CurrentWindowIndex() {
        if (player != null) {
            return player.getCurrentWindowIndex();
        } else {
            return -1;
        }
    }

    @Override
    public int NextWindowIndex() {
        if (player != null) {
            return player.getNextWindowIndex();
        } else {
            return -1;
        }
    }

    @Override
    public int PlaybackState() {
        if (player != null) {
            return player.getPlaybackState();
        } else {
            return -1;
        }
    }

    @Override
    public int VideoScalingMode() {
        if (player != null) {
            return player.getVideoScalingMode();
        } else {
            return -1;
        }
    }

    @Override
    public int GetCurrentSegmentDuration() {
        if (player != null) {
            return currentVideoSegmentDuration;
        } else {
            return -1;
        }
    }

    @Override
    public int GetCurrentVideoWidth() {
        if (player != null) {
            return currentVideoWidthSize;
        } else {
            return -1;
        }
    }

    @Override
    public int GetCurrentVideoHeight() {
        if (player != null) {
            return currentVideoHeightSize;
        } else {
            return -1;
        }
    }

    @Override
    public long CurrentPosition() {
        if (player != null) {
            return player.getCurrentPosition();
        } else {
            return -1L;
        }
    }

    @Override
    public long CurrentDuration() {
        if (player != null) {
            return player.getDuration();
        } else {
            return -1L;
        }
    }

    @Override
    public long BufferedPosition() {
        if (player != null) {
            return player.getBufferedPosition();
        } else {
            return -1L;
        }
    }

    @Override
    public long ContentPosition() {
        if (player != null) {
            return player.getContentPosition();
        } else {
            return -1L;
        }
    }

    @Override
    public boolean PlayWhenReady() {
        if(player != null){
            return player.getPlayWhenReady();
        } else {
            return false;
        }
    }

    @Override
    public boolean IsCurrentWindowDynamic() {
        if(player != null){
            return player.isCurrentWindowDynamic();
        } else {
            return false;
        }
    }

    @Override
    public boolean IsCurrentWindowSeekable() {
        if(player != null){
            return player.isCurrentWindowSeekable();
        } else {
            return false;
        }
    }

    @Override
    public boolean IsLoading() {
        if(player != null){
            return player.isLoading();
        } else {
            return false;
        }
    }

    @Override
    public boolean IsPlayingAd() {
        if(player != null){
            return player.isPlayingAd();
        } else {
            return false;
        }
    }

    @Override
    public boolean ShuffleModeEnabled() {
        if(player != null){
            return player.getShuffleModeEnabled();
        } else {
            return false;
        }
    }

    @Override
    public float Volume() {
        if(player != null){
            return player.getVolume();
        } else {
            return -1F;
        }
    }

    @Override
    public void SeekTo(int windowIndex, long positionMs) {
        if(player != null){
            player.seekTo(windowIndex,positionMs);
        } else {
            throw new NullPointerException(SeekToOneException);
        }
    }

    @Override
    public void SeekTo(long positionMs) {
        if(player != null){
            player.seekTo(positionMs);
        } else {
            throw new NullPointerException(SeekToTwoException);
        }
    }

    @Override
    public void SeekToDefaultPosition() {
        if(player != null){
            player.seekToDefaultPosition();
        } else {
            throw new NullPointerException(SeekToDefaultPositionOneException);
        }
    }

    @Override
    public void SeekToDefaultPosition(int windowIndex) {
        if(player != null){
            player.seekToDefaultPosition();
        } else {
            throw new NullPointerException(SeekToDefaultPositionTwoException);
        }
    }

    @Override
    public void SendMessages(ExoPlayer.ExoPlayerMessage... messages) {
        if(player != null){
            player.sendMessages(messages);
        } else {
            throw new NullPointerException(SendMessagesException);
        }
    }

    @Override
    public void SetAudioAttributes(AudioAttributes audioAttributes) {
        if(player != null){
            player.setAudioAttributes(audioAttributes);
        } else {
            throw new NullPointerException(SetAudioAttributesException);
        }
    }

    @Override
    public void SetPlaybackParameters(@Nullable PlaybackParameters playbackParameters) {
        if(player != null){
            player.setPlaybackParameters(playbackParameters);
        } else {
            throw new NullPointerException(SetPlaybackParametersException);
        }
    }

    @Override
    public void SetPlayWhenReady(boolean playWhenReady) {
        if(player != null){
            player.setPlayWhenReady(playWhenReady);
        } else {
            throw new NullPointerException(SetPlayWhenReadyException);
        }
    }

    @Override
    public void SetRepeatMode(int repeatMode) {
        if(player != null){
            player.setRepeatMode(repeatMode);
        } else {
            throw new NullPointerException(SetRepeatModeException);
        }
    }

    @Override
    public void SetSeekParameters(@Nullable SeekParameters seekParameters) {
        if(player != null){
            player.setSeekParameters(seekParameters);
        } else {
            throw new NullPointerException(SetSeekParametersException);
        }
    }

    @Override
    public void SetShuffleModeEnabled(boolean shuffleModeEnabled) {
        if(player != null){
            player.setShuffleModeEnabled(shuffleModeEnabled);
        } else {
            throw new NullPointerException(SetShuffleModeEnabledException);
        }
    }

    @Override
    public void SetVideoScalingMode(int videoScalingMode) {
        if(player != null){
            player.setVideoScalingMode(videoScalingMode);
        } else {
            throw new NullPointerException(SetVideoScalingModeException);
        }
    }

    @Override
    public void SetVideoSurface(Surface surface) {
        if(player != null){
            player.setVideoSurface(surface);
        } else {
            throw new NullPointerException(SetVideoSurfaceException);
        }
    }

    @Override
    public void SetVideoSurfaceHolder(SurfaceHolder surfaceHolder) {
        if(player != null){
            player.setVideoSurfaceHolder(surfaceHolder);
        } else {
            throw new NullPointerException(SetVideoSurfaceHolderException);
        }
    }

    @Override
    public void SetVideoSurfaceView(SurfaceView surfaceView) {
        if(player != null){
            player.setVideoSurfaceView(surfaceView);
        } else {
            throw new NullPointerException(SetVideoSurfaceViewException);
        }
    }

    @Override
    public void SetVideoTextureView(TextureView textureView) {
        if(player != null){
            player.setVideoTextureView(textureView);
        } else {
            throw new NullPointerException(SetVideoTextureViewException);
        }
    }

    @Override
    public void SetVolume(float audioVolume) {
        if(player != null){
            player.setVolume(audioVolume);
        } else {
            throw new NullPointerException(SetVolumeException);
        }
    }

    @Override
    public void Stop() {
        if(player != null){
            player.stop();
        } else {
            throw new NullPointerException(StopOneException);
        }
    }

    @Override
    public void Stop(boolean reset) {
        if(player != null){
            player.stop(reset);
        } else {
            throw new NullPointerException(StopTwoException);
        }
    }

    @Override
    public TrackSelectionArray CurrentTrackSelections() {
        if (player != null) {
            return player.getCurrentTrackSelections();
        } else {
            return null;
        }
    }

    @Override
    public Timeline CurrentTimeline() {
        if (player != null) {
            return player.getCurrentTimeline();
        } else {
            return null;
        }
    }

    @Override
    public DecoderCounters AudioDecoderCounters() {
        if (player != null) {
            return player.getAudioDecoderCounters();
        } else {
            return null;
        }
    }

    @Override
    public Format AudioFormat() {
        if (player != null) {
            return player.getAudioFormat();
        } else {
            return null;
        }
    }

    @Override
    public Object CurrentManifest() {
        if (player != null) {
            return player.getCurrentManifest();
        } else {
            return null;
        }
    }

    @Override
    public Object CurrentTag() {
        if (player != null) {
            return player.getCurrentTag();
        } else {
            return null;
        }
    }

    @Override
    public TrackGroupArray CurrentTrackGroups() {
        if (player != null) {
            return player.getCurrentTrackGroups();
        } else {
            return null;
        }
    }

    @Override
    public ExoPlaybackException PlaybackError() {
        if (player != null) {
            return player.getPlaybackError();
        } else {
            return null;
        }
    }

    @Override
    public Looper PlaybackLooper() {
        if (player != null) {
            return player.getPlaybackLooper();
        } else {
            return null;
        }
    }

    @Override
    public PlaybackParameters PlaybackParameters() {
        if (player != null) {
            return player.getPlaybackParameters();
        } else {
            return null;
        }
    }

    @Override
    public Player.VideoComponent VideoComponent() {
        if (player != null) {
            return player.getVideoComponent();
        } else {
            return null;
        }
    }

    @Override
    public DecoderCounters VideoDecoderCounters() {
        if (player != null) {
            return player.getVideoDecoderCounters();
        } else {
            return null;
        }
    }

    @Override
    public Format VideoFormat() {
        if (player != null) {
            return player.getVideoFormat();
        } else {
            return null;
        }
    }

    @Override
    public Player getPlayer() {
        if (player != null) {
            return player;
        } else {
            throw new NullPointerException("Player does not created");
        }
    }

    @Override
    public PlayerView getPlayerView() {
        if (player != null) {
            return playerView;
        } else {
            throw new NullPointerException("Player does not created");
        }
    }
}
//MediaSource videoSource = mediaSourceFactory
//                    .setExtractorsFactory(extractorsFactory)
//                    .createMediaSource(videoUri);
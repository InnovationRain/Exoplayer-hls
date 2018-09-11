package com.example.hovsep.exoplayertestfive.ExoPlayer;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.Surface;
import android.view.View;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.bufferingCompleted;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.changedInternetConnection;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.currentVideoHeightSize;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.currentVideoSegmentDuration;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.currentVideoWidthSize;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.dialog;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.mainActivity;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.progressBar;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.trackSelector;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo.userAlertDialogStyle;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.Utils.DismissDialog;
import static com.example.hovsep.exoplayertestfive.ExoPlayer.Utils.ErrorPlayingVideo;

class PlayerAnalyticsListener implements AnalyticsListener {
    private static final String TAG = "AnalyticsListener";

    @Override
    public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                bufferingCompleted = null;
                progressBar.setVisibility(View.GONE);
                dialog = new ProgressDialog(mainActivity, userAlertDialogStyle);
                dialog.setMessage("Buffering, please wait.");
                dialog.show();
                break;
            case Player.STATE_IDLE:
                DismissDialog();
                break;
            case Player.STATE_READY:
                progressBar.setVisibility(View.GONE);
                DismissDialog();
                bufferingCompleted = "Complete";
                break;
            case Player.STATE_ENDED:
                progressBar.setVisibility(View.GONE);
                DismissDialog();
                ErrorPlayingVideo("This video has been ended");
                break;
            case PlaybackStateCompat.STATE_SKIPPING_TO_NEXT:
                break;
            case PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS:
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimelineChanged(EventTime eventTime, int reason) {
        currentVideoSegmentDuration = reason;
    }

    @Override
    public void onPositionDiscontinuity(EventTime eventTime, int reason) {
        Log.d(TAG,"Search position  - " + reason);
    }

    @Override
    public void onSeekStarted(EventTime eventTime) {
        Log.d(TAG,"Current Video Started");
    }

    @Override
    public void onSeekProcessed(EventTime eventTime) {
        Log.d(TAG,"Current Video Processed");
    }

    @Override
    public void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters) {
        Log.d(TAG,"onPlaybackParametersChanged");
    }

    @Override
    public void onRepeatModeChanged(EventTime eventTime, int repeatMode) {
        Log.d(TAG,"onRepeatModeChanged - " + repeatMode);
    }

    @Override
    public void onShuffleModeChanged(EventTime eventTime, boolean shuffleModeEnabled) {
        Log.d(TAG,"onShuffleModeChanged - " + shuffleModeEnabled);
    }

    @Override
    public void onLoadingChanged(EventTime eventTime, boolean isLoading) {
        Log.d(TAG,"onLoadingChanged - " + isLoading);
    }

    @Override
    public void onPlayerError(EventTime eventTime, ExoPlaybackException error) {
        ConnectivityManager cm =
                (ConnectivityManager)mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;
        if(!(activeNetwork != null && activeNetwork.isConnected())){
            progressBar.setVisibility(View.GONE);
            DismissDialog();
            ErrorPlayingVideo("No Internet Connection ");
        } else {
            switch (error.type) {
                case ExoPlaybackException.TYPE_SOURCE:
                    progressBar.setVisibility(View.GONE);
                    DismissDialog();
                    ErrorPlayingVideo("Sorry , but this url is not found");
                    break;
                case ExoPlaybackException.TYPE_RENDERER:
                    progressBar.setVisibility(View.GONE);
                    DismissDialog();
                    ErrorPlayingVideo("Sorry , but this url is not rendered");
                    break;
                case ExoPlaybackException.TYPE_UNEXPECTED:
                    progressBar.setVisibility(View.GONE);
                    DismissDialog();
                    ErrorPlayingVideo("Sorry , but this url is not unexpected");
                    break;
                default:
                    progressBar.setVisibility(View.GONE);
                    DismissDialog();
                    ErrorPlayingVideo("Sorry , but something went wrong");
            }
        }
    }

    @Override
    public void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo != null) {
            if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_VIDEO)
                    == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                Log.d(TAG,"sdfsdf\n" +
                        "xgfdgfd\n" +
                        "error_unsupported_video");
            }
            if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_AUDIO)
                    == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                Log.d(TAG,"sdfsdf\n" +
                        "xgfdgfd\n" +
                        "error_unsupported_audio");
            }
        }
        if(!trackGroups.isEmpty()){
            Set<String> languagesSet = new HashSet<>();
            for(int i = 0; i < trackGroups.length;i++){
                if(trackGroups.get(i).getFormat(0).language != null){
                    languagesSet.add(trackGroups.get(i).getFormat(0).language);
                }
            }
            PlayerExo.newInstance().setHlsStreamLanguageSet(languagesSet);
        }
    }

    @Override
    public void onLoadStarted(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        Log.d(TAG,"onLoadStarted");
    }

    @Override
    public void onLoadCompleted(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        Log.d(TAG,"onLoadCompleted");
    }

    @Override
    public void onLoadCanceled(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        Log.d(TAG,"onLoadCanceled");
    }

    @Override
    public void onLoadError(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
        Log.d(TAG,"onLoadError");
    }

    @Override
    public void onDownstreamFormatChanged(EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        Log.d(TAG,"onDownstreamFormatChanged");
    }

    @Override
    public void onUpstreamDiscarded(EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        Log.d(TAG,"onUpstreamDiscarded");
    }

    @Override
    public void onMediaPeriodCreated(EventTime eventTime) {
        Log.d(TAG,"onMediaPeriodCreated");
    }

    @Override
    public void onMediaPeriodReleased(EventTime eventTime) {
        Log.d(TAG,"onMediaPeriodReleased");
    }

    @Override
    public void onReadingStarted(EventTime eventTime) {
        Log.d(TAG,"onReadingStarted");
    }

    @Override
    public void onBandwidthEstimate(EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {
        Log.d(TAG,"onBandwidthEstimate");
    }

    @Override
    public void onViewportSizeChange(EventTime eventTime, int width, int height) {
        Log.d(TAG,"onViewportSizeChange");
    }

    @Override
    public void onNetworkTypeChanged(EventTime eventTime, @Nullable NetworkInfo networkInfo) {
        if(networkInfo == null || !networkInfo.isConnected()) {
            changedInternetConnection = false;
            ErrorPlayingVideo("No Internet Connection");
            Log.d(TAG,"No Internet Connection");
        } else {
            changedInternetConnection = true;
            ErrorPlayingVideo("Connected to Internet");
            Log.d(TAG,"Connected to Internet");
        }
    }

    @Override
    public void onMetadata(EventTime eventTime, Metadata metadata) {
        Log.d(TAG,"onMetadata");
    }

    @Override
    public void onDecoderEnabled(EventTime eventTime, int trackType, DecoderCounters decoderCounters) {
        Log.d(TAG,"onDecoderEnabled");
    }

    @Override
    public void onDecoderInitialized(EventTime eventTime, int trackType, String decoderName, long initializationDurationMs) {
        Log.d(TAG,"onDecoderInitialized");
    }

    @Override
    public void onDecoderInputFormatChanged(EventTime eventTime, int trackType, Format format) {
        Log.d(TAG,"onDecoderInputFormatChanged");
    }

    @Override
    public void onDecoderDisabled(EventTime eventTime, int trackType, DecoderCounters decoderCounters) {
        Log.d(TAG,"onDecoderDisabled");
    }

    @Override
    public void onAudioSessionId(EventTime eventTime, int audioSessionId) {
        Log.d(TAG,"onAudioSessionId");
    }

    @Override
    public void onAudioUnderrun(EventTime eventTime, int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        ErrorPlayingVideo("Sorry , But Something Went Wrong");
    }

    @Override
    public void onDroppedVideoFrames(EventTime eventTime, int droppedFrames, long elapsedMs) {
        Log.d(TAG,"onDroppedVideoFrames");
    }

    @Override
    public void onVideoSizeChanged(EventTime eventTime, int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        currentVideoWidthSize = width;
        currentVideoHeightSize = height;
    }

    @Override
    public void onRenderedFirstFrame(EventTime eventTime, Surface surface) {
        Log.d(TAG,"onRenderedFirstFrame");
    }

    @Override
    public void onDrmKeysLoaded(EventTime eventTime) {
        Log.d(TAG,"onDrmKeysLoaded");
    }

    @Override
    public void onDrmSessionManagerError(EventTime eventTime, Exception error) {
        Log.d(TAG,"onDrmSessionManagerError");
    }

    @Override
    public void onDrmKeysRestored(EventTime eventTime) {
        Log.d(TAG,"onDrmKeysRestored");
    }

    @Override
    public void onDrmKeysRemoved(EventTime eventTime) {
        Log.d(TAG,"onDrmKeysRemoved");
    }
}
package com.example.hovsep.exoplayertestfive.ExoPlayer.InterfacesPackage;

import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

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
import com.google.android.exoplayer2.ui.PlayerView;

public interface VideoUtils {

    int AudioSessionId();
    int BufferedPercentage();
    int CurrentAdGroupIndex();
    int CurrentAdIndexInAdGroup();
    int PreviousWindowIndex();
    int RendererCount();
    int RendererType(int index);
    int RepeatMode();
    int CurrentPeriodIndex();
    int CurrentWindowIndex();
    int NextWindowIndex();
    int PlaybackState();
    int VideoScalingMode();
    int GetCurrentSegmentDuration();
    int GetCurrentVideoWidth();
    int GetCurrentVideoHeight();

    long CurrentPosition();
    long CurrentDuration();
    long BufferedPosition();
    long ContentPosition();

    boolean PlayWhenReady();
    boolean IsCurrentWindowDynamic();
    boolean IsCurrentWindowSeekable();
    boolean IsLoading();
    boolean IsPlayingAd();
    boolean ShuffleModeEnabled();

    float Volume();

    void SeekTo(int windowIndex, long positionMs);
    void SeekTo(long positionMs);
    void SeekToDefaultPosition();
    void SeekToDefaultPosition(int windowIndex);
    void SendMessages(ExoPlayer.ExoPlayerMessage... messages);
    void SetAudioAttributes(AudioAttributes audioAttributes);
    void SetPlaybackParameters(@Nullable PlaybackParameters playbackParameters);
    void SetPlayWhenReady(boolean playWhenReady);
    void SetRepeatMode(int repeatMode);
    void SetSeekParameters(@Nullable SeekParameters seekParameters);
    void SetShuffleModeEnabled(boolean shuffleModeEnabled);
    void SetVideoScalingMode(int videoScalingMode);
    void SetVideoSurface(Surface surface);
    void SetVideoSurfaceHolder(SurfaceHolder surfaceHolder);
    void SetVideoSurfaceView(SurfaceView surfaceView);
    void SetVideoTextureView(TextureView textureView);
    void SetVolume(float audioVolume);
    void Stop();
    void Stop(boolean reset);

    TrackSelectionArray CurrentTrackSelections();
    Timeline CurrentTimeline();
    DecoderCounters AudioDecoderCounters();
    Format AudioFormat();
    Object CurrentManifest();
    Object CurrentTag();
    TrackGroupArray CurrentTrackGroups();
    ExoPlaybackException PlaybackError();
    Looper PlaybackLooper();
    PlaybackParameters PlaybackParameters();
    Player.VideoComponent VideoComponent();
    DecoderCounters VideoDecoderCounters();
    Format VideoFormat();

    Player getPlayer();
    PlayerView getPlayerView();
}

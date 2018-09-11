package com.example.hovsep.exoplayertestfive.ExoPlayer;

import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;

class MediaSourceHls {

    private static final String HLS = "hls";
    private static final String TAG = "MediaSourceHls";

    static Uri subtitleUri;
    static String id;
    static int selectionFlags;
    static String language;

    private static MediaSourceHls mediaSource;

    static MediaSourceHls newInstance(){
        if(mediaSource == null){
            mediaSource = new MediaSourceHls();
        }
        return mediaSource;
    }

    public MediaSource CreateMediaSource(DefaultDataSourceFactory defaultDataSourceFactory,
                                  String mContextUri){
        String[] splitUrl = mContextUri.split(" ");
        switch (splitUrl[0]) {
            case HLS:
                return CreateHlsMediaSource(defaultDataSourceFactory, Uri.parse(splitUrl[1]));
            default:
                throw new UnsupportedOperationException("This Content type is not supported.");
        }
    }

    private MediaSource CreateHlsMediaSource(DefaultDataSourceFactory defaultDataSourceFactory,
                                            Uri uri) {
        HlsMediaSource.Factory factory = new HlsMediaSource.Factory(defaultDataSourceFactory);
        HlsMediaSource hlsMediaSource = factory.createMediaSource(uri);
        return hlsMediaSource;
    }

    private MediaSource CreateMediaSourceAndSubtitles(DefaultDataSourceFactory defaultDataSourceFactory,
                                     Uri uri){
        MediaSource videoSource =
                new ExtractorMediaSource.Factory(defaultDataSourceFactory).createMediaSource(uri);
        Format subtitleFormat = Format.createTextSampleFormat(
                id, // An identifier for the track. May be null.
                MimeTypes.APPLICATION_SUBRIP, // The mime type. Must be set correctly.
                selectionFlags, // Selection flags for the track.
                language); // The subtitle language. May be null.
        MediaSource subtitleSource =
                new SingleSampleMediaSource.Factory(defaultDataSourceFactory)
        .createMediaSource(subtitleUri, subtitleFormat, C.TIME_UNSET);
        MergingMediaSource mergedSource =
                new MergingMediaSource(videoSource, subtitleSource);
        return mergedSource;
    }
}
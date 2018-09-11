package com.example.hovsep.exoplayertestfive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.example.hovsep.exoplayertestfive.ExoPlayer.PlayerExo;
import com.example.hovsep.exoplayertestfive.ExoPlayer.Utils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String HLS_TEST1 = "hls https://video-dev.github.io/streams/x36xhzz/x36xhzz.m3u8";
    private static final String HLS_TEST2 = "hls https://video-dev.github.io/streams/x36xhzz/x36xhzz.m3u8";
    private static final String HLS_TEST3 = "hls http://185.37.148.42:17070/vod/coco-2017-bdrip-1080p-exkinoray.mp4/index.m3u8?token=NjY2OTk5NjY2KzE1MzY1ODAzODcrMTU1MDIzMjY2OSsxKyswKzArViszK2VmNzE4ZmJmOGI5NzEzM2MxN2NlYjljNDY2MTNiNmIz";
    private static final String ARANEA_HLS = "hls http://178.219.48.14:13010/h1/index.m3u8";
    private static final String NOT_FOUND_HLS = "hls http://185.37.148.42:17070/vod/coco-2017-bdrip-1080p-exkinoray.mp4/index.m3u8?token=NjY2OTk5NjY2KzE1MzY0MjUxNTQrMTU1MDIzMjY2OSsxKyswKzArViszKzA4OWM1ZGY0YjJhOTc2NTlkYzhmYTJmYzBlNTBhODVh";
    private static final String MOBILE_HLS = "hls http://185.37.148.42:17070/C249/timeshift_rel-43200.m3u8?token=NjY2OTk5NjY2KzE1MzY1ODA1NjIrMTU1MDIzMjY2OSsxKyswKzArMTIrMytDMjQ5KzM0MTE1NTk2ZTJkNGE4ZDM0MDg3MWRjYzI1NTAwMjcw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        exo_simple_player_view
//        exo_playback_control_view
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case 8:
                PlayerExo.newInstance().initiateNewVideo(HLS_TEST1,0L);
                return true;
            case 9:
                PlayerExo.newInstance().initiateNewVideo(HLS_TEST2,0L);
                return true;
            case 10:
                PlayerExo.newInstance().initiateNewVideo(HLS_TEST3,0L);
                return true;
            case 11:
                PlayerExo.newInstance().initiateNewVideo(ARANEA_HLS,0L);
                return true;
            case 12:
                PlayerExo.newInstance().initiateNewVideo(MOBILE_HLS,0L);
                return true;
//            case 13:
//                Utils.newInstance().GetLanguages();
//                return true;
//            case 14:
//                PlayerExo.newInstance().initiateNewVideo(NOT_FOUND_HLS,0L);
//                return true;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.newInstance().releasePlayer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializePlayer();
        Utils.newInstance().Stop();
        PlayerExo.newInstance().initiateNewVideo(MOBILE_HLS,0L);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.newInstance().releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.newInstance().releasePlayer();
    }

    private void initializePlayer(){
        PlayerExo.newInstance().initiatePlayer(
                R.id.mainLayout,
                R.id.player_view,
                R.id.exoProgressBar,
                R.style.MyAlertDialogStyle,
                R.layout.error_popup,
                R.id.text_view,
                R.layout.popup_languages,
                R.id.hls_stream_languages,
                R.drawable.ic_fullscreen_skrink,
                R.drawable.ic_fullscreen_expand,
                R.layout.languages_model,
                R.id.languageName,
                this);
        PlayerExo.newInstance().initiatePlayerControls(
                R.color.Red,
                R.drawable.select_buttons
        );
    }
}

# Exoplayer-hls
Exoplayer for android Mobile and TV Boxes (HLS Streams)

Add in build.gradle(module : app) `
  implementation 'com.google.android.exoplayer:exoplayer:2.8.4'
  
For working full screan and stream languages buttons opent in your progect exo_simple_player_view.xml file, and add this`

    <com.google.android.exoplayer2.ui.PlaybackControlView
      android:id="@id/exo_controller"
      android:layout_width="match_parent"
      android:layout_height="match_parent" />
      
Open in your project exo_playback_control_view.xml file:
In first LinearLayout before 

    <ImageButton android:id="@id/exo_next"
      style="@style/ExoMediaButton.Next"/> 
      
Add Languages button `

      <FrameLayout
        android:id="@+id/exo_languages"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:padding="2dp"
        android:layout_gravity="right">

      <ImageButton
          android:id="@+id/exo_languages_btn"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:layout_gravity="center"
          android:adjustViewBounds="true"
          android:scaleType="fitCenter"
          android:src="@drawable/ic_languages"
          android:background="@drawable/select_buttons"/>

    </FrameLayout>
    
And in last LinearLayout before last TextView add full screan button `

    <FrameLayout
        android:id="@+id/exo_fullscreen_button"
        android:layout_width="32dp"
        android:layout_height="32dp"
        android:layout_gravity="right">

      <ImageButton
          android:id="@+id/exo_fullscreen_icon"
          android:layout_margin="4dp"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:layout_gravity="center"
          android:adjustViewBounds="true"
          android:scaleType="fitCenter"
          android:src="@drawable/ic_fullscreen_expand"
          android:background="@drawable/select_buttons"/>

    </FrameLayout>
    
    
Player Initialization in Activity Example `

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
          
Player controller Initialization in Activity Example `

        PlayerExo.newInstance().initiatePlayerControls(
            R.color.Red,
            R.drawable.select_buttons
        );
        
Test for playing hls stream `
       private static final String HLS_TEST1 = "hls https://video-dev.github.io/streams/x36xhzz/x36xhzz.m3u8";
       PlayerExo.newInstance().initiateNewVideo(HLS_TEST1,0L);


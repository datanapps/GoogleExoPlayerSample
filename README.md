# GoogleExoPlayerSample

A ExoPlayer player to play video in full screen with video media controller. 
ExoPlyer also have to play youtube url with the help of youtubevideo extracter


### Download APK : 

https://github.com/datanapps/GoogleExoPlayerSample/blob/master/screens/app-debug.apk



#### Android Dependency :


    implementation 'com.google.android.exoplayer:exoplayer-core:2.9.4'
    implementation 'com.google.android.exoplayer:exoplayer-dash:2.8.2'
    implementation 'com.google.android.exoplayer:exoplayer-ui:2.8.2'
    
    
#### Added View in layout : 

     <com.google.android.exoplayer2.ui.PlayerView
            android:id="@+id/playerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            />

#### Play Url in java file :

    private fun initializePlayer() {

      // init player 
       var  player = ExoPlayerFactory.newSimpleInstance(this, DefaultRenderersFactory(this),
            DefaultTrackSelector(), DefaultLoadControl()
        )
      // set player view
        playerView.setPlayer(player)

        player.setPlayWhenReady(true)
        player.seekTo(0, 0)

        // play MP4 Videos
       val uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        val mediaSource = buildMediaSource(uri)
        player.prepare(mediaSource, true, false)
    }
    
    
    
    Hope it will work for you. Thank you.
    
    
    


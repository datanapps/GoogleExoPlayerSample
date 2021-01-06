# GoogleExoPlayerSample (r2.2.12)

A ExoPlayer player to play video in full screen with video media controller. 
ExoPlyer also have to play youtube url with the help of youtubevideo extracter

### Code structure : 

![alt text](https://github.com/datanapps/GoogleExoPlayerSample/blob/master/screens/screen_1.jpg)

### Download APK : 

https://github.com/datanapps/GoogleExoPlayerSample/blob/master/screens/app-debug.apk



#### Android Dependency :


    // exo player
    implementation 'com.google.android.exoplayer:exoplayer-core:2.12.2'
    implementation 'com.google.android.exoplayer:exoplayer-dash:2.12.2'
    implementation 'com.google.android.exoplayer:exoplayer-hls:2.12.2'
    implementation 'com.google.android.exoplayer:exoplayer:2.12.2'
    implementation 'com.google.android.exoplayer:exoplayer-ui:2.12.2'
    implementation 'com.google.android.exoplayer:extension-ima:2.12.2'
    
    
#### Added View in layout : 

     <com.google.android.exoplayer2.ui.StyledPlayerView
        android:id="@+id/playerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        />

#### Play Url in java file :

    fun initializePlayer() {

        val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(this)
            .setAdsLoaderProvider(DefaultMediaSourceFactory.AdsLoaderProvider {
                var loader = ImaAdsLoader.Builder( this).build()
                loader.setPlayer(player)
                return@AdsLoaderProvider loader
            })
            .setAdViewProvider(playerView)

        player = SimpleExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
        playerView.setPlayer(player)

        // create play list
        player!!.setMediaItem(getMediaItem())

        // prepare media list
        player!!.setPlayWhenReady(playWhenReady);
        player!!.seekTo(currentWindow, playbackPosition);
        player!!.addListener(this);
        player!!.prepare();
    }
    
    
    
    Hope it will work for you. Thank you.
    
    
 <img src="https://datanapps.com/public/dnarestapi/naughty_smile.jpg" height="200" width="300">
 
 [![See](https://datanapps.com/public/dnarestapi/buy/buy_coffee2.png)](https://www.paypal.me/datanappspaynow)

  ### License

Copyright [2021] [datanapps]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0

    


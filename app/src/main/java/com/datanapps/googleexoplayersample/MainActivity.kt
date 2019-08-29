package com.datanapps.googleexoplayersample


import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    lateinit var player : SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    private fun initializePlayer() {

        player = ExoPlayerFactory.newSimpleInstance(this, DefaultRenderersFactory(this),
            DefaultTrackSelector(), DefaultLoadControl()
        )

        playerView.setPlayer(player)

        player.setPlayWhenReady(false)
        player.seekTo(0, 0)

        val uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        val mediaSource = buildMediaSource(uri)
        player.prepare(mediaSource, true, false)

    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource.Factory(
            DefaultHttpDataSourceFactory("exoplayer-codelab")
        ).createMediaSource(uri)
    }

    public override fun onStart() {
        super.onStart()

            initializePlayer()

    }

    public override fun onResume() {
        super.onResume()
        //hideSystemUi()
        /*if (Util.SDK_INT <= 23 || playerView == null) {
            initializePlayer()
        }*/
    }


    public override fun onPause() {
        super.onPause()
        //if (Util.SDK_INT <= 23) {
            releasePlayer()
        //}
    }

    public override fun onStop() {
        super.onStop()
       // if (Util.SDK_INT > 23) {
            releasePlayer()
        //}
    }


    private fun releasePlayer() {
        if (playerView != null) {
           var playbackPosition = player.getCurrentPosition()
            var currentWindow = player.getCurrentWindowIndex()
            var playWhenReady = player.getPlayWhenReady()
            player.release()
            //player = null
        }
    }


}

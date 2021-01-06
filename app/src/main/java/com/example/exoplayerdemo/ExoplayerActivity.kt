package com.example.exoplayerdemo

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_adv_exo_player.*


class ExoplayerActivity : AppCompatActivity(), Player.EventListener{

    private var player: SimpleExoPlayer? = null

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

  val ADS_URL = "http://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dskippablelinear&correlator="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }


    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.removeListener(this);
            player!!.release()
            player = null
        }
    }



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


       /* // create play list
        player!!.addMediaItem(MediaItem.fromUri(getString(R.string.media_url_1)))
        player!!.addMediaItem(MediaItem.fromUri(getString(R.string.media_url_2)))
        player!!.addMediaItem(MediaItem.fromUri(getString(R.string.media_url_3)))*/

        player!!.setMediaItem(getMediaItem())

        // prepare media list
        player!!.setPlayWhenReady(playWhenReady);
        player!!.seekTo(currentWindow, playbackPosition);
        player!!.addListener(this);
        player!!.prepare();
    }

    override  fun onPlaybackStateChanged(playbackState: Int) {
        val stateString: String
        stateString = when (playbackState) {
            ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE "
            ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING "
            ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY "
            ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED "
            else -> "UNKNOWN_STATE "
        }
        Log.d("TAG", "changed state to $stateString")
    }

    fun getMediaItem() : MediaItem {
        // add one media item
        // val mediaItem: MediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
        return MediaItem.Builder()
            .setUri(Uri.parse(getString(R.string.media_url_1)))
            .setMimeType(MimeTypes.BASE_TYPE_VIDEO)
            .setAdTagUri(Uri.parse(ADS_URL))
            .build()
    }

}
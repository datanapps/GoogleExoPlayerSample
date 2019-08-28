package com.datanapps.googleexoplayersample


import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.exoplayer2.util.Util


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        //hideSystemUi()
        if (Util.SDK_INT <= 23 || playerView == null) {
            initializePlayer()
        }
    }


    private fun initializePlayer() {

        var player = ExoPlayerFactory.newSimpleInstance(this, DefaultRenderersFactory(this),
            DefaultTrackSelector(), DefaultLoadControl()
        )

        playerView.setPlayer(player)

        player.setPlayWhenReady(true)
        player.seekTo(0, 0)

        val uri = Uri.parse("https://www.youtube.com/watch?v=aZbTL0xTWQg")
        val mediaSource = buildMediaSource(uri)
        player.prepare(mediaSource, true, false)

    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource.Factory(
            DefaultHttpDataSourceFactory("exoplayer-codelab")
        ).createMediaSource(uri)
    }
}

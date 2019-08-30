package com.datanapps.googleexoplayersample


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.activity_video.*


class VideoActivity : AppCompatActivity() {

    lateinit var player : SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

    }


    private fun initializePlayer() {

        player = ExoPlayerFactory.newSimpleInstance(this, DefaultRenderersFactory(this),
            DefaultTrackSelector(), DefaultLoadControl()
        )

        playerView.setPlayer(player)

        player.setPlayWhenReady(true)
        player.seekTo(0, 0)


        // play MP4 Videos

       val uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        val mediaSource = buildMediaSource(uri)
        player.prepare(mediaSource, true, false)


       // Play youtube video
       // extractYoutubeUrl("https://www.youtube.com/watch?v=OJ3K90FpQ6A");
    }

    @SuppressLint("StaticFieldLeak")
    private fun extractYoutubeUrl(youtubeLink:String) {
        object : YouTubeExtractor(this) {
            public override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                vMeta: VideoMeta
            ) {
                if (ytFiles != null) {
                    val itag = 22
                    val downloadUrl = ytFiles.get(itag).url

                    // play url
                    val uri = Uri.parse(downloadUrl)
                    val mediaSource = buildMediaSource(uri)
                    player.prepare(mediaSource, true, false)

                }
            }
        }.extract(youtubeLink, true, true)

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



    public override fun onPause() {
        super.onPause()
        releasePlayer()

    }

    public override fun onStop() {
        super.onStop()
        releasePlayer()
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

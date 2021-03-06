package com.example.androidtask6.ui

import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.example.androidtask6.R
import com.example.androidtask6.data.entities.Song
import com.example.androidtask6.databinding.ActivityMainBinding
import com.example.androidtask6.exoplayer.callbacks.toSong
import com.example.androidtask6.exoplayer.isPlaying
import com.example.androidtask6.other.Status
import com.example.androidtask6.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var glide: RequestManager

    lateinit var mainViewModel: MainViewModel

    private var curPlayingSong: Song? = null

    private var playbackState: PlaybackStateCompat? = null

    private var shouldUpdateSeekbar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        subscribeToObservers()
        initListeners()
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(
            this,
            Observer {
                it?.let { result ->
                    when (result.status) {
                        Status.SUCCESS -> {
                            result.data?.let { songs ->
                                if (curPlayingSong == null && songs.isNotEmpty()) {
                                    curPlayingSong = songs[0]
                                    updateTitleAndSongImage(songs[0])
                                }
                            }
                        }
                        else -> Unit
                    }
                }
            }
        )

        mainViewModel.playbackState.observe(
            this,
            Observer {
                playbackState = it
                binding.ivPlayPauseDetail.setImageResource(
                    if (playbackState?.isPlaying == true) R.drawable.ic_pause else R.drawable.ic_play
                )
                binding.seekBar.progress = it?.position?.toInt() ?: 0
            }
        )

        mainViewModel.curPlayerPosition.observe(
            this,
            Observer {
                if (shouldUpdateSeekbar) {
                    binding.seekBar.progress = it.toInt()
                    setCurrentTimeToTextView(it)
                }
            }
        )

        mainViewModel.curSongDuration.observe(
            this,
            Observer {
                binding.seekBar.max = it.toInt()
                val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
                binding.tvSongDuration.text = dateFormat.format(it)
            }
        )

        mainViewModel.curPlayingSong.observe(
            this,
            Observer {
                if (it == null) return@Observer
                curPlayingSong = it.toSong()
                updateTitleAndSongImage(curPlayingSong!!)
            }
        )
    }

    private fun setCurrentTimeToTextView(ms: Long) {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        binding.tvCurTime.text = dateFormat.format(ms)
    }

    private fun initListeners() {
        binding.run {
            ivSkip.setOnClickListener {
                mainViewModel.skipToNextSong()
            }
            ivSkipPrevious.setOnClickListener {
                mainViewModel.skipToPreviousSong()
            }
            ivPlayPauseDetail.setOnClickListener {
                curPlayingSong?.let {
                    mainViewModel.playOrToggleSong(it, true)
                }
            }
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        setCurrentTimeToTextView(progress.toLong())
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    shouldUpdateSeekbar = false
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    binding.seekBar.let {
                        mainViewModel.seekTo(it.progress.toLong())
                        shouldUpdateSeekbar = true
                    }
                }
            })
        }
    }

    private fun updateTitleAndSongImage(song: Song) {
        val title = "${song.title} - ${song.artist}"
        binding.tvSongName.text = title
        glide.load(song.bitmapUri).into(binding.ivSongImage)
    }
}

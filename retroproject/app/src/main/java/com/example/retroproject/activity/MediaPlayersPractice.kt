package com.example.retroproject.activity


import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retroproject.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class MediaPlayersPractice : AppCompatActivity(), View.OnClickListener,
    OnSeekBarChangeListener {

    private var iv_plays: FloatingActionButton? = null
    private var iv_reset: ImageView? = null
    private var seekBar: SeekBar? = null
    private var mediaPlayer: MediaPlayer? = null
    private var duration: Long = 0
    private var tv_start: TextView? = null
    private var tv_total: TextView? = null
    private var minutes: Long = 0
    private var seconds: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediaplayers_practice)


        // method for find ids..
        findViews()
        // method for click Listener
        initListner()
    }

    private fun findViews() {
        iv_plays = findViewById(R.id.iv_plays)
        iv_reset = findViewById(R.id.iv_reset)
        tv_start = findViewById(R.id.tv_start)
        tv_total = findViewById(R.id.tv_total)
        seekBar = findViewById(R.id.seekbar)
    }

    private fun initListner() {
        iv_plays!!.setOnClickListener(this)
        iv_reset!!.setOnClickListener(this)
        seekBar!!.setOnSeekBarChangeListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_plays ->
                // for check the mediaplayer is null
                if (mediaPlayer == null) {

                    /*  * if media player is null then create a new mediaplayer*/
                    mediaPlayer = MediaPlayer.create(this, R.raw.musics)
                    /* start mediaplayer*/
                    mediaPlayer!!.start()
                    iv_plays!!.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    /* * for getting the duration of media-player */
                    duration = mediaPlayer!!.duration.toLong()
                    minutes = duration / 1000 / 60 //converting into minutes
                    seconds = duration / 1000 % 60 //converting into seconds
                    tv_total!!.text = "$minutes:$seconds"

                    /* * setting the max-length of seekbar according to media-player duration  * */
                    seekBar!!.max = duration.toInt()
                    /* * use Timer task for updating the seekbar progress according to media-player Current position * */
                    Timer().scheduleAtFixedRate(object : TimerTask() {
                        override fun run() {
                            try
                            { seekBar!!.progress = mediaPlayer!!.currentPosition }

                            catch (e: Exception) { }
                        }
                    }, 0, 1000)
                    /* * for reset the mediaplayer when mediaplayer is complete * */
                    mediaPlayer!!.setOnCompletionListener { mediaPlayer ->
                        var mediaPlayer = mediaPlayer
                        mediaPlayer!!.release()
                    }
                    Toast.makeText(this, "start MediaPlayer", Toast.LENGTH_SHORT).show()
                }
                else if (mediaPlayer!!.isPlaying){
                    /* * if media player is already created then only play  * */
                    Log.e("play else", "onClick: ")
                    mediaPlayer!!.pause()
                    iv_plays!!.setImageResource(R.drawable.ic_baseline_pause_24)
                    Toast.makeText(this, "Pause MediaPlayer", Toast.LENGTH_SHORT).show()

                }
            else{
                    Toast.makeText(this, "Start MediaPlayer", Toast.LENGTH_SHORT).show()
                iv_plays!!.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    mediaPlayer!!.start()

                }

            //for single play and pause button
         /*   R.id.iv_stop -> {
                *//* stop media player if is playing *//*
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.pause()
                    Toast.makeText(this, "Pause MediaPlayer", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Already paused", Toast.LENGTH_SHORT).show()
                }
            }*/
            R.id.iv_reset ->
                /**  Reset Media player.. */
                if (mediaPlayer != null) {
                    mediaPlayer!!.release()
                   // mediaPlayer = null
                    mediaPlayer = MediaPlayer.create(this, R.raw.musics)
                    seekBar!!.progress = 0

                    /* * set both text to 00:00 when mediaplayer is reset...* */
                    tv_start!!.text = "00:00"
                    tv_total!!.text = "00:00"
                    Toast.makeText(this, "Reset MediaPlayer", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Song will start from the beginning", Toast.LENGTH_SHORT).show()
                }
        }
    }

    /* seek bar progress change Listener*/
    @SuppressLint("SetTextI18n")
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromuser: Boolean) {
        if (mediaPlayer != null) {
            if (fromuser) {
                /* * media player seek to progress of the seekbar * */
                mediaPlayer!!.seekTo(progress)
            }
            minutes = (progress / 1000 / 60).toLong() //converting the progress into minutes
            seconds = (progress / 1000 % 60).toLong() //converting the progress into second
            tv_start!!.text = "$minutes:$seconds" // set text according to progress change of seekbar
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer!!.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.release()
    }
}
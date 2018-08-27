package com.mlm09kdev.kotlinpracticeapp


import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.TimePicker
import com.mlm09kdev.kotlinpracticeapp.utils.PreferenceUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_timer.*


class MainActivity : AppCompatActivity() {

    enum class  TimerState{
        Stopped, Paused, Running
    }

    private lateinit var  timer: CountDownTimer
    private var timerLengthSeconds = 0L
    private var timerState: TimerState = TimerState.Stopped

    private var secondsRemaining = 0L



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setIcon(R.drawable.ic_timer)
        supportActionBar?.title = "     Timer"

        fab_start_button.setOnClickListener { v ->
            startTimer()
            timerState = TimerState.Running
            updateButtons()

        }
        fab_pause_button.setOnClickListener { v ->
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()
        }

        fab_stop_button.setOnClickListener { v ->
            timer.cancel()
            onTimerFinished()
        }

    }

    override fun onResume() {
        super.onResume()
    initTimer()
    }

    override fun onPause() {
        super.onPause()

        if(timerState == TimerState.Running){
            timer.cancel()

        }
        else if (timerState == TimerState.Paused)
        {

        }
        PreferenceUtils.setPreviousTimerLengthSeconds(timerLengthSeconds, this)
        PreferenceUtils.setSecondsRemaining(secondsRemaining, this)
        PreferenceUtils.setTimerState(timerState, this)
    }

    fun initTimer(){
        timerState = PreferenceUtils.getTimerState(this)
        if(timerState == TimerState.Stopped){
            setNewTimerLength()
        }
        else{
            setPreviousTimerLength()
        }

        secondsRemaining = if(timerState == TimerState.Running || timerState == TimerState.Paused)
            PreferenceUtils.getSecondsRemaining(this)
        else
            timerLengthSeconds


        if (timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountdownUI()
    }

    private fun onTimerFinished(){
        timerState = TimerState.Stopped

        setNewTimerLength()
        progressBarView.progress = 0

        PreferenceUtils.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000){
            override fun onFinish()  = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining =  millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()
    }

    private fun setNewTimerLength(){
        val lengthInMinutes = PreferenceUtils.getTimerLength(this)
        timerLengthSeconds = (lengthInMinutes * 60L)
        progressBarView.max = timerLengthSeconds.toInt()

    }

    private fun setPreviousTimerLength(){
        timerLengthSeconds = PreferenceUtils.getPreviousTimerLengthSeconds(this)
        progressBarView.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondString = secondsInMinuteUntilFinished.toString()
        countDownTextView.text = "$minutesUntilFinished:${
            if(secondString.length == 2)
                secondString
            else
                "0" + secondString}"

        progressBarView.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }
    private fun updateButtons(){
        when (timerState){
            TimerState.Running ->{
                fab_start_button.isEnabled = false
                fab_pause_button.isEnabled = true
                fab_stop_button.isEnabled = true
            }
            TimerState.Stopped ->{
                fab_start_button.isEnabled = true
                fab_pause_button.isEnabled = false
                fab_stop_button.isEnabled = false
            }
            TimerState.Paused ->{
                fab_start_button.isEnabled = true
                fab_pause_button.isEnabled = false
                fab_stop_button.isEnabled = true
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}



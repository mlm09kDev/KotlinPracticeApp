package com.mlm09kdev.kotlinpracticeapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mlm09kdev.kotlinpracticeapp.utils.PreferenceUtils

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        //todo: show notification

        PreferenceUtils.setTimerState(MainActivity.TimerState.Stopped, context)
        PreferenceUtils.setAlarmSetTIme(context, 0)
    }
}

package com.example.timetracker

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class TimerViewModel: ViewModel() {
    val TAG = "Rah"
    private lateinit var timer: Timer
    init {
        Log.v("Rah", "TimerViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "TimerViewModel cleared")
    }

    fun getTimer(): Timer = if (this::timer.isInitialized) timer else {
        timer = Timer(CoroutineScope(SupervisorJob()+Dispatchers.Default))
        timer
    }
}





package com.example.timetracker

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Timer(private val scope: CoroutineScope) {

    private val sectomsec = 1_000L

    private val timeMutableState = mutableStateOf(Triple(0, 0, 0)) //hrs,min,sec
    private var time by timeMutableState
    fun getTimeState(): State<Triple<Int, Int, Int>> = timeMutableState

    private val tickingMutableState = mutableStateOf(false)
    var ticking by tickingMutableState
    fun getTickingState(): State<Boolean> = tickingMutableState

    // todo ues Coroutines flow instead


    fun start() {
        Log.v("Rah", "Start called")
        ticking = true
        val job = scope.launch {
            Log.v("Rah", "Scope launched")
            //delay(sectomsec)
            while (ticking) {
                time =
                    // sec
                    if (time.third < 59) time.copy(third = time.third + 1)
                    // min
                    else if (time.second < 59) time.copy(second = time.second + 1, third = 0)
                    // hrs
                    else Triple(time.first + 1, 0, 0)
                Log.v("Rah", time.toString())
                delay(sectomsec)
            }
        }
        Log.v("Rah", job.toString())

    }

    fun stop() {
        Log.v("Rah", "Stop called")
        ticking = false
    }

    fun reset() {
        time = Triple(0, 0, 0)
    }

}
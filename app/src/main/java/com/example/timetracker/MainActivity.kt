package com.example.timetracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.timetracker.ui.theme.TimeTrackerTheme
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: TimerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
        setContent {
            TimeTrackerTheme {
                Dispatchers.Main
                Column {
                    Text(text = "Time Tracker")
                    // TODO ask Ramona what did I do here?
                    val timer = viewModel.getTimer()
                    Log.v("Rah", "Got Timer $timer")
                    Watch(
                        timer = timer,
                        onStart = { timer.start() },
                        onStop = { timer.stop() },
                        onReset = { timer.reset() }
                    )
                }
            }
        }


    }
}

@Composable
fun Watch(
    timer: Timer,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit
) {
    val display by remember {
        timer.getTimeState()
    }
    val isTicking by remember {
        timer.getTickingState()
    }

    val circleColor by animateColorAsState(
        targetValue = if (isTicking) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    )

    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.clickable {
                Log.v("Rah", "Ticking value $isTicking")
                if (!isTicking) {
                    // Start ticking if not ticking
                    onStart()
                } else {
                    Log.v("Rah", "pls close")
                    onStop()
                }
            }
        ) {
            Surface(
                shape = CircleShape,
                color = circleColor,
                modifier = Modifier.size(200.dp)
            ) {}
            Text(text = display.toString())
        }
        if (!isTicking && display != Triple(0, 0, 0)) {
            SlidingSwitch2(
                modifier = Modifier.padding(horizontal = 30.dp),
                onSwitched = onReset
            )
        }
    }


}



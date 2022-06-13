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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.timetracker.ui.theme.TimeTrackerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeTrackerTheme {
                Column {
                    Text(text = "Time Tracker")
                    Watch()
                    //SwipeToDismissListItems()

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Watch() {
    val secToMSec = 1L


    var time by remember {
        mutableStateOf(Triple(0,0,0)) //hrs,min,sec
    }
    fun tickTock1Sec(){
        var (hrs, min, sec) = time
        sec++
        if (sec==60) {
            sec = 0
            min++
        }
        if (min==60) {
            min = 0
            hrs++
        }
        time = Triple(hrs, min, sec)
    }
    val tickerTimer = Timer("ticker_timer", true)
    var tickerTask: TimerTask by remember {
        mutableStateOf(Timer().schedule(0,){Log.v("Watch", "Task initialized to blank task")})
    }

    var isCounting by remember {
        mutableStateOf(false)
    }
    val circleColor by animateColorAsState(
        targetValue = if(isCounting) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    )

    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.clickable {
                isCounting = !isCounting
                if (isCounting) {
                    tickerTask = tickerTimer.scheduleAtFixedRate(
                        delay = 0,
                        period = secToMSec,
                    ) {
                        tickTock1Sec()
                        Log.v("Rah", time.toString())
                    }
                } else {
                    Log.v("Rah", "pls close")
                    tickerTask.cancel()
                }
            }
        ) {
            Surface(
                shape = CircleShape,
                color = circleColor,
                modifier = Modifier.size(200.dp)
            ) {}
            Text(text = time.toString())
        }
        var reset by remember {
            mutableStateOf(false)
        }
        if (!isCounting && time != Triple(0, 0, 0)) {
            /*SlidingSwitch(checked = reset, onCheckedChange = {
                time = Triple(0, 0, 0)
            })*/
            SlidingSwitch2(
                modifier = Modifier.padding(horizontal = 30.dp),
                onSwitched = {time = Triple(0, 0, 0)}
            )
        }
    }


}


@Composable
fun SlidingSwitch(checked:Boolean, onCheckedChange: ((Boolean)->Unit)?) {
    Switch(checked = checked, onCheckedChange = onCheckedChange)
}


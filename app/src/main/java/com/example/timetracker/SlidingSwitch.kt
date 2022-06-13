package com.example.timetracker

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview



@Composable
@OptIn(ExperimentalMaterialApi::class)
fun SlidingSwitch2(modifier: Modifier, onSwitched: (()->Unit)? ) {

    var swiped by remember {
        mutableStateOf(false)
    }
    val dismissState = rememberDismissState(
        confirmStateChange = { dismissValue ->
            swiped = dismissValue == DismissValue.DismissedToEnd
            if (swiped) onSwitched?.invoke()
            swiped
        }
    )
    SwipeToDismiss(
        state = dismissState,
        modifier = modifier,
        directions = setOf(DismissDirection.StartToEnd),
        dismissThresholds = { FractionalThreshold(0.75f) },
        background = {
            val color by animateColorAsState(targetValue =
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.LightGray
                    DismissValue.DismissedToEnd -> Color.Red
                    else -> {Color.Gray}
                }
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color)) {
                Text(text = "BACK GROUND")
            }
        },
        dismissContent = {
            Box(modifier = Modifier.fillMaxWidth()){
                Text(text = " --> ", modifier = Modifier.background(Color.White))
            }


        }
    )
}

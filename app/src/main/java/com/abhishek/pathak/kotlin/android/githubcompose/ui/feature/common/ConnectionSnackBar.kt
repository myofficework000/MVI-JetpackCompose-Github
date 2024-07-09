package com.abhishek.pathak.kotlin.android.githubcompose.ui.feature.common

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ConnectionSnackBar(isConnected: Boolean) {
    val snackBarHotState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(isConnected) {
        if(!isConnected){
            snackBarHotState.showSnackbar(
                message = "No internet connection",
                duration = SnackbarDuration.Long
            )
        }
    }
    SnackbarHost(hostState = snackBarHotState,
        snackbar = {
            Snackbar(action = {
                TextButton(onClick = { snackBarHotState.currentSnackbarData?.dismiss() }) {
                    Text(text = "Dismiss")
                }
            },
                backgroundColor = Color.Yellow) {
                Text(text = "No internet connection", color = Color.Black)
            }
        })
}
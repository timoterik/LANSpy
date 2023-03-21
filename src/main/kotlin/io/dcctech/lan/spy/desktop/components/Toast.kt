/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

package io.dcctech.lan.spy.desktop.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import io.dcctech.lan.spy.desktop.data.LogLevel
import io.dcctech.lan.spy.desktop.data.ToastDuration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private var isShown: Boolean = false

@Composable
fun FrameWindowScope.Toast(
    text: String,
    visibility: MutableState<Boolean> = mutableStateOf(false),
    logLevel: LogLevel = LogLevel.INFO,
    duration: ToastDuration? = null,
    surfaceColor: Color? = null,
    textColor: Color? = null
) {
    if (isShown) {
        return
    }

    if (visibility.value) {
        isShown = true
        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier.size(300.dp, 70.dp),
                color = surfaceColor ?: logLevel.bgColor(),
                shape = RoundedCornerShape(4.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = text, color = textColor ?: logLevel.textColor())

                }
                DisposableEffect(Unit) {
                    GlobalScope.launch {
                        delay((duration ?: logLevel.toastDuration()).value.toLong())
                        isShown = false
                        visibility.value = false
                    }
                    onDispose { }
                }
            }
        }
    }
}
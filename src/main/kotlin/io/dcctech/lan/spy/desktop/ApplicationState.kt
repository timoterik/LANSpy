/*
 * Copyright © 2022-2023, DCCTech, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.dcctech.lan.spy.desktop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.TrayState
import io.dcctech.lan.spy.desktop.common.Settings
import io.dcctech.lan.spy.desktop.window.LanSpyDesktopWindowState

@Composable
fun rememberApplicationState() = remember {
    DesktopApplicationState().apply {
        newWindow()
    }
}

class DesktopApplicationState {
    val settings = Settings()
    val tray = TrayState()

    val windows: List<LanSpyDesktopWindowState> get() = _windows
    private val _windows = mutableStateListOf<LanSpyDesktopWindowState>()

    fun newWindow() {
        _windows.add(
            LanSpyDesktopWindowState(
                application = this,
                path = null,
                exit = _windows::remove
            )
        )
    }

    suspend fun exit() {
        val windowsCopy = windows.reversed()
        for (window in windowsCopy) {
            if (!window.exit()) {
                break
            }
        }
    }
}
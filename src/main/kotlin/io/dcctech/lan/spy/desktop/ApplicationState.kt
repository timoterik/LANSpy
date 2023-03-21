/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

package io.dcctech.lan.spy.desktop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.TrayState
import io.dcctech.lan.spy.desktop.common.Settings
import io.dcctech.lan.spy.desktop.window.LanSpyDesktopWindowState

/**

A composable function that remembers the application state using DesktopApplicationState.
It applies a new window to the application state.
@return The remembered DesktopApplicationState with a new window applied.
 */
@Composable
fun rememberApplicationState() = remember {
    DesktopApplicationState().apply {
        newWindow()
    }
}

/**
A class that represents the state of a desktop application.
It contains a Settings object, a TrayState object, and a list of LanSpyDesktopWindowState objects that represent the application's windows.
@property settings The application's settings.
@property tray The application's tray state.
@property windows The list of LanSpyDesktopWindowState objects that represent the application's windows.
 */
class DesktopApplicationState {
    val settings = Settings()
    val tray = TrayState()

    val windows: List<LanSpyDesktopWindowState> get() = _windows
    private val _windows = mutableStateListOf<LanSpyDesktopWindowState>()

    /**
    Creates a new window and adds it to the list of windows.
     */
    fun newWindow() {
        _windows.add(
            LanSpyDesktopWindowState(
                application = this,
                path = null,
                exit = _windows::remove
            )
        )
    }

    /**
    Exits the application by closing all windows in reverse order.
    If a window cannot be closed, the remaining windows will not be closed.
     */
    suspend fun exit() {
        val windowsCopy = windows.reversed()
        for (window in windowsCopy) {
            if (!window.exit()) {
                break
            }
        }
    }
}
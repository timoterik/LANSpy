/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

package io.dcctech.lan.spy.desktop.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.WindowPlacement
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.common.theme.DarkColors
import io.dcctech.lan.spy.desktop.common.theme.LightColors
import io.dcctech.lan.spy.desktop.utils.showNotification
import kotlinx.coroutines.launch
import java.awt.event.KeyEvent


/**

A composable function that creates a menu bar for the window.
@param state The state of the LanSpy desktop window.
@throws IllegalStateException if ExperimentalComposeUiApi is not opt-in.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FrameWindowScope.WindowMenuBar(state: LanSpyDesktopWindowState) = MenuBar {
    val scope = rememberCoroutineScope()
    fun exit() = scope.launch { state.exit() }
    fun openHelpDialog() = scope.launch { state.helpDialog() }
    fun openWifiDialog() = scope.launch { state.wifiDialog() }

    Menu(R.file) {
        Item(R.newWindow, onClick = state::newWindow)
        Separator()
        Item(R.exit, onClick = { exit() })
    }
    Menu(R.actions, mnemonic = 'A') {
        if (!state.isRunning) Item(R.startSearch, onClick = state::start)
        if (state.isRunning) Item(R.stopSearch, onClick = state::stop)
        if (state.listOfClients.isNotEmpty()) Item(R.reset, onClick = state::reset)
    }
    Menu(R.preferences, mnemonic = 'P') {
        Item(
            R.infoWifi,
            shortcut = KeyShortcut(Key(KeyEvent.VK_W), alt = true),
            onClick = { openWifiDialog() })
        Item(
            if (state.window.placement == WindowPlacement.Fullscreen) R.exitFullscreen else R.enterFullscreen,
            onClick = state::toggleFullscreen, shortcut = KeyShortcut(Key(KeyEvent.VK_F), alt = true)
        )
        Item(
            R.reset,
            mnemonic = 'R',
            shortcut = KeyShortcut(Key.R, ctrl = true),
            onClick = { showNotification(R.reset, R.reset) }
        )

        Menu(R.theme) {
            RadioButtonItem(
                R.light,
                mnemonic = 'L',
                selected = state.application.settings.theme == LightColors,
                onClick = { state.application.settings.theme = LightColors }
            )
            RadioButtonItem(
                R.dark,
                mnemonic = 'D',
                selected = state.application.settings.theme == DarkColors,
                onClick = { state.application.settings.theme = DarkColors }
            )
        }
    }
    Menu(R.help) {
        Item(R.help, shortcut = KeyShortcut(Key(KeyEvent.VK_H), alt = true), onClick = { openHelpDialog() })
    }

}

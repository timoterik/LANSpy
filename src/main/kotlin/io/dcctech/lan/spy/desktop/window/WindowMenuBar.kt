/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
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
import kotlinx.coroutines.launch
import java.awt.event.KeyEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FrameWindowScope.WindowMenuBar(state: LanSpyDesktopWindowState) = MenuBar {
    val scope = rememberCoroutineScope()

    fun exit() = scope.launch { state.exit() }
    fun openHelpDialog() = scope.launch { state.helpDialog() }
    fun openPreferencesDialog() = scope.launch { state.preferencesDialog() }

    Menu(R.file) {
        Item(R.newWindow, onClick = state::newWindow)
        Separator()
        Item(R.exit, onClick = { exit() })
    }
    Menu(R.actions, mnemonic = 'A') {
        if (!state.isRunning) Item(R.startSearch, onClick = state::start)
        if (state.isRunning) Item(R.stopSearch, onClick = state::stop)
        if (state.resultList.isNotEmpty()) Item(R.reset, onClick = state::reset)
    }
    Menu(R.preferences, mnemonic = 'P') {
//        Item(
//            R.about,
//            shortcut = KeyShortcut(Key(KeyEvent.VK_A), alt = true),
//            onClick = { openPreferencesDialog() })
        Item(
            if (state.window.placement == WindowPlacement.Fullscreen) R.exitFullscreen else R.enterFullscreen,
            onClick = state::toggleFullscreen, shortcut = KeyShortcut(Key(KeyEvent.VK_F), alt = true)
        )
        Item(
            R.reset,
            mnemonic = 'R',
            shortcut = KeyShortcut(Key.R, ctrl = true),
            onClick = { println(R.reset) }
        )

        Menu(R.theme) {
            RadioButtonItem(
                R.light,
                mnemonic = 'L',
//                    icon = ColorCircle(),
                selected = state.theme == LightColors,
                onClick = { state.theme = LightColors }
            )
            RadioButtonItem(
                R.device,
                mnemonic = 'D',
//                    icon = ColorCircle(androidx.compose.ui.graphics.Color.DarkGray),
                selected = state.theme == DarkColors,
                onClick = { state.theme = DarkColors }
            )
        }
    }
    Menu(R.help) {
        Item(R.help, shortcut = KeyShortcut(Key(KeyEvent.VK_H), alt = true), onClick = { openHelpDialog() })
    }

}

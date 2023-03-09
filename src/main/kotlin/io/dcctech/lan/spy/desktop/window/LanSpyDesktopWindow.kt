/*
 * Copyright © 2022-2023, DCCTech, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.dcctech.lan.spy.desktop.window

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import io.dcctech.lan.spy.desktop.common.LocalAppResources
import io.dcctech.lan.spy.desktop.common.ResName
import io.dcctech.lan.spy.desktop.util.DialogBox
import io.dcctech.lan.spy.desktop.util.ResultList
import kotlinx.coroutines.launch
import java.awt.event.KeyEvent

@Composable
fun LANSpyDesktopWindow(state: LanSpyDesktopWindowState) {
    val scope = rememberCoroutineScope()

    fun exit() = scope.launch { state.exit() }

    Window(
        state = state.window,
        title = titleOf(state),
        icon = LocalAppResources.current.res[ResName.DCCTECH_LOGO],
        onCloseRequest = { exit() }
    ) {
        LaunchedEffect(Unit) { state.run() }

        WindowMenuBar(state)

        Row {
            Column(
                Modifier.fillMaxHeight(0.75F).padding(30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ResultList(state.resultList.map { it.value }.toList())
            }
        }
        Row {
            Logo()
        }

        if (state.exitDialog.isAwaiting) {
            DialogBox(
                title = "Discover devices",
                message = "Are you sure you want to exit?",
                onResult = {
                    state.exitDialog.onResult(it)
                }
            )
        }
        if (state.helpDialog.isAwaiting) {
            DialogBox(
                title = "Help dialog",
                message = "Was this helpful?",
                onResult = {
                    state.helpDialog.onResult(it)
                }
            )
        }
        if (state.preferencesDialog.isAwaiting) {
            DialogBox(
                title = "Preferences dialog",
                message = "This dialog box is about the settings values.",
                onResult = {
                    state.preferencesDialog.onResult(it)
                },
                withoutDialogue = true
            )
        }
    }
}

private fun titleOf(state: LanSpyDesktopWindowState): String {
    val changeMark = state.process
    return "Device discovery $changeMark"
}

@Composable
private fun FrameWindowScope.WindowMenuBar(state: LanSpyDesktopWindowState) = MenuBar {
    val scope = rememberCoroutineScope()

    fun exit() = scope.launch { state.exit() }
    fun openHelpDialog() = scope.launch { state.helpDialog() }
    fun openPreferencesDialog() = scope.launch { state.preferencesDialog() }

    Menu("File") {
        Item("New window", onClick = state::newWindow)
        Separator()
        Item("Exit", onClick = { exit() })
    }
    Menu("Actions", mnemonic = 'A') {
        if (!state.isRunning) Item("Start searching...", onClick = state::start)
        if (state.isRunning) Item("Stop searching", onClick = state::stop)
        if (state.resultList.isNotEmpty()) Item("Reset", onClick = state::reset)
    }
    Menu("Settings", mnemonic = 'S') {
        Item(
            "Preferences",
            shortcut = KeyShortcut(Key(KeyEvent.VK_P), alt = true),
            onClick = { openPreferencesDialog() })
        Item(
            if (state.window.placement == WindowPlacement.Fullscreen) "Exit fullscreen" else "Enter fullscreen",
            onClick = state::toggleFullscreen, shortcut = KeyShortcut(Key(KeyEvent.VK_F), alt = true)
        )
    }
    Menu("Help") {
        Item("Help", shortcut = KeyShortcut(Key(KeyEvent.VK_H), alt = true), onClick = { openHelpDialog() })
    }

}

@Composable
fun Logo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LocalAppResources.current.res[ResName.DCCTECH_IMG]?.let {
            Image(
                it, "DCCTech image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .height(125.dp)
            )
        }
    }
}
/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import io.dcctech.lan.spy.desktop.common.LocalAppResources
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.common.ResName
import io.dcctech.lan.spy.desktop.components.DialogBox
import io.dcctech.lan.spy.desktop.ui.TwoColumnsLayout
import io.dcctech.lan.spy.desktop.utils.getAppTitleFromState
import kotlinx.coroutines.launch

/**

A Composable function that displays the main window of the LANSpy desktop application.
@param state a LanSpyDesktopWindowState object which holds the current state of the application.
@return A UI that consists of a title that displays the current status of the discovery process and additional buttons
which are to modify the application state from the windows menu, a list of discovered network devices and services.
@see Composable annotation which means it can be used within a Composable function or combined with other Composable
functions to build a UI.
 */
@Composable
fun LANSpyDesktopWindow(state: LanSpyDesktopWindowState) {
    val scope = rememberCoroutineScope()
    fun exit() = scope.launch { state.exit() }

    Window(
        state = state.window,
        title = getAppTitleFromState(state),
        icon = LocalAppResources.current.res[ResName.DCCTECH_LOGO],
        onCloseRequest = { exit() }
    ) {
        LaunchedEffect(Unit) { state.run() }

        WindowMenuBar(state)

        TwoColumnsLayout(state)

        if (state.exitDialog.isAwaiting) {
            DialogBox(
                title = R.discoverDevices,
                message = R.confirmationDialog,
                onResult = {
                    state.exitDialog.onResult(it)
                }
            )
        }
        if (state.helpDialog.isAwaiting) {
            DialogBox(
                title = R.helpDialogTitle,
                message = R.helpDialogMsg,
                onResult = {
                    state.helpDialog.onResult(it)
                }
            )
        }
        if (state.wifiDialog.isAwaiting) {
            DialogBox(
                title = R.wifiDialog,
                message = state.networkList.map { it.toString() }.joinToString { "\n" },
                onResult = {
                    state.wifiDialog.onResult(it)
                },
                withoutDialogue = true
            )
        }

    }
}



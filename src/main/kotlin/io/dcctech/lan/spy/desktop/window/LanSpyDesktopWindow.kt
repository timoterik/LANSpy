/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.window

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import io.dcctech.lan.spy.desktop.common.LocalAppResources
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.common.ResName
import io.dcctech.lan.spy.desktop.components.DialogBox
import io.dcctech.lan.spy.desktop.components.Logo
import io.dcctech.lan.spy.desktop.components.ResultList
import io.dcctech.lan.spy.desktop.utils.getAppTitleFromState
import io.dcctech.lan.spy.desktop.utils.getWifiInformation
import kotlinx.coroutines.launch

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
                message = getWifiInformation(),
                onResult = {
                    state.wifiDialog.onResult(it)
                },
                withoutDialogue = true
            )
        }

    }
}



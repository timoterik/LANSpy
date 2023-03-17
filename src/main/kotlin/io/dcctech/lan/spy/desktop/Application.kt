/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.MenuScope
import androidx.compose.ui.window.Tray
import io.dcctech.lan.spy.desktop.common.LocalAppResources
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.common.ResName
import io.dcctech.lan.spy.desktop.window.LANSpyDesktopWindow
import kotlinx.coroutines.launch

@Composable
fun ApplicationScope.DesktopApplication(state: DesktopApplicationState) {
    if (state.settings.isTrayEnabled && state.windows.isNotEmpty()) {
        ApplicationTray(state)
    }

    for (window in state.windows) {
        key(window) {
            LANSpyDesktopWindow(window)
        }
    }
}

@Composable
private fun ApplicationScope.ApplicationTray(state: DesktopApplicationState) {

    Tray(
        icon = LocalAppResources.current.res[ResName.DCCTECH_LOGO]
            ?: painterResource("/Color-dcctech-no-bg.png"),
        state = state.tray,
        tooltip = R.appName,
        menu = { ApplicationMenu(state) }
    )
}

@Composable
private fun MenuScope.ApplicationMenu(state: DesktopApplicationState) {

    val scope = rememberCoroutineScope()
    fun exit() = scope.launch { state.exit() }

    Separator()
    Item(R.exit, onClick = { exit() })
}
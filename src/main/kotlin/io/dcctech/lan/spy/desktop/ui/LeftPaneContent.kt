/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.components.NetworkList
import io.dcctech.lan.spy.desktop.utils.title
import io.dcctech.lan.spy.desktop.window.LanSpyDesktopWindowState

@Composable
fun LeftPaneContent(state: LanSpyDesktopWindowState) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.4f)
            .padding(20.dp)
    ) {
        title(R.networks)
        Divider(thickness = 2.dp, color = Color.Red, modifier = Modifier.padding(5.dp))
        NetworkList(state.networkList.map { it.value })
    }
}
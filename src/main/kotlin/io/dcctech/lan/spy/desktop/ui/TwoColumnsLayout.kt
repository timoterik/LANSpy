/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

package io.dcctech.lan.spy.desktop.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.dcctech.lan.spy.desktop.window.LanSpyDesktopWindowState

@Composable
fun TwoColumnsLayout(state: LanSpyDesktopWindowState) {
    Row(Modifier.fillMaxSize()) {
        LeftPaneContent(state)
        Divider(
            color = Color.Blue,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        RightPaneContent(state)
    }
}
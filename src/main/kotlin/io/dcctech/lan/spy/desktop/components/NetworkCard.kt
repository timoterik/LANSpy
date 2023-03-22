/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

package io.dcctech.lan.spy.desktop.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.data.NetworkService

@Composable
fun NetworkCard(
    networkService: NetworkService
) {
    Card(
        elevation = 10.dp,
        backgroundColor = Color.Green
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            KeyNValueInRow(key = R.displayName, value = networkService.displayName)
            KeyNValueInRow(key = R.index, value = networkService.index)
            KeyNValueInRow(key = R.mtu, value = networkService.mtu.toString())
            KeyNValueInRow(key = R.hardwareAddress, value = networkService.hardwareAddress ?: "")
        }
    }
}
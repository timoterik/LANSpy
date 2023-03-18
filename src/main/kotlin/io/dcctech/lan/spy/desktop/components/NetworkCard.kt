/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
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
import io.dcctech.lan.spy.desktop.data.NetworkInfo
import io.dcctech.lan.spy.desktop.utils.concat

@Composable
fun NetworkCard(
    networkInfo: NetworkInfo
) {
    Card(
        elevation = 10.dp,
        backgroundColor = Color.Green
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            KeyNValueInRow(key = R.displayName, value = networkInfo.displayName)
            KeyNValueInRow(key = R.name, value = networkInfo.name)
            KeyNValueInRow(key = R.index, value = networkInfo.index)
            KeyNValueInRow(key = R.mtu, value = networkInfo.mtu.toString())
            KeyNValueInRow(key = R.hardwareAddress, value = networkInfo.hardwareAddress ?: "")
            KeyNValueInRow(key = R.address, value = networkInfo.address.concat())
        }
    }
}
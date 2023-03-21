/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

package io.dcctech.lan.spy.desktop.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.data.Client
import io.dcctech.lan.spy.desktop.utils.formatter

@Composable
fun DeviceCard(
    client: Client,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CreateColumnWithText(key = R.deviceStatus, value = client.status.name)
        CreateColumnWithText(key = R.deviceName, value = client.name)
        CreateColumnWithText(key = R.interfaceName, value = client.interfaceName)
        CreateColumnWithText(key = R.deviceAddress, value = client.address)
        CreateColumnWithText(key = R.deviceMac, value = client.mac)
        CreateColumnWithText(key = R.lastTime, value = formatter.format(client.lastTime))
    }
}
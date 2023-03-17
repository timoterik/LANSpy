/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.data.Device
import io.dcctech.lan.spy.desktop.utils.formatter

@Composable
fun DeviceCard(
    device: Device,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CreateColumnWithText(key = R.deviceStatus, value = device.status.name)
        CreateColumnWithText(key = R.deviceName, value = device.name)
        CreateColumnWithText(key = R.deviceAddress, value = device.address)
        CreateColumnWithText(key = R.deviceAddress, value = device.mac)
        CreateColumnWithText(key = R.lastTime, value = formatter.format(device.lastTime))
    }
}
/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */
package io.dcctech.lan.spy.desktop

import androidx.compose.ui.graphics.Color
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.data.Device
import io.dcctech.lan.spy.desktop.data.DeviceStatus
import io.dcctech.lan.spy.desktop.data.LogLevel
import io.dcctech.lan.spy.desktop.window.LanSpyDesktopWindowState
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun getAppTitleFromState(state: LanSpyDesktopWindowState): String {
    val changeMark = state.process
    return "${R.appName} $changeMark"
}


var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    .withZone(ZoneId.systemDefault())


fun String.getNameAndMac(): Pair<String, String?> {

    val parts = this.split("\n")
    val name = parts.firstOrNull() ?: this
    val mac = try {
        parts[1]
    } catch (t: Throwable) {
        null
    }
    return Pair(name, mac)
}

fun LogLevel.log(msg: String) {
    println("$this: $msg")
}

fun getDeviceColorByStatus(device: Device): Color {
    return when (device.status) {
        DeviceStatus.VISIBLE -> Color.Green
        DeviceStatus.INVISIBLE -> Color.Yellow
        DeviceStatus.GONE -> Color.LightGray
    }
}

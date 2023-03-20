/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */
package io.dcctech.lan.spy.desktop.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.data.Client
import io.dcctech.lan.spy.desktop.data.LogLevel
import io.dcctech.lan.spy.desktop.data.NetworkInfo
import io.dcctech.lan.spy.desktop.data.Status
import io.dcctech.lan.spy.desktop.window.LanSpyDesktopWindowState
import java.net.NetworkInterface
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.swing.JOptionPane


fun getAppTitleFromState(state: LanSpyDesktopWindowState): String {
    val changeMark = state.process
    return "${R.appName} $changeMark"
}


var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    .withZone(ZoneId.systemDefault())


fun LogLevel.log(msg: String) {
    println("$this: $msg")
}

fun getDeviceColorByStatus(client: Client): Color {
    return when (client.status) {
        Status.VISIBLE -> Color.Green
        Status.INVISIBLE -> Color.Yellow
        Status.GONE -> Color.LightGray
    }
}

fun getNetworkInformation(state: LanSpyDesktopWindowState) {

    try {
        val interfaces = NetworkInterface.getNetworkInterfaces()

        for (intf in interfaces) {
            val addresses = intf.inetAddresses
            for (addr in addresses) {
                if (!addr.isLinkLocalAddress && !addr.isLoopbackAddress) {
                    val client = Client(
                        status = Status.VISIBLE,
                        name = addr.hostName,
                        interfaceName = intf.displayName,
                        address = addr.hostAddress,
                        mac = addr.address.getMac(),
                        lastTime = Instant.now()
                    )

                    state.addDeviceToResult(client)

                    LogLevel.DEBUG.log(client.toString())
                } else {
                    val networkInfo = NetworkInfo(
                        displayName = intf.displayName,
                        index = "${intf.index}",
                        hardwareAddress = intf.hardwareAddress.getMac(),
                        mtu = intf.mtu,
                        address = intf.inetAddresses().toString(),
                        lastTime = Instant.now(),
                        status = Status.VISIBLE,
                        name = intf.name,
                        mac = intf.hardwareAddress.getMac()

                    )
                    state.addNetwork(networkInfo)
                    LogLevel.DEBUG.log(networkInfo.toString())

                }
            }
        }
    } catch (t: Throwable) {
        LogLevel.ERROR.log("${t.localizedMessage}\n ${t.printStackTrace()}")
    }

}

/**
 * Extract each array of mac address and convert it to hexadecimal with the following format: 08-00-27-DC-4A-9E.
 * */
fun ByteArray?.getMac(): String {
    if (this == null) return R.unknown

    var macAddress = ""

    for (i in this.indices) {
        macAddress += java.lang.String.format("%02X%s", this[i], if (i < this.size - 1) "-" else "")
    }

    return macAddress
}


fun showNotification(title: String, message: String) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE)
}

@Composable
fun title(title: String) = Text(textAlign = TextAlign.Center, text = title, modifier = Modifier.padding(5.dp))
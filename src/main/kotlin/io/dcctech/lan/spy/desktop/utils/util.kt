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
import io.dcctech.lan.spy.desktop.data.Device
import io.dcctech.lan.spy.desktop.data.DeviceStatus
import io.dcctech.lan.spy.desktop.data.LogLevel
import io.dcctech.lan.spy.desktop.data.NetworkInfo
import io.dcctech.lan.spy.desktop.window.LanSpyDesktopWindowState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.net.NetworkInterface
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.swing.JOptionPane


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

fun getNetworkInformation(): List<NetworkInfo> {
    val resultList = mutableListOf<NetworkInfo>()
    val interfaces = NetworkInterface.getNetworkInterfaces()
    for (intf in interfaces) {
        val listOfAddress = mutableListOf<String>()
        val addresses = intf.inetAddresses
        for (addr in addresses) {
            listOfAddress.add(addr.hostAddress)
        }
        val wifiInfo = NetworkInfo(
            name = intf.name, displayName = intf.displayName, index = "${intf.index}",
            hardwareAddress = intf.hardwareAddress?.contentToString(), mtu = intf.mtu, address = listOfAddress
        )
        LogLevel.DEBUG.log(wifiInfo.toString())
        resultList.add(wifiInfo)
    }
    return resultList
}


fun discoveryOfServerModules(state: LanSpyDesktopWindowState) {
    val port = state.application.settings.port
    val timer = Timer()
    val bytesToSend = "discovering".toByteArray()
    val group: InetAddress = InetAddress.getByName(state.application.settings.inetAddress)

    try {
        state.scope.launch(Dispatchers.IO) {
            MulticastSocket(port).use { ms ->
                ms.joinGroup(group)
                val buffer = ByteArray(1024)
                val packetToReceive = DatagramPacket(buffer, buffer.size)
                timer.schedule(
                    PacketSender(ms, bytesToSend, group, port), 0, state.application.settings.sendingPeriod
                )
                while (state.isRunning) {
                    LogLevel.DEBUG.log(R.listening)
                    ms.receive(packetToReceive)
                    val nameAndMac = String(buffer, 0, packetToReceive.length).getNameAndMac()
                    val device = Device(
                        status = DeviceStatus.VISIBLE,
                        name = nameAndMac.first,
                        address = "${packetToReceive.address}:${packetToReceive.port}",
                        mac = nameAndMac.second ?: "",
                        lastTime = Instant.now()
                    )

                    state.addDeviceToResult(device)
                }
//                ms.leaveGroup(SocketAddress, NetworkInterface)
                ms.leaveGroup(group)
            }
        }
    } catch (t: Throwable) {
        LogLevel.ERROR.log("${t.localizedMessage}\n ${t.printStackTrace()}")
    }
}

suspend fun getAllNetworkInformation(state: LanSpyDesktopWindowState) {
    while (true) {
        try {
            state.scope.launch(Dispatchers.IO) {
                state.networkList.plus(getNetworkInformation()).distinct()
            }
            delay(10000)
        } catch (t: Throwable) {
            LogLevel.ERROR.log("${t.localizedMessage}\n ${t.printStackTrace()}")
        }
    }
}


fun checkDevices(state: LanSpyDesktopWindowState) {
    state.resultList.forEach { (key, device) ->
        LogLevel.DEBUG.log("${R.checking}: ${R.device}: $key; ${R.lastTime}: ${formatter.format(device.lastTime)}")
        when (Instant.now().minusMillis(device.lastTime.toEpochMilli()).toEpochMilli()) {
            in 1..10000 -> state.setStatus(key, DeviceStatus.VISIBLE)
            in 10001..25000 -> state.setStatus(key, DeviceStatus.INVISIBLE)
            in 25001..40000 -> state.setStatus(key, DeviceStatus.GONE)
            else -> state.removeDeviceFromList(key)
        }
    }
}

fun showNotification(title: String, message: String) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE)
}

fun List<String>.concat() = this.joinToString(",\n") { it }

@Composable
fun title(title: String) = Text(textAlign = TextAlign.Center, text = title, modifier = Modifier.padding(5.dp))
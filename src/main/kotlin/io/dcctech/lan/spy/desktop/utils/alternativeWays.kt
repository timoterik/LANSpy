/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.utils

import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.data.Client
import io.dcctech.lan.spy.desktop.data.LogLevel
import io.dcctech.lan.spy.desktop.data.Status
import io.dcctech.lan.spy.desktop.window.LanSpyDesktopWindowState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.net.NetworkInterface
import java.time.Instant
import java.util.*


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

fun discoveryWithMulticastSocket(state: LanSpyDesktopWindowState) {
    val port = state.application.settings.port
    val timer = Timer()
    val bytesToSend = "discovering".toByteArray()
    val group: InetAddress = InetAddress.getByName(state.application.settings.inetAddress)

    try {
        state.scope.launch(Dispatchers.IO) {
            MulticastSocket(1900).use { ms ->
                ms.joinGroup(group)
                val buffer = ByteArray(1024)
                val packetToReceive = DatagramPacket(buffer, buffer.size)
                timer.schedule(
                    PacketSender(ms, bytesToSend, group, port), 0, state.application.settings.timeout.toLong()
                )
                while (state.isRunning) {
                    LogLevel.DEBUG.log(R.listening)
                    ms.receive(packetToReceive)
                    val nameAndMac = String(buffer, 0, packetToReceive.length).getNameAndMac()
                    val client = Client(
                        status = Status.VISIBLE,
                        name = nameAndMac.first,
                        interfaceName = packetToReceive.socketAddress.toString(),
                        address = "${packetToReceive.address}:${packetToReceive.port}",
                        mac = nameAndMac.second ?: "",
                        lastTime = Instant.now()
                    )

//                    state.addDeviceToResult(client)
                }
                ms.leaveGroup(group)
            }
        }
    } catch (t: Throwable) {
        LogLevel.ERROR.log("${t.localizedMessage}\n ${t.printStackTrace()}")
    }
}

fun newDiscovery(state: LanSpyDesktopWindowState) {
    try {
        state.scope.launch(Dispatchers.IO) {
            while (state.isRunning) {
                try {
                    for (i in 1..254) {
                        val host: String = "${state.application.settings.inetAddress}.$i"
                        val inetAddress = InetAddress.getByName(host)
                        LogLevel.DEBUG.log("${R.listening}: ${inetAddress.hostAddress}")
                        if (inetAddress.isReachable(state.application.settings.timeout)) {
                            val ni = NetworkInterface.getByInetAddress(inetAddress)
                            val macAddress = ni?.hardwareAddress
                            val client = Client(
                                status = Status.VISIBLE,
                                name = inetAddress.hostName,
                                interfaceName = ni.interfaceAddresses.toString(),
                                address = inetAddress.hostAddress,
                                mac = macAddress?.contentToString() ?: "",
                                lastTime = Instant.now()
                            )

//                            state.addDeviceToResult(client)

                        }
                    }
                } catch (t: Throwable) {
                    LogLevel.ERROR.log("${t.localizedMessage}\n ${t.printStackTrace()}")
                }

            }
        }
    } catch (t: Throwable) {
        LogLevel.ERROR.log("${t.localizedMessage}\n ${t.printStackTrace()}")
    }
}

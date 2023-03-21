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


/**
 * Constructs a new MAC address object from the specified string, which represents a MAC address in the standard format.
 * The string is obtained by decoding the data in the specified byte array using the platform's default charset.
 * The byte array is created from the buffer of a received DatagramPacket object.
 * The length of the data is specified by packetToReceive.length, and the data starts at the beginning of the buffer (offset 0)
 .*/
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

/**

Uses multicast socket to discover devices on the local network.
@param state The current state of the LanSpyDesktopWindow.
All discovered clients/devices have been set in the state variable
@throws: Catch any exceptions that occur and log them.
 */
fun discoveryWithMulticastSocket(state: LanSpyDesktopWindowState) {
    val settings= state.application.settings
    val port = settings.port
    val timer = Timer()
    val bytesToSend = "discovering".toByteArray()
    val group: InetAddress = InetAddress.getByName(settings.inetAddress)

    try {
        state.scope.launch(Dispatchers.IO) {
            // Creates a new MulticastSocket object bound to the port specified in the settings object. The socket is used
            // to receive and send multicast datagrams. The use function is called on the socket object,
            // ensuring that the socket is closed after the block of code is executed. The socket is passed as an argument
            // to the lambda function, allowing the lambda to perform operations on the socket object.
            MulticastSocket(settings.port).use { ms ->
                ms.joinGroup(group)
                val buffer = ByteArray(1024)
                // Creates a new DatagramPacket object with the specified buffer and length.
                // The buffer is used to store the data received or sent by the DatagramPacket.
                // The length parameter specifies the length of the data in the buffer.
                val packetToReceive = DatagramPacket(buffer, buffer.size)
                timer.schedule(
                    PacketSender(ms, bytesToSend, group, port), 0, state.application.settings.timeout.toLong()
                )
                while (state.isRunning) {
                    LogLevel.DEBUG.log(R.listening)
                    ms.receive(packetToReceive)
                    // Constructs a new String object by decoding the data in the specified byte array using the platform's
                    // default charset. The byte array is created from the buffer of a received DatagramPacket object.
                    // The length of the data is specified by packetToReceive.length, and the data starts at
                    // the beginning of the buffer (offset 0).
                    val nameAndMac = String(buffer, 0, packetToReceive.length).getNameAndMac()
                    val client = Client(
                        status = Status.VISIBLE,
                        name = nameAndMac.first,
                        interfaceName = packetToReceive.socketAddress.toString(),
                        address = "${packetToReceive.address}:${packetToReceive.port}",
                        mac = nameAndMac.second ?: "",
                        lastTime = Instant.now()
                    )
                    // All discovered clients/network services have been set in the state variable
                    state.addDeviceToResult(client)
                }
                ms.leaveGroup(group)
            }
        }
    } catch (t: Throwable) {
        LogLevel.ERROR.log("${t.localizedMessage}\n ${t.printStackTrace()}")
    }
}

/**

Uses InetAddress to discover devices on the local network.
@param state The current state of the LanSpyDesktopWindow.
All discovered clients/devices have been set in the state variable
@throws: Catch any exceptions that occur and log them.
 */
fun discoveryWithInetAddress(state: LanSpyDesktopWindowState) {
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
                            // All discovered clients/network services have been set in the state variable
                            state.addDeviceToResult(client)

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

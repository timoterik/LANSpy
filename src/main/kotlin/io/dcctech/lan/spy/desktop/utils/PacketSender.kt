/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

package io.dcctech.lan.spy.desktop.utils

import io.dcctech.lan.spy.desktop.data.LogLevel
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.util.*

/**

A class that sends a packet to a specified multicast group and port using a timer.
@property ms The MulticastSocket used to send the packet.
@property b The ByteArray representing the packet data.
@property group The InetAddress representing the multicast group.
@property port The port number to send the packet to.
 */
class PacketSender(
    var ms: MulticastSocket,
    var b: ByteArray,
    group: InetAddress,
    port: Int
) : TimerTask() {

    /**
    The DatagramPacket to send.
     */
    private val packet = DatagramPacket(b, b.size, group, port)

    /**
    Sends the DatagramPacket using the MulticastSocket.
    If an error occurs, the TimerTask is canceled and the error is logged with LogLevel.ERROR.
     */
    override fun run() = try {
        ms.send(packet)
    } catch (t: Throwable) {
        cancel()
        LogLevel.ERROR.log("${t.localizedMessage}\n ${t.printStackTrace()}")
    }

}
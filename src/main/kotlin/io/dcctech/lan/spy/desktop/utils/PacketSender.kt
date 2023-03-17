/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.utils

import io.dcctech.lan.spy.desktop.data.LogLevel
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.util.*

class PacketSender(
    var ms: MulticastSocket,
    var b: ByteArray,
    group: InetAddress,
    port: Int
) : TimerTask() {

    private val packet = DatagramPacket(b, b.size, group, port)

    override fun run() = try {
        ms.send(packet)
    } catch (t: Throwable) {
        cancel()
        LogLevel.ERROR.log("${t.localizedMessage}\n ${t.printStackTrace()}")
    }

}
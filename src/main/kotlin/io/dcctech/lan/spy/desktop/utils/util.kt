/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */
package io.dcctech.lan.spy.desktop.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.data.Client
import io.dcctech.lan.spy.desktop.data.LogLevel
import io.dcctech.lan.spy.desktop.data.NetworkService
import io.dcctech.lan.spy.desktop.data.Status
import io.dcctech.lan.spy.desktop.window.LanSpyDesktopWindowState
import java.net.NetworkInterface
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.swing.JOptionPane


/**

Retrieves the application title from the given LanSpyDesktopWindowState object.
The title is composed of the application name and the current status of the discovery process.
@param state The LanSpyDesktopWindowState object that contains the current state of the application.
@return The composed application title as a String.
 */
fun getAppTitleFromState(state: LanSpyDesktopWindowState): String {
    val changeMark = state.process
    return "${R.appName} $changeMark"
}


/**
A variable that holds a DateTimeFormatter object used to format dates and times.
The pattern used is "yyyy-MM-dd HH:mm:ss" and the timezone is set to the system default.
 */
var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    .withZone(ZoneId.systemDefault())

/**
Logs a message with this log level to the console.
This function is an extension function for the enum class LogLevel. It allows logging messages with different log levels
to the console by calling the log() function on a LogLevel instance, passing the message as an argument.
The function concatenates the log level and the message, separated by a colon, and prints the resulting string to the console.
@param msg The message is to be logged.
 */
fun LogLevel.log(msg: String) {
    println("$this: $msg")
}

/**
A function that retrieves network information for the current device and adds the information to the result list of the application state.
It takes in a LanSpyDesktopWindowState object which holds the current state of the application.
The function retrieves network interfaces and their corresponding IP addresses, excluding local and loopback addresses.
For each valid IP address, it adds a new client or network service to the result list of the application state.
Note that this function does not return anything, it only updates the state of the application.
 */
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

                    // All discovered clients have been set in the state variable
                    state.addDeviceToResult(client)

                    LogLevel.DEBUG.log(client.toString())
                } else {
                    val networkService = NetworkService(
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
                    // All discovered network service have been set in the state variable
                    state.addNetwork(networkService)
                    LogLevel.DEBUG.log(networkService.toString())

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
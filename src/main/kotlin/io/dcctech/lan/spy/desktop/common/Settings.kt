/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

package io.dcctech.lan.spy.desktop.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.dcctech.lan.spy.desktop.common.theme.LightColors

/**

A class representing the application settings. This class contains various configuration parameters for the application.
@property inetAddress The IP address used for multicast communication.
@property port The port used for multicast communication.
@property delayInDiscovery The delay in milliseconds between searching for network services or devices.
@property delayedCheck The delay in milliseconds between delayed checks of network information.
@property timeout The timeout in milliseconds for network communication.
@property isTrayEnabled A flag indicating whether the application should be enabled in the system tray.
@property theme The color theme used by the application.
 */
class Settings {
    var inetAddress by mutableStateOf("224.0.0.3")
        private set
    var port by mutableStateOf(5000)
        private set

    var delayInDiscovery by mutableStateOf(1000L)
        private set

    var delayedCheck by mutableStateOf(5000L)
        private set

    var timeout by mutableStateOf(1000)
        private set

    var isTrayEnabled by mutableStateOf(true)
        private set

    var theme by mutableStateOf(LightColors)
}
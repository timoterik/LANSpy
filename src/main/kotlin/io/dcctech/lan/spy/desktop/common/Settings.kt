/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.dcctech.lan.spy.desktop.common.theme.DarkColors

class Settings {
    var inetAddress by mutableStateOf("224.0.0.3")
        private set
    var port by mutableStateOf(5000)
        private set

    var searchNetworkInfoDelay by mutableStateOf(1000L)
        private set

    var delayedCheck by mutableStateOf(5000L)
        private set

    var timeout by mutableStateOf(1000)
        private set

    var isTrayEnabled by mutableStateOf(true)
        private set

    var theme by mutableStateOf(DarkColors)
}
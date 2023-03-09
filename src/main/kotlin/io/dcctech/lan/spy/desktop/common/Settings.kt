/*
 * Copyright © 2022-2023, DCCTech, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.dcctech.lan.spy.desktop.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Settings {
    var port by mutableStateOf(5000)
        private set

    var delayedCheck by mutableStateOf(5000)
        private set

    var sendingPeriod by mutableStateOf(2000)
        private set

    var isTrayEnabled by mutableStateOf(true)
        private set
}
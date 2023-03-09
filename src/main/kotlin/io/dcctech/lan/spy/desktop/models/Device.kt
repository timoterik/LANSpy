/*
 * Copyright © 2022-2023, DCCTech, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.dcctech.lan.spy.desktop.models

import java.time.Instant

enum class DeviceStatus {
    VISIBLE, INVISIBLE, GONE
}

data class Device(

    var status: DeviceStatus,
    val name: String,
    val address: String,
    val mac: String,
    val lastTime: Instant

)

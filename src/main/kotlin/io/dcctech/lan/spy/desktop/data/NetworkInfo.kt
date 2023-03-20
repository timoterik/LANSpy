/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.data

import io.dcctech.lan.spy.desktop.common.R
import java.time.Instant


data class NetworkInfo(


    var displayName: String,
    val index: String,
    val hardwareAddress: String?,
    val mtu: Int,
    override val address: String,
    override val lastTime: Instant,
    override var status: Status,
    override val name: String,
    override val mac: String

) : Ssdp(status, name, address, mac, lastTime) {

    override fun toString(): String {
        return "Network: display name=$displayName; index=$index, " +
                "hardware address:${hardwareAddress ?: R.unknown}; MTU=$mtu; addresses: [${address}]"
    }
}

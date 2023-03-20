/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.data

import java.time.Instant


data class Client(

    var interfaceName: String,
    override val name: String,
    override val address: String,
    override val mac: String,
    override val lastTime: Instant,
    override var status: Status

) : Ssdp(status, name, address, mac, lastTime) {
    override fun toString(): String {
        return "Client: display name=$name; mac=$mac, interface name:${interfaceName}; addresses: [${address}]"
    }
}

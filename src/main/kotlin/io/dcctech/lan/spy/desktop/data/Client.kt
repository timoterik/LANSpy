/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.data

import java.time.Instant


/**

Data class representing a client device discovered on the network.
Inherits from the Ssdp class and adds an interface name property.
@property interfaceName the name of the network interface on which the client was discovered
@property name the name of the client device
@property address the IP address of the client device
@property mac the MAC address of the client device
@property lastTime the timestamp when the client device was last seen on the network
@property status the current status of the client device
 */
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

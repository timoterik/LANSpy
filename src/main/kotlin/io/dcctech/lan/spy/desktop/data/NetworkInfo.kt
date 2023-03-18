/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.data


data class NetworkInfo(

    var name: String,
    var displayName: String,
    val index: String,
    val hardwareAddress: String?,
    val address: List<String> = emptyList(),
    val mtu: Int,

    ) {
    override fun toString(): String {
        return "Network: name=$name; display name=$displayName; index=$index, " +
                "hardware address:${hardwareAddress ?: "---"}; MTU=$mtu; addresses: [${address.joinToString { "$it," }}]"
    }
}

/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.data

/**

An enumeration class that represents the status of a network device or service.
There are three possible values for the status: VISIBLE, INVISIBLE, and GONE.
VISIBLE means the device or service is currently visible on the network,
INVISIBLE means it is not currently visible but was previously discovered,
and GONE means it is no longer available on the network.
 */
enum class Status {
    VISIBLE, INVISIBLE, GONE
}
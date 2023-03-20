/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.data

import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.utils.formatter
import io.dcctech.lan.spy.desktop.utils.log
import java.time.Instant

//Simple Service Discovery Protocol
open class Ssdp(
    open var status: Status,
    open val name: String,
    open val address: String,
    open val mac: String,
    open val lastTime: Instant
) {

    companion object {
        fun checkEntity(list: MutableMap<String, *>): MutableMap<String, *> {
            list.forEach { (key, ssdp) ->
                LogLevel.DEBUG.log("${R.checking}: ${this::class.java.name}: $key; ${R.lastTime}: ${formatter.format((ssdp as Ssdp).lastTime)}")
                when (Instant.now().minusMillis(ssdp.lastTime.toEpochMilli()).toEpochMilli()) {
                    in 1..10000 -> ssdp.changeStatus(Status.VISIBLE)
                    in 10001..25000 -> ssdp.changeStatus(Status.INVISIBLE)
                    in 25001..40000 -> ssdp.changeStatus(Status.GONE)
                    else -> list.minus(key)
                }
            }

            return list
        }

        fun Ssdp.changeStatus(newStatus: Status) {
            val oldStatus = this.status
            this.status = newStatus
            val msg = "${R.update}: ${R.statusHasChanged}"
                .replace("key", this.mac)
                .replace("oldStatus", "$oldStatus")
                .replace("newStatus", "$newStatus")

            LogLevel.INFO.log(msg)
        }
    }


}

/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.data

import androidx.compose.ui.graphics.Color
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.utils.formatter
import io.dcctech.lan.spy.desktop.utils.log
import java.time.Instant

/**

Represents a Simple Service Discovery Protocol (SSDP) object, which contains information about a discovered network service.
This class is open for extension and can be subclassed.
@param status The current status of the network service.
@param name The name of the network service.
@param address The IP address of the network service.
@param mac The MAC address of the network service.
*/
open class Ssdp(
    open var status: Status,
    open val name: String,
    open val address: String,
    open val mac: String,
    open val lastTime: Instant
) {

    companion object {

        /**
        Checks the entities in the list and updates their status based on their last seen time.
        If a device has not been seen recently, it will be marked with a color in the list.
        űIf a device has not been seen for a long time, it will be removed from the list.
        @param list The mutable map of entities to check.
        @return The function returns a mutable map of entities with their status updated based on their last seen time,
        and any devices that have not been seen for a long time are removed from the map..
         */
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

        /**
        Changes the status of the SSDP object to the specified new status.
        @param newStatus The new status to set for the SSDP object.
         */
        fun Ssdp.changeStatus(newStatus: Status) {
            val oldStatus = this.status
            this.status = newStatus
            val msg = "${R.update}: ${R.statusHasChanged}"
                .replace("key", this.mac)
                .replace("oldStatus", "$oldStatus")
                .replace("newStatus", "$newStatus")

            LogLevel.INFO.log(msg)
        }

        /**
        Returns the corresponding Color for the current status of the device or network service.
        The color is determined based on the Status enum of the Ssdp object.
        @return the Color that corresponds to the current status of the device or network service
         */
        fun Ssdp.getColorByStatus(): Color {
            return when (this.status) {
                Status.VISIBLE -> Color.Green
                Status.INVISIBLE -> Color.Yellow
                Status.GONE -> Color.LightGray
            }
        }
    }


}

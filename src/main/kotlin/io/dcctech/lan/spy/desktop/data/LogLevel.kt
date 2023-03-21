/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

package io.dcctech.lan.spy.desktop.data

import androidx.compose.ui.graphics.Color

/**

An enumeration of different log levels along with their respective properties such as background color, text color, and toast duration.
The levels are:
CRITICAL: critical conditions.
ERROR: error conditions.
WARNING: warning conditions.
INFO: informational messages.
DEBUG: messages helpful for debugging.
*/
enum class LogLevel {
    CRITICAL {
        override fun bgColor(): Color = Color.Red
        override fun textColor(): Color = Color.White
        override fun toastDuration(): ToastDuration = ToastDuration.LONG
    },
    ERROR {
        override fun bgColor(): Color = Color.Yellow
        override fun textColor(): Color = Color.Black
        override fun toastDuration(): ToastDuration = ToastDuration.LONG

    },
    WARNING {
        override fun bgColor(): Color = Color.Blue
        override fun textColor(): Color = Color.White
        override fun toastDuration(): ToastDuration = ToastDuration.MEDIUM

    },
    INFO {
        override fun bgColor(): Color = Color.Green
        override fun textColor(): Color = Color.White
        override fun toastDuration(): ToastDuration = ToastDuration.SHORT

    },
    DEBUG {
        override fun bgColor(): Color = Color.LightGray
        override fun textColor(): Color = Color.Black
        override fun toastDuration(): ToastDuration = ToastDuration.SHORT

    };

    /**
    Returns the background color associated with this log level.
     */
    abstract fun bgColor(): Color

    /**
    Returns the text color associated with this log level.
     */
    abstract fun textColor(): Color

    /**
    Returns the toast duration associated with this log level.
     */
    abstract fun toastDuration(): ToastDuration
}
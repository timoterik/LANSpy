/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.data

import androidx.compose.ui.graphics.Color

enum class LogLevel {
    CRITICAL { //critical conditions.
        override fun bgColor(): Color = Color.Red
        override fun textColor(): Color = Color.White
        override fun toastDuration(): ToastDuration = ToastDuration.LONG
    },
    ERROR {  //error conditions.
        override fun bgColor(): Color = Color.Yellow
        override fun textColor(): Color = Color.Black
        override fun toastDuration(): ToastDuration = ToastDuration.LONG

    },
    WARNING {   //warning conditions.
        override fun bgColor(): Color = Color.Blue
        override fun textColor(): Color = Color.White
        override fun toastDuration(): ToastDuration = ToastDuration.MEDIUM

    },
    INFO { //informational messages.
        override fun bgColor(): Color = Color.Green
        override fun textColor(): Color = Color.White
        override fun toastDuration(): ToastDuration = ToastDuration.SHORT

    },
    DEBUG { //messages helpful for debugging.
        override fun bgColor(): Color = Color.LightGray
        override fun textColor(): Color = Color.Black
        override fun toastDuration(): ToastDuration = ToastDuration.SHORT

    };

    abstract fun bgColor(): Color
    abstract fun textColor(): Color
    abstract fun toastDuration(): ToastDuration
}
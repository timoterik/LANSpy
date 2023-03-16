/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.data

import androidx.compose.material.Colors

enum class LogLevel {
    CRITICAL { //critical conditions.
        override fun color(): Colors {
            TODO("Not yet implemented")
        }
    },
    ERROR {
        override fun color(): Colors {
            TODO("Not yet implemented")
        } //error conditions.
    },
    WARNING {   //warning conditions.
        override fun color(): Colors {
            TODO("Not yet implemented")
        }
    },
    INFO { //informational messages.
        override fun color(): Colors {
            TODO("Not yet implemented")
        }
    },
    DEBUG { //messages helpful for debugging.
        override fun color(): Colors {
            TODO("Not yet implemented")
        }
    };

    abstract fun color(): Colors
}
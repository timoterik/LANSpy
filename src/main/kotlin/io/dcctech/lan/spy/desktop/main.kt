/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

package io.dcctech.lan.spy.desktop

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import io.dcctech.lan.spy.desktop.common.LocalAppResources
import io.dcctech.lan.spy.desktop.common.rememberAppResources

fun main() = application {
    MaterialTheme(
        colors = darkColors()
    ) {
        /*
        * This code creates a CompositionLocalProvider composable that provides the LocalAppResources with
        * the rememberAppResources() value. Then, it calls the DesktopApplication composable with
        * the rememberApplicationState() value as a parameter,
        * which creates a desktop application with its corresponding tray and windows.*/
        CompositionLocalProvider(LocalAppResources provides rememberAppResources()) {
            DesktopApplication(rememberApplicationState())
        }
    }
}
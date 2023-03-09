/*
 * Copyright © 2022-2023, DCCTech, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.dcctech.lan.spy.desktop.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

val LocalAppResources = staticCompositionLocalOf<AppResources> {
    error("LocalNotepadResources isn't provided")
}

enum class ResName {
    DCCTECH_LOGO, DCCTECH_IMG
}

@Composable
fun rememberAppResources(): AppResources {
    val dcctechLogo = painterResource("/Color-dcctech-no-bg.png")
    val dcctechImg = painterResource("/color-dcctech.png")

    val resources = mutableMapOf(
        ResName.DCCTECH_LOGO to dcctechLogo,
        ResName.DCCTECH_IMG to dcctechImg
    )

    return remember { AppResources(resources) }
}

class AppResources(val res: MutableMap<ResName, Painter>)

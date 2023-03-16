/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import io.dcctech.lan.spy.desktop.data.LogLevel
import io.dcctech.lan.spy.desktop.log

val LocalAppResources = staticCompositionLocalOf<AppResources> {
    LogLevel.CRITICAL.log(R.notProvided)
    error(R.notProvided)
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

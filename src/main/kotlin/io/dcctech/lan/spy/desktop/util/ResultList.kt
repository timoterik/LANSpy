/*
 * Copyright © 2022-2023, DCCTech, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.dcctech.lan.spy.desktop.util

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.dcctech.lan.spy.desktop.models.Device
import io.dcctech.lan.spy.desktop.models.DeviceStatus
import java.awt.Desktop
import java.net.URI
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun ResultList(list: List<Device>) {
    Box {

        val state = rememberLazyListState()

        LazyColumn(
            Modifier.fillMaxWidth().border(BorderStroke(3.dp, Color.Black)), state
        ) {

            if (list.isEmpty()) {
                items(1) {
                    EmptyCard()
                }
            } else {
                items(list) { device ->
                    ShowDevice(device)
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }

        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }
}

@Composable
fun ShowDevice(device: Device) {
    Box(
        modifier = Modifier.height(65.dp)
            .fillMaxWidth()
            .background(color = getColor(device)),
        contentAlignment = Alignment.CenterStart
    ) {
        DeviceCard(device) {
            val uri = URI.create("http:/${device.address}")
            try {
                val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(uri)
                }
            } catch (e: Exception) {
                println("ERROR: The URL($uri) couldn't be opened in the browser\n${e.printStackTrace()}")
            }
        }
    }
}

fun getColor(device: Device): Color {
    return when (device.status) {
        DeviceStatus.VISIBLE -> Color.Green
        DeviceStatus.INVISIBLE -> Color.Yellow
        DeviceStatus.GONE -> Color.LightGray
    }
}


@Composable
fun DeviceCard(
    device: Device,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CreateColumnWithText(key = "Device status", value = device.status.name)
        CreateColumnWithText(key = "Device name", value = device.name)
        CreateColumnWithText(key = "Device address", value = device.address)
        CreateColumnWithText(key = "Device mac", value = device.mac)
        CreateColumnWithText(key = "Last seen", value = formatter.format(device.lastTime))
    }
}

@Composable
fun EmptyCard() {
    Row(
        Modifier
            .padding(16.dp)
            .height(50.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column { Icon(Icons.Rounded.Warning, null) }
        Column { Text("The list of search results is empty", fontWeight = FontWeight.Bold) }
    }
}

@Composable
fun CreateColumnWithText(modifier: Modifier = Modifier, key: String, value: String) {
    Column {
        Row(modifier.align(Alignment.CenterHorizontally)) {
            Text(key, modifier = modifier.padding(horizontal = 5.dp), fontWeight = FontWeight.Bold)
        }
        Row(modifier.align(Alignment.CenterHorizontally)) {
            Text(value, modifier = modifier.padding(horizontal = 5.dp))
        }
    }
}

var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    .withZone(ZoneId.systemDefault())
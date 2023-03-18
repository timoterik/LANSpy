/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun KeyNValueInRow(modifier: Modifier = Modifier, key: String, value: String) {
    Row(modifier.padding(5.dp)) {
        Column(modifier.fillMaxWidth(0.5f)) {
            Text("$key:", modifier = modifier.padding(3.dp), fontWeight = FontWeight.Bold)
        }
        Column(modifier.fillMaxWidth(0.5f)) {
            Text(value, modifier = modifier.padding(3.dp))
        }
    }
}
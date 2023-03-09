/*
 * Copyright © 2022-2023, DCCTech, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.dcctech.lan.spy.desktop.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.window.FrameWindowScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import javax.swing.JOptionPane

enum class AlertDialogResult {
    Yes, No, Cancel
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun FrameWindowScope.DialogBox(
    title: String,
    message: String,
    onResult: ((result: AlertDialogResult) -> Unit),
    withoutDialogue: Boolean = false
) {
    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.Swing) {
            val resultInt = JOptionPane.showConfirmDialog(
                window, message, title, if (withoutDialogue) JOptionPane.DEFAULT_OPTION else JOptionPane.YES_NO_OPTION
            )
            val result = when (resultInt) {
                JOptionPane.YES_OPTION -> AlertDialogResult.Yes
                JOptionPane.OK_OPTION -> AlertDialogResult.Yes
                JOptionPane.NO_OPTION -> AlertDialogResult.No
                JOptionPane.CANCEL_OPTION -> AlertDialogResult.Cancel
                else -> AlertDialogResult.No
            }
            onResult(result)
        }

        onDispose {
            job.cancel()
        }
    }
}

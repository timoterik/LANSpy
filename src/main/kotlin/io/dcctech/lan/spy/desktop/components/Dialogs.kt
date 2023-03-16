/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.window.FrameWindowScope
import io.dcctech.lan.spy.desktop.data.AlertDialogResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import javax.swing.JOptionPane


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

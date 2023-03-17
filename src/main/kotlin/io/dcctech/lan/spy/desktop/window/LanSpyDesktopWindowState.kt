/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.window

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import io.dcctech.lan.spy.desktop.DesktopApplicationState
import io.dcctech.lan.spy.desktop.common.R
import io.dcctech.lan.spy.desktop.data.AlertDialogResult
import io.dcctech.lan.spy.desktop.data.Device
import io.dcctech.lan.spy.desktop.data.DeviceStatus
import io.dcctech.lan.spy.desktop.data.LogLevel
import io.dcctech.lan.spy.desktop.utils.DialogState
import io.dcctech.lan.spy.desktop.utils.checkDevices
import io.dcctech.lan.spy.desktop.utils.discoveryOfServerModules
import io.dcctech.lan.spy.desktop.utils.log
import kotlinx.coroutines.*
import java.nio.file.Path

class LanSpyDesktopWindowState(
    val application: DesktopApplicationState,
    path: Path?,
    private val exit: (LanSpyDesktopWindowState) -> Unit
) {
    val window = WindowState()
    var resultList: MutableMap<String, Device> = mutableStateMapOf()
    val exitDialog = DialogState<AlertDialogResult>()
    val helpDialog = DialogState<AlertDialogResult>()
    val wifiDialog = DialogState<AlertDialogResult>()
    private var isInit by mutableStateOf(false)
    val scope = CoroutineScope(Dispatchers.Default)

    var notification: Notification? = null

    var process by mutableStateOf("")
        private set

    var path by mutableStateOf(path)
        private set

    var isRunning by mutableStateOf(false)
        private set

    private var _text by mutableStateOf("")
    var text: String
        get() = _text
        set(value) {
            check(isInit)
            _text = value
            isRunning = true
        }


    fun toggleFullscreen() {
        window.placement = if (window.placement == WindowPlacement.Fullscreen) {
            WindowPlacement.Floating
        } else {
            WindowPlacement.Fullscreen
        }
    }

    fun start() {
        isRunning = true
        discoveryOfServerModules(state = this)
        setProcessState(R.running)
    }

    fun stop() {
        isRunning = false
        setProcessState(R.stopped)
    }


    fun reset() {
        isRunning = false
        resultList = mutableMapOf()
        setProcessState("")
    }

    suspend fun run() {
        if (path != null) {
            LogLevel.INFO.log("${R.path}: $path")
            open(path!!)
        } else {
            initNew()
        }
        checking()
    }

    suspend fun checking() {
        while (scope.isActive) {
            try {
                delay(application.settings.delayedCheck)
                checkDevices(this)
            } catch (t: Throwable) {
                LogLevel.ERROR.log("${t.localizedMessage}\n ${t.printStackTrace()}")
            }
        }
    }

    fun setProcessState(status: String) {
        process = status
    }

    fun createNotification(title: String, msg: String, type: Notification.Type = Notification.Type.Info) {
        println("create")
        application.tray.sendNotification(Notification(title = title, message = msg, type = type))
        notification = Notification(title = title, message = msg, type = type)
        println("this a noti: $notification")
    }

    private fun open(path: Path) {
        isInit = false
        this.path = path
        try {
            isInit = true
        } catch (e: Exception) {
            val msg = "${R.cannotOpenThisPath}: $path"
            text = msg
            LogLevel.ERROR.log("$msg \n ${e.printStackTrace()}")
        }
    }

    private fun initNew() {
        _text = R.appName
        isInit = true
        isRunning = false
    }

    fun newWindow() {
        application.newWindow()
    }

    suspend fun helpDialog() {
        when (helpDialog.awaitResult()) {
            AlertDialogResult.Yes -> {
                LogLevel.DEBUG.log(R.successfulAssistance)
            }

            AlertDialogResult.No -> {
                LogLevel.WARNING.log(R.notSatisfiedWithOurAssistance)
            }

            AlertDialogResult.Cancel -> Unit
        }
    }

    suspend fun wifiDialog() {
        when (wifiDialog.awaitResult()) {
            AlertDialogResult.Yes -> {
                LogLevel.INFO.log(R.hasBeenFinished)
            }

            else -> Unit
        }
    }

    suspend fun exit(): Boolean {
        return if (areYouSure()) {
            scope.cancel()
            exit(this)
            true
        } else {
            false
        }
    }

    fun addDeviceToResult(device: Device) {
        resultList[device.address] = device
    }

    fun setStatus(key: String, newStatus: DeviceStatus) {
        var oldStatus: DeviceStatus? = null
        resultList[key]?.let {
            oldStatus = it.status
            it.status = newStatus
            val msg = "${R.update}: ${R.statusHasChanged}"
                .replace("key", key)
                .replace("oldStatus", "$oldStatus")
                .replace("newStatus", "$newStatus")

            LogLevel.INFO.log(msg)
        }
    }

    fun removeDeviceFromList(key: String) {
        resultList.remove(key)
        LogLevel.INFO.log(R.removeDevice.replace("key", key))
    }

    private suspend fun areYouSure(): Boolean {
        return if (isRunning || resultList.isNotEmpty()) {
            when (exitDialog.awaitResult()) {
                AlertDialogResult.Yes -> {
                    true
                }

                AlertDialogResult.No -> {
                    false
                }

                AlertDialogResult.Cancel -> false
            }
        } else {
            true
        }
    }

}

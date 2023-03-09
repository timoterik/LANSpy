/*
 * Copyright © 2022-2023, DCCTech, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.dcctech.lan.spy.desktop.window

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import io.dcctech.lan.spy.desktop.DesktopApplicationState
import io.dcctech.lan.spy.desktop.common.Settings
import io.dcctech.lan.spy.desktop.models.Device
import io.dcctech.lan.spy.desktop.models.DeviceStatus
import io.dcctech.lan.spy.desktop.util.AlertDialogResult
import io.dcctech.lan.spy.desktop.util.formatter
import kotlinx.coroutines.*
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.nio.file.Path
import java.time.Instant
import java.util.*

class LanSpyDesktopWindowState(
    private val application: DesktopApplicationState,
    path: Path?,
    private val exit: (LanSpyDesktopWindowState) -> Unit
) {
    val settings: Settings get() = application.settings
    val window = WindowState()
    var resultList: MutableMap<String, Device> = mutableStateMapOf()
    val exitDialog = DialogState<AlertDialogResult>()
    val helpDialog = DialogState<AlertDialogResult>()
    val preferencesDialog = DialogState<AlertDialogResult>()
    private var isInit by mutableStateOf(false)

    val scope = CoroutineScope(Dispatchers.Default)

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
        setProcessState("-> is running...")
    }

    fun stop() {
        isRunning = false
        setProcessState("-> has stopped!")
    }

    fun reset() {
        isRunning = false
        resultList = mutableMapOf()
        setProcessState("")
    }

    suspend fun run() {
        if (path != null) {
            println("INFO: path $path")
            open(path!!)
        } else {
            initNew()
        }
        checking()
    }

    suspend fun checking() {
        while (scope.isActive) {
            try {
                delay(5000)
                checkDevices(this)
            } catch (t: Throwable) {
                println(t.localizedMessage)
            }
        }
    }

    fun setProcessState(status: String) {
        process = status
    }

    private fun open(path: Path) {
        isInit = false
        this.path = path
        try {
            isInit = true
        } catch (e: Exception) {
            e.printStackTrace()
            text = "Cannot read $path"
        }
    }

    private fun initNew() {
        _text = "LANSpy Desktop"
        isInit = true
        isRunning = false
    }

    fun newWindow() {
        application.newWindow()
    }

    suspend fun helpDialog() {
        when (helpDialog.awaitResult()) {
            AlertDialogResult.Yes -> {
                println("INFO: We're glad we were able to helped ...")
            }

            AlertDialogResult.No -> {
                println("INFO: We're sorry to hear that you are not satisfied with our assistance service....")
            }

            AlertDialogResult.Cancel -> Unit
        }
    }

    suspend fun preferencesDialog() {
        when (preferencesDialog.awaitResult()) {
            AlertDialogResult.Yes -> {
                println("INFO: It will be finished!")
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

    fun setStatus(key: String, status: DeviceStatus) {
        var oldStatus: DeviceStatus? = null
        resultList[key]?.let {
            oldStatus = it.status
            it.status = status
            println("UPDATED: the status of the device with address $key has changed from $oldStatus to $status")
        }
    }

    fun removeDeviceFromList(key: String) {
        resultList.remove(key)
        println("INFO: remove the device with address: $key from the list")
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

    class DialogState<T> {
        private var onResult: CompletableDeferred<T>? by mutableStateOf(null)

        val isAwaiting get() = onResult != null

        suspend fun awaitResult(): T {
            onResult = CompletableDeferred()
            val result = onResult!!.await()
            onResult = null
            return result
        }

        fun onResult(result: T) = onResult!!.complete(result)
    }
}

@OptIn(DelicateCoroutinesApi::class)
private fun discoveryOfServerModules(state: LanSpyDesktopWindowState) {
    val port = 5000  //FIXME state.settings.port
    val timer = Timer()
    val bytesToSend = "discovering".toByteArray()
    val group: InetAddress = InetAddress.getByName("228.5.6.7")

    try {
        state.scope.launch(Dispatchers.IO) {
            MulticastSocket(port).use { ms ->
                ms.joinGroup(group)
                val buffer = ByteArray(1024)
                val packetToReceive = DatagramPacket(buffer, buffer.size)
                timer.schedule(
                    PacketSender(ms, bytesToSend, group, port), 0, 3000
                ) //FIXME state.settings.sendingPeriod
                while (state.isRunning) {
                    println("INFO: listening....")
                    ms.receive(packetToReceive)
                    val nameAndMac = String(buffer, 0, packetToReceive.length).getNameAndMac()
                    val device = Device(
                        status = DeviceStatus.VISIBLE,
                        name = nameAndMac.first,
                        address = "${packetToReceive.address}:${packetToReceive.port}",
                        mac = nameAndMac.second ?: "",
                        lastTime = Instant.now()
                    )

                    state.addDeviceToResult(device)
                }
                ms.leaveGroup(group)
            }
        }
    } catch (t: Throwable) {
        println(t.localizedMessage)
    }
}

fun String.getNameAndMac(): Pair<String, String?> {

    val parts = this.split("\n")
    val name = parts.firstOrNull() ?: this
    val mac = try {
        parts[1]
    } catch (t: Throwable) {
        null
    }
    return Pair(name, mac)
}

class PacketSender(
    var ms: MulticastSocket,
    var b: ByteArray,
    group: InetAddress,
    port: Int
) : TimerTask() {

    private val packet = DatagramPacket(b, b.size, group, port)

    override fun run() = try {
        ms.send(packet)
    } catch (t: Throwable) {
        cancel()
        println(t.localizedMessage)
    }

}

fun checkDevices(state: LanSpyDesktopWindowState) {
    state.resultList.forEach { (key, device) ->
        println("INFO: checking... device: $key, lastTime: ${formatter.format(device.lastTime)}")
        when (Instant.now().minusMillis(device.lastTime.toEpochMilli()).toEpochMilli()) {
            in 1..10000 -> state.setStatus(key, DeviceStatus.VISIBLE)
            in 10001..25000 -> state.setStatus(key, DeviceStatus.INVISIBLE)
            in 25001..40000 -> state.setStatus(key, DeviceStatus.GONE)
            else -> state.removeDeviceFromList(key)
        }
    }
}
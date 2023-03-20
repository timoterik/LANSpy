/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.common

object R {

    val appName: String
    val deviceDiscovery: String
    val running: String
    val stopped: String
    val path: String
    val cannotOpenThisPath: String
    val successfulAssistance: String
    val notSatisfiedWithOurAssistance: String
    val hasBeenFinished: String
    val update: String
    val statusHasChanged: String
    val removeDevice: String
    val listening: String
    val checking: String
    val device: String
    val lastTime: String
    val notProvided: String
    val companyLogo: String
    val companyImage: String
    val couldNotOpenedInBrowser: String
    val resultListIsEmpty: String
    val deviceStatus: String
    val deviceName: String
    val deviceAddress: String
    val deviceMac: String
    val discoverDevices: String
    val confirmationDialog: String
    val helpDialogTitle: String
    val helpDialogMsg: String
    val preferencesDialogTitle: String
    val wifiDialog: String
    val file: String
    val newWindow: String
    val exit: String
    val actions: String
    val startSearch: String
    val stopSearch: String
    val reset: String
    val preferences: String
    val exitFullscreen: String
    val enterFullscreen: String
    val about: String
    val theme: String
    val light: String
    val dark: String
    val help: String
    val infoWifi: String
    val displayName: String
    val name: String
    val index: String
    val mtu: String
    val hardwareAddress: String
    val address: String
    val networks: String
    val network: String
    val devices: String
    val unknown: String
    val interfaceName: String

    init {
        if (System.getProperty("user.language").equals("hu")) {
            appName = "Lan Spy Alkalmazás"
            deviceDiscovery = "Eszköz keresése a hálozaton"
            running = "Az alkalmazás fut"
            stopped = "Az alkalmazás leállt"
            path = "path"
            cannotOpenThisPath = "Nem tudja megnyitni ezt az útvonalat"
            successfulAssistance = "Örülünk, hogy segíthettünk!"
            notSatisfiedWithOurAssistance =
                "Sajnálattal halljuk, hogy nem elégedett a segítségnyújtási szolgáltatásunkkal!"
            hasBeenFinished = "Be lett fejezve."
            update = "Frissítés"
            statusHasChanged = "A key -val rendelkező eszköz állapota oldStatus-ról newStatus-ra változott."
            removeDevice = "A (key) címmel rendelkező eszköz eltávolítva."
            listening = "Figyel..."
            checking = "Ellenőrzés..."
            device = "Eszköz..."
            lastTime = "Utoljára látva"
            notProvided = "A LocalNotepadResources nem biztosított"
            companyLogo = "DCCTech logó"
            companyImage = "DCCTech kép"
            couldNotOpenedInBrowser = "Az URL(uri) nem nyitható meg a böngészőben:"
            resultListIsEmpty = "A keresési eredmények listája üres."
            deviceStatus = "Eszköz állapota"
            deviceName = "Eszköz megnevezése"
            deviceAddress = "Eszköz címe"
            deviceMac = "MAC cím"
            discoverDevices = "Eszközök keresése"
            confirmationDialog = "Biztosan kiszeretne lépni?"
            helpDialogTitle = "Súgó ablak"
            helpDialogMsg = "Ez hasznos volt?"
            preferencesDialogTitle = "Beállítások"
            wifiDialog = "Wifi hálozati információk"
            file = "Fájl"
            newWindow = "Új ablak"
            exit = "Kilépés"
            actions = "Müveletek"
            startSearch = "Keresés indítása"
            stopSearch = "Keresés leállítása"
            reset = "Eredmény törlése"
            preferences = "Beállítások"
            exitFullscreen = "Teljes képernyősmód kikapcsolása"
            enterFullscreen = "Teljes képernyősmód bekapcsolása"
            about = "Az alkalmazásról"
            theme = "Téma"
            light = "Világos"
            dark = "Sötét"
            help = "Súgó"
            infoWifi = "Wifi info"
            displayName = "Megjelenítendő név"
            name = "Név"
            address = "Cím"
            index = "Index"
            mtu = "MTU"
            hardwareAddress = "Hardvercím"
            networks = "Hálózati információk"
            devices = "Eszközök"
            unknown = "Ismeretlen"
            interfaceName = "Ismeretlen"
            network = "Hálozat"

        } else {
            appName = "Lan Spy App"
            deviceDiscovery = "Device discovery"
            running = "is running"
            stopped = "has stopped"
            path = "path"
            cannotOpenThisPath = "Cannot open this path"
            successfulAssistance = "We're glad we were able to helped!"
            notSatisfiedWithOurAssistance =
                "We're sorry to hear that you are not satisfied with our assistance service!"
            hasBeenFinished = "It has been completed."
            update = "Update"
            statusHasChanged = "The status of the device with address key has changed from oldStatus to newStatus."
            removeDevice = "Device with address (key) removed."
            listening = "Listening..."
            checking = "Checking..."
            device = "Device"
            lastTime = "Last seen"
            notProvided = "LocalNotepadResources isn't provided"
            companyLogo = "DCCTech logo"
            companyImage = "DCCTech image"
            couldNotOpenedInBrowser = "The URL(uri) couldn't be opened in the browser:"
            resultListIsEmpty = "The list of search results is empty."
            deviceStatus = "Device status"
            deviceName = "Device name"
            deviceAddress = "Device address"
            deviceMac = "MAC address"
            discoverDevices = "Discover devices"
            confirmationDialog = "Are you sure you want to exit?"
            helpDialogTitle = "Help"
            helpDialogMsg = "Was this helpful?"
            preferencesDialogTitle = "Preferences"
            wifiDialog = "Information about the WIFI network."
            file = "File"
            newWindow = "New window"
            exit = "Exit"
            actions = "Actions"
            startSearch = "Start search"
            stopSearch = "Stop search"
            reset = "Reset"
            preferences = "Preferences"
            exitFullscreen = "Exit fullscreen"
            enterFullscreen = "Enter fullscreen"
            about = "About"
            theme = "Theme"
            light = "Light"
            dark = "Dark"
            help = "Help"
            infoWifi = "Wifi info"
            displayName = "Display name"
            name = "Name"
            address = "Address"
            index = "Index"
            mtu = "MTU"
            hardwareAddress = "Hardware address"
            networks = "Network information"
            devices = "Devices"
            unknown = "Unknown"
            interfaceName = "Interface name"
            network = "Network"
        }
    }
}
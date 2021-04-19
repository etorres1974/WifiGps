package br.com.wifigps

import android.net.wifi.ScanResult

data class WifiData(
    val name : String,
    val level : Int,
    val timestamp: Long
){
    constructor(scanResult: ScanResult) : this(scanResult.SSID, scanResult.level, scanResult.timestamp)

    companion object{
        const val attributesForCSV = "name,level,timestamp"
    }

    fun formatToCsvLine() : String{
        return "\n $name,$level,$timestamp"
    }
}

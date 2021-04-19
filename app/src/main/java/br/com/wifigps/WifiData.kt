package br.com.wifigps

import android.net.wifi.ScanResult
import java.time.LocalDateTime
import java.util.*

data class WifiData(
    val name : String,
    val level : Int,
    val timestamp: LocalDateTime
){
    constructor(scanResult: ScanResult) : this(scanResult.SSID, scanResult.level, LocalDateTime.now())

    companion object{
        const val attributesForCSV = "name,level,timestamp"
    }

    fun formatToCsvLine() : String{
        return "\n $name,$level,$timestamp"
    }
}

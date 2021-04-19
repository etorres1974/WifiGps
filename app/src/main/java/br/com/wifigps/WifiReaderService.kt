package br.com.wifigps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.util.Log

class WifiReaderService(private val context: Context) {

    var dataList : List<WifiData> = emptyList()

    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                scanSuccess()
            } else {
                scanFailure()
            }
        }
    }
    private val intentFilter = IntentFilter()

    init {
        Log.d("TEST ", "I am initialized")
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)
        wifiManager.startScan()
    }

    private fun scanSuccess() {
        val results = wifiManager.scanResults
        val data = results.map{ WifiData(it) }
        dataList = dataList.plus(data)
        Log.d("TEST S", data.toString())
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        Log.d("TEST F", results.toString())
    }

}
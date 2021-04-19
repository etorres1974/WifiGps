package br.com.wifigps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WifiReaderService(context: Context) {

    private val _dataList = MutableLiveData<List<WifiData>>().apply {
        value = emptyList()
    }

    val dataList: LiveData<List<WifiData>> = _dataList

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
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)
    }

    private fun scanSuccess() {
        val results = wifiManager.scanResults
        val data = results.map { WifiData(it) }
        _dataList.value = _dataList.value?.plus(data)
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        Log.d("TEST Scan Fail", results.toString())
    }

}
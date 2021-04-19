package br.com.wifigps.ui.wifi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.wifigps.CSVHelper
import br.com.wifigps.WifiData

class WifiViewModel(app : Application) : AndroidViewModel(app) {

    private val context = getApplication<Application>().applicationContext
    private val _dataList = MutableLiveData<List<WifiData>>()
    private val _scanCounter = MutableLiveData<Int>()

    val dataList: LiveData<List<WifiData>> = _dataList
    val scanCounter: LiveData<Int> = _scanCounter

    init {
        _dataList.value = emptyList()
        _scanCounter.value = -1
    }


    fun updateWifiData(data : List<WifiData>){
        _dataList.value = _dataList.value?.plus(data)
        _scanCounter.value = _scanCounter.value?.plus(1)
    }

    fun exportCsv(){
        _dataList.value?.let { CSVHelper.createCSV(context, it) }
    }
}
package br.com.wifigps.ui.notifications

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.wifigps.CSVHelper
import br.com.wifigps.WifiData

class NotificationsViewModel(app : Application) : AndroidViewModel(app) {

    private val context = getApplication<Application>().applicationContext
    private val _dataList = MutableLiveData<List<WifiData>>()
    private val _scanCounter = MutableLiveData<Int>()

    val dataList: LiveData<List<WifiData>> = _dataList
    val scanCounter: LiveData<Int> = _scanCounter

    init {
        _dataList.value = emptyList()
        _scanCounter.value = 0
    }


    fun updateWifiData(data : List<WifiData>){
        _dataList.value = _dataList.value?.plus(data)
        _scanCounter.value = _scanCounter.value?.plus(1)
    }

    fun exportCsv(){
        _dataList.value?.let { CSVHelper.createCSV(context, it) }
    }
}
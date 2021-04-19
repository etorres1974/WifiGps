package br.com.wifigps

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

class CSVHelper() {
    companion object{
    fun createCSV(context: Context, list: List<WifiData>){
        var data = StringBuilder()
        data.append(WifiData.attributesForCSV)
        list.forEach { data.append(it.formatToCsvLine()) }
        try{
            context.openFileOutput(FILE_NAME,Context.MODE_PRIVATE).apply {
                write(data.toString().toByteArray())
                close()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun export(context: Context){
        var fileLocation = File(context.filesDir, FILE_NAME)
        var path = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName,
            fileLocation
        )
        var intent = Intent(Intent.ACTION_SEND).apply {
            setType(TYPE_CSV)
            putExtra(Intent.EXTRA_SUBJECT, "Wifi Data")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_STREAM, path)
            context.startActivity(Intent.createChooser(this, "Send Email"))
        }
    }


        const val FILE_NAME = "data.csv"
        const val TYPE_CSV = "text/csv"
    }

}
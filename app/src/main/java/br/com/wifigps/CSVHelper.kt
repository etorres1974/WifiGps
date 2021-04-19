package br.com.wifigps

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class CSVHelper {
    companion object {

        fun createCSV(context: Context, list: List<WifiData>) {
            var data = StringBuilder()
            data.append(WifiData.attributesForCSV)
            list.forEach { data.append(it.formatToCsvLine()) }
            try {
                context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).apply {
                    write(data.toString().toByteArray())
                    close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun getExportUri(context: Context): Uri? {
            var fileLocation = File(context.filesDir, FILE_NAME)
            return FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName,
                fileLocation
            )
        }


        const val FILE_NAME = "data.csv"
        const val TYPE_CSV = "text/csv"
    }

}
package br.com.wifigps.ui.notifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.wifigps.CSVHelper
import br.com.wifigps.R
import br.com.wifigps.WifiData
import br.com.wifigps.WifiReaderService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private lateinit var wifiReaderService: WifiReaderService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        wifiReaderService = WifiReaderService(requireContext())
        setupUi(root)
        subscribeUi(root)
        return root
    }

    private fun setupUi(root: View) {
        root.findViewById<Button>(R.id.button_export_csv).setupExportButton()

    }

    private fun subscribeUi(root: View) {
        wifiReaderService.dataList.observe(
            viewLifecycleOwner,
            notificationsViewModel::updateWifiData
        )
        notificationsViewModel.dataList.observe(viewLifecycleOwner) {
            root.findViewById<TableLayout>(R.id.table_layout).setupTable(it)
            root.findViewById<TextView>(R.id.text_view_rows_counter).setupRowsCounter(it)
        }
        notificationsViewModel.scanCounter.observe(viewLifecycleOwner) {
            root.findViewById<TextView>(R.id.text_view_scan_counter).setupScanCounter(it)
        }
    }

    private fun Button.setupExportButton() {
        setOnClickListener {
            notificationsViewModel.exportCsv()
            CSVHelper.getExportUri(context)?.let { exportIntent(it) }
        }
    }

    private fun TextView.setupRowsCounter(list: List<WifiData>) {
        text = "Rows : ${list.size}"
    }

    private fun TextView.setupScanCounter(count: Int) {
        text = "Scans : $count"
    }

    private fun TableLayout.setupTable(list: List<WifiData>) {
        list.forEach { addRow(it) }
    }

    private fun TableLayout.addRow(wifiData: WifiData) {
        addView(TableRow(requireContext()).apply {
            addWifiDataLine(wifiData)
        })
    }

    private fun TableRow.addWifiDataLine(wifiData: WifiData) {
        addView(TextView(requireContext()).apply {
            text = wifiData.level.toString()
        })
        addView(TextView(requireContext()).apply {
            text = wifiData.name
        })
        addView(TextView(requireContext()).apply {
            text = getDate(wifiData.timestamp)
        })
    }

    private fun getDate(localDateTime: LocalDateTime): String? {
        val format = DateTimeFormatter.ofPattern(" hh:mm - dd/MM/yyyy")
        return localDateTime.format(format)
    }

    fun exportIntent(uri: Uri) {
        var intent = Intent(Intent.ACTION_SEND).apply {
            type = CSVHelper.TYPE_CSV
            putExtra(Intent.EXTRA_SUBJECT, "Wifi Data")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_STREAM, uri)
            context?.startActivity(Intent.createChooser(this, "Send Email"))
        }
    }


}
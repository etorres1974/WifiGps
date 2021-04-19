package br.com.wifigps.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.wifigps.CSVHelper
import br.com.wifigps.R
import br.com.wifigps.WifiReaderService

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
        val textView: TextView = root.findViewById(R.id.text_notifications)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        wifiReaderService = WifiReaderService(requireContext())
        root.findViewById<Button>(R.id.button_export_csv).setupExportButton()
        return root
    }

    fun Button.setupExportButton(){
        setOnClickListener {
            CSVHelper.createCSV(requireContext(), wifiReaderService.dataList)
            CSVHelper.export(requireContext())
        }
    }


}
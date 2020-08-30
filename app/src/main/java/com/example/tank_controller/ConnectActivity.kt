package com.example.tank_controller

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_connect_view.*
import org.jetbrains.anko.toast

class ConnectActivity : AppCompatActivity() {

    val REQUEST_ENABLE_BLUETOOTH = 1

    lateinit var listView: ListView
    lateinit var pairedDevices: Set<BluetoothDevice>

    var bluetoothAdapter:BluetoothAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_view)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if(bluetoothAdapter==null) {
            toast("This device doesn't support Bluetooth")
            return
        }

        if(!bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        select_device_refresh.setOnClickListener{ pairedDeviceList() }
    }

    private fun pairedDeviceList() {
        pairedDevices = bluetoothAdapter!!.bondedDevices
        listView = findViewById(R.id.select_device_list)

        val list : ArrayList<BluetoothDevice> = ArrayList()

        if(!pairedDevices.isEmpty()){
            for(device:BluetoothDevice in pairedDevices){
                list.add(device)
            }
        } else {
            toast("No paired Bluetooth devices found")
        }

        val adapter = ArrayAdapter(this, R.layout.list_view_item, list)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device: BluetoothDevice = list[position]
            val address: String = device.address
            selectDevice(address)
       }
    }

    private fun selectDevice(address: String) {
        toast(address)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if(resultCode == Activity.RESULT_OK) {
                if(bluetoothAdapter!!.isEnabled) {
                    toast("Bluetooth has been enabled")
                } else {
                    toast("Bluetooth has been disabled")
                }
            } else if(resultCode == Activity.RESULT_CANCELED) {
                toast("Bluetooth enabling has been canceled")
            }
        }
    }


}
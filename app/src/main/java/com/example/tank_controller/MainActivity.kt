package com.example.tank_controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val driverButton = findViewById<Button>(R.id.driverViewButton)
        driverButton.setOnClickListener{
            val intent = Intent(this, driverView::class.java)
            startActivity(intent)
        }

        val shooterButton = findViewById<Button>(R.id.shooterViewButton)
        shooterButton.setOnClickListener{
            val intent = Intent(this, shooterView::class.java)
            startActivity(intent)
        }

        val settingsButton = findViewById<Button>(R.id.settingsViewButton)
        settingsButton.setOnClickListener{
            val intent = Intent(this, settingsView::class.java)
            startActivity(intent)
        }
    }
}

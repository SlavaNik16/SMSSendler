package com.example.smssenlder

import android.Manifest.permission.READ_PHONE_STATE
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_READ_PHONE_STATE = 123

    private lateinit var phoneNumberTextView: TextView
    private lateinit var getNumberButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phoneNumberTextView = findViewById(R.id.phone_number)
        getNumberButton = findViewById(R.id.button)

        // Check and request permission when activity is created
        checkAndRequestPermission()

        getNumberButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(READ_PHONE_STATE),
                    PERMISSION_REQUEST_READ_PHONE_STATE
                )
            } else {
                displayPhoneNumber()
            }
        }

    }

    private fun checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_PHONE_STATE),
                PERMISSION_REQUEST_READ_PHONE_STATE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_READ_PHONE_STATE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Разрешение предоставлено, выполните операции, требующие это разрешение
                    displayPhoneNumber()
                } else {
                    // Разрешение не предоставлено, показать сообщение пользователю или попробовать запросить разрешение снова
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }



    private fun displayPhoneNumber() {
        if (ContextCompat.checkSelfPermission(
                this,
                READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            val phoneNumber = telephonyManager.line1Number
            phoneNumberTextView.text = phoneNumber ?: "Phone number not available"
        } else {
            // Permission not granted, show error message
            Toast.makeText(
                this,
                "Permission denied",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

package com.example.smssenlder

import android.Manifest.permission.READ_PHONE_NUMBERS
import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.READ_SMS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.telephony.TelephonyManager
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class PhoneActivity : AppCompatActivity() {
    var phone_number: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)
        phone_number = findViewById(R.id.phone_number);
    }

    // Function will run after click to button
    fun GetNumber(v: View?) {
        if (ActivityCompat.checkSelfPermission(
                this,
                READ_SMS
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                READ_PHONE_NUMBERS
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission check

            // Create obj of TelephonyManager and ask for current telephone service
            val telephonyManager = this.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            val phoneNumber = telephonyManager.line1Number
            phone_number?.setText(phoneNumber)
            return
        } else {
            // Ask for permission
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf<String>(READ_SMS, READ_PHONE_NUMBERS, READ_PHONE_STATE), 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                val telephonyManager = this.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
                if (ActivityCompat.checkSelfPermission(this, READ_SMS) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        READ_PHONE_NUMBERS
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        READ_PHONE_STATE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                val phoneNumber = telephonyManager.line1Number
                phone_number?.setText(phoneNumber)
            }
            else -> throw IllegalStateException("Unexpected value: $requestCode")
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
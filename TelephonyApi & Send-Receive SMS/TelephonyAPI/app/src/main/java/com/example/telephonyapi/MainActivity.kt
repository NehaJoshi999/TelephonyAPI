package com.example.telephonyapi

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE),111)
        }
        else
        {
            getTelephonyInfo()
        }
    }

    private fun getTelephonyInfo() {
        var tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        var data = ""
        when(tm.phoneType)
        {
            TelephonyManager.PHONE_TYPE_CDMA -> { data = "CDMA" }
            TelephonyManager.PHONE_TYPE_GSM -> { data = "GSM"}
            TelephonyManager.PHONE_TYPE_NONE -> { data = "None"}
        }
        data = tm.networkOperatorName
        data += "\n"+ tm.simCountryIso
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_NUMBERS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        data += "\n"+ tm.line1Number
        data += "\n"+ tm.deviceSoftwareVersion
        data += "\n"+ tm.isNetworkRoaming
        Toast.makeText(applicationContext,data,Toast.LENGTH_LONG).show()
    }

    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            getTelephonyInfo()
        }
    }*/
}
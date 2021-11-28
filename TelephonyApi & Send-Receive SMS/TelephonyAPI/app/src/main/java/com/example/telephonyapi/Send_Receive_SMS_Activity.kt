package com.example.telephonyapi

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_send_receive_sms.*

class Send_Receive_SMS_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_receive_sms)

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS),111)
        }
        else
        {
            receiveSMS()
        }

        //sending sms
        button.setOnClickListener {
            var sms = SmsManager.getDefault()
            sms.sendTextMessage(edPhone.text.toString(),"me",edMsg.text.toString(),null,null)
        }

        //sending mms

       /* button.setOnClickListener {
            var attached_Uri = Uri.parse("content://media/external/images/media/1");
            var i = Intent(Intent.ACTION_SEND,attached_Uri)
            i.putExtra("sms_body","neha")
            i.putExtra("address","999999999")
            i.setType("image/jpeg")
            startActivity(i)
        }*/
    }

    private fun receiveSMS() {
        var br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)){
                        edPhone.setText(sms.originatingAddress)
                        edMsg.setText(sms.displayMessageBody)
                        //Toast.makeText(applicationContext,sms.displayMessageBody,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            receiveSMS()
        }
    }
}
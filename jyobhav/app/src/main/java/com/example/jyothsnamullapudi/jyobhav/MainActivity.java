package com.example.jyothsnamullapudi.jyobhav;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        findViewById(R.id.btnSendSMS).setOnClickListener(this);
    }

    public void onClick(View v) {
        String phoneNumber = ((EditText)
                findViewById(R.id.editText)).getText().toString();
        String sms = ((EditText)
                findViewById(R.id.editText2)).getText().toString();
        try {
            SmsManager.getDefault().sendTextMessage(phoneNumber, null, sms , null, null);
        } catch (Exception e) {
            AlertDialog.Builder alertDialogBuilder = new
                    AlertDialog.Builder(this);
            AlertDialog dialog = alertDialogBuilder.create();

            dialog.setMessage(e.getMessage());

            dialog.show();

        }
    }
}
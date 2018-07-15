package in.jpmc.student;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.firebase.auth.FirebaseAuth;

public class sms extends AppCompatActivity {

    BootstrapButton bm2, btnSendSM;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        //bm2 = (BootstrapButton)findViewById(R.id.logout);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        btnSendSM = (BootstrapButton) findViewById(R.id.btnSendSMS);

        btnSendSM.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              String phoneNumber = ((BootstrapEditText) findViewById(R.id.phonenumber)).getText().toString();
                                              String sms = ((BootstrapEditText) findViewById(R.id.message)).getText().toString();
                                              try {
                                                  SmsManager.getDefault().sendTextMessage(phoneNumber, null, sms, null, null);
                                              } catch (Exception e) {
                                                  AlertDialog.Builder alertDialogBuilder = new
                                                          AlertDialog.Builder(sms.this);
                                                  AlertDialog dialog = alertDialogBuilder.create();

                                                  dialog.setMessage(e.getMessage());

                                                  dialog.show();

                                                  //OnClick();

                                              }

                                          }
                                      }
        );
    }



    public void OnClick()
    {
        bm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(sms.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}

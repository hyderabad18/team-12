package in.jpmc.student;

/**
 * Created by Jyothsna Mullapudi on 15-07-2018.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
public class  approvaladmin  extends AppCompatActivity{
        private Button approve1;
        private Button  disapprove1;
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.adminapproval);
            approve1= (Button)findViewById(R.id.b1);
            disapprove1= (Button)findViewById(R.id.b2);

                approve1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(approvaladmin.this, sms.class));
                    }

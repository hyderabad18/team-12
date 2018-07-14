package in.jpmc.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class Volunteer extends AppCompatActivity {
    private Button upl,lout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        upl=(Button)findViewById(R.id.upload);
        lout=(Button)findViewById(R.id.logout);
        upl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Volunteer.this,Upload.class);
                startActivity(intent);
            }
        });
        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent=new Intent(Volunteer.this,Upload.class);
                //startActivity(intent);
            }
        });
    }
}

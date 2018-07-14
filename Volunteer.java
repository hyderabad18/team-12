package in.jpmc.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Volunteer extends AppCompatActivity {
    private Button upld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        upld=(Button)findViewById(R.id.upload);
        upld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Volunteer.this,Upload.class);
                startActivity(intent);
            }
        });
    }
}

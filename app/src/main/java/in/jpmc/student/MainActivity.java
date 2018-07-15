package in.jpmc.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import details.Students;
import details.Volunteers;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private BootstrapEditText Name;
    private BootstrapEditText Password;
    private TextView Info;
    private BootstrapButton Login, userRegistration;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference mydb = db.getReference().child("Students");
    final DatabaseReference mydb2 = db.getReference().child("Volunteers");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (BootstrapEditText)findViewById(R.id.etName);
        Password = (BootstrapEditText)findViewById(R.id.etPassword);
        Login = (BootstrapButton)findViewById(R.id.btnLogin);
        userRegistration = (BootstrapButton)findViewById(R.id.btnSignup);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);


        //Info.setText("No of attempts remaining: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected_id = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selected_id);

                if(Name.getText().toString().equals("admin") &&
                        Password.getText().toString().equals("admin") && radioButton.getText().toString().equals("Admin") )
                {
                    Intent i = new Intent(MainActivity.this, MainAdmin.class);
                    startActivity(i);
                }


                if (radioButton.getText().toString().equals("Student")) {

                    mydb.addValueEventListener(new ValueEventListener() {
                        //DatabaseReference userref = mydb.child();
                        @Override


                        public void onDataChange(@NonNull DataSnapshot ds) {

                            final String s1 = Name.getText().toString();

                            final String s2 = Password.getText().toString();


                            if (ds.child(s1).exists()) {
                                Students u = ds.child(s1).getValue(Students.class);
                                String p = u.getpassword().toString();


                                if (u.getpassword().equals(s2)) {
                                    Intent i = new Intent(MainActivity.this,Student.class);
                                    //i.putExtra("msg", s1);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //add code here to check for volunteer and students
                    //validate(Name.getText().toString(), Password.getText().toString());
                }
                else
                {
                    mydb2.addValueEventListener(new ValueEventListener() {
                        @Override

                        public void onDataChange(@NonNull DataSnapshot ds) {

                            final String s1 = Name.getText().toString();
                            final String s2 = Password.getText().toString();

                            if (ds.child(s1).exists()) {
                                Volunteers u = ds.child(s1).getValue(Volunteers.class);

                                if (u.getpassword().equals(s2)) {
                                    Intent i = new Intent(MainActivity.this, Volunteer.class);
                                    //i.putExtra("msg", s1);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });



        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });

        /*forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
            }
        });*/
    }


    private void validate(String userName, String userPassword) {

        progressDialog.setMessage("You can subscribe to my channel until you are verified!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    //checkEmailVerification();
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if(counter == 0){
                        Login.setEnabled(false);
                    }
                }
            }
        });


    }

   /* private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(MainActivity.this, SecondActivity.class));

//        if(emailflag){
//            finish();
//            startActivity(new Intent(MainActivity.this, SecondActivity.class));
//        }else{
//            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
//            firebaseAuth.signOut();
//        }
    }*/

}


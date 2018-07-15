package in.jpmc.student;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Student extends AppCompatActivity {

    StorageReference mstorage, filepath, down;
    private BootstrapButton btm, btm2;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference mydb = db.getReference().child("Admin");
    private BootstrapEditText text;
    FirebaseAuth mAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    AdminDTO a1 = new AdminDTO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        btm = (BootstrapButton) findViewById(R.id.btnSendSMS);
        btm2 = (BootstrapButton) findViewById(R.id.logout);
        text = (BootstrapEditText) findViewById(R.id.edit);
        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();
        btm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();

                Intent i = new Intent(Student.this, MainActivity.class);
                startActivity(i);
            }
        });
        btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = text.getText().toString();
                String number = "1";
                a1.setText(s1);
                a1.setNumber(number);

                mydb.child(number).setValue(a1);


                mydb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(Student.this, "Requested", Toast.LENGTH_SHORT).show();
                mstorage = FirebaseStorage.getInstance().getReference();
                //startdownload();
                signInAnonymously();
            }
        });


    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
                startdownload();
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("As", "signInAnonymously:FAILURE", exception);
                    }
                });
    }

    private void startdownload() {
        //StorageReference filepath = mstorage.child("audio").child("new_Audio.mp3");
//download
        down = mstorage.child("audio").child("new_audio.mp3");

        File localFile = null;
        try {
            localFile = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "new_Audio.mp3");
            localFile.createNewFile();
            down.getFile(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        down.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Downloaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }


        });

    }

}

// do your stuf


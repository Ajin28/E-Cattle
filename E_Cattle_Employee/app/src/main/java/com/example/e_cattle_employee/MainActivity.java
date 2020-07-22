package com.example.e_cattle_employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {


    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseuser;
    EditText editText_email,editText_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_email=findViewById(R.id.login_email);
        editText_password=findViewById(R.id.login_password);

        progressBar=findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseuser=firebaseAuth.getCurrentUser();
        /*
        if(firebaseuser!=null){
            Intent intent= new Intent(getApplicationContext(),SelectReport.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
         */

    }




    public void openEmployee(View view) {

        final   String sEmail=editText_email.getText().toString().trim();
        String sPassword=editText_password.getText().toString().trim();
        if(sEmail.isEmpty()){
            editText_email.setError("Email is required");
            editText_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches())
        {
            editText_email.setError("Email is invalid");
            editText_email.requestFocus();
            return;
        }
        if(sPassword.isEmpty()){
            editText_password.setError("Password is required");
            editText_password.requestFocus();
            return;
        }

        if(sPassword.length()<6){
            editText_password.setError("Minimum length of password is 6");
            editText_password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(sEmail,sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            /*
                            final String employeeEmail=sEmail;
                            DatabaseReference Values= FirebaseDatabase.getInstance().getReference().child("User");
                            Values.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot user: dataSnapshot.getChildren()){

                                        String userEmail=user.child("Email").getValue().toString();
                                        if(employeeEmail.equalsIgnoreCase(userEmail)){
                                            Toast.makeText(getApplicationContext(),"Already registered as a user",Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                            return;

                                        }


                                    }
                                    progressBar.setVisibility(View.GONE);

                                    Intent intent= new Intent(getApplicationContext(),SelectReport.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);





                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                             */

                            progressBar.setVisibility(View.GONE);

                            Intent intent= new Intent(getApplicationContext(),SelectReport.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });




    }


    public void signUp(View view) {
        Intent intent= new Intent(getApplicationContext(),Register.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

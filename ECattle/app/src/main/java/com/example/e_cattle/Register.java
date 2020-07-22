package com.example.e_cattle;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    ProgressBar progressBar;
    EditText email,password,phone,name;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.register_email);
        password=findViewById(R.id.register_password);
        progressBar=findViewById(R.id.progressBar1);
       // phone=findViewById(R.id.register_phone);
      //  name=findViewById(R.id.register_name);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signIn(View view) {
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void registerUser(View view) {

        /*

        String sEmail=email.getText().toString().trim();
        String sPassword=password.getText().toString().trim();
        final String sPhone=phone.getText().toString().trim();
        final String sName=name.getText().toString().trim();
        if(sEmail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.PHONE.matcher(sPhone).matches())
        {
            phone.setError("Phone is invalid");
            phone.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches())
        {
            email.setError("Email is invalid");
            email.requestFocus();
            return;
        }
        if(sPassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(sName.isEmpty()){
            name.setError("Name is required");
            name.requestFocus();
            return;
        }

        if(sPhone.isEmpty()){
            phone.setError("Phone is required");
            phone.requestFocus();
            return;
        }
        if(sPassword.length()<6){
            password.setError("Minimum length of password is 6");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(sEmail,sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {


                            DatabaseReference Values= FirebaseDatabase.getInstance().getReference().child("Employee").child(FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid());
                            Values.child("Email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            Values.child("Name").setValue(sName);
                            Values.child("Phone").setValue(sPhone);


                            Toast.makeText(getApplicationContext(),"Registeration complete",Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });








         */

        String sEmail=email.getText().toString().trim();
        String sPassword=password.getText().toString().trim();
        if(sEmail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches())
        {
            email.setError("Email is invalid");
            email.requestFocus();
            return;
        }

        if(sPassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(sPassword.length()<6){
            password.setError("Minimum length of password is 6");
            password.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(sEmail,sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {


                            DatabaseReference Values= FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid());
                            Values.child("Email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                            Toast.makeText(getApplicationContext(),"Registeration complete",Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

    }




    @Override
    public void onBackPressed() {

        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

}

package com.example.e_cattle;

import android.content.Intent;
import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ;
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
            Intent intent= new Intent(getApplicationContext(),Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
         */


    }


    public void signUp(View view) {
        Intent intent= new Intent(getApplicationContext(),Register.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void openUser(View view) {

        final String sEmail=editText_email.getText().toString().trim();
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

                            final String userEmail=sEmail;
                            DatabaseReference Values= FirebaseDatabase.getInstance().getReference().child("Employee");
                            Values.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot employee: dataSnapshot.getChildren()){

                                        String employeeEmail=employee.child("Email").getValue().toString();
                                        if(userEmail.equalsIgnoreCase(employeeEmail)){
                                            Toast.makeText(getApplicationContext(),"Already registered as a employee",Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);

                                            return;

                                        }


                                    }

                                    progressBar.setVisibility(View.GONE);

                                    Intent intent= new Intent(getApplicationContext(),Main2Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);





                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                             */
                            progressBar.setVisibility(View.GONE);

                            Intent intent= new Intent(getApplicationContext(),Main2Activity.class);
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

   /*
    public void openEmployee(View view) {

        String sEmail=editText_email.getText().toString().trim();
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
                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful())
                        {

                            Intent intent= new Intent(getApplicationContext(),SelectReport.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                             startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });




    }



    public void openHomepage(View view) {
        Intent intent1= new Intent(getApplicationContext(),Main2Activity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
    }


    */
}

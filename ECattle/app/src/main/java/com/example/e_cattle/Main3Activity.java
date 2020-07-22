package com.example.e_cattle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main3Activity extends AppCompatActivity {

    private static final int MY_Phone_REQUEST_CODE = 111;
    String report;
    String emplyeeid;
    TextView Name,Phone,Email;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Name=findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Phone = findViewById(R.id.Phone);

        emplyeeid="";

        phone="";
        report=getIntent().getStringExtra("reportID");
        askPhonePermission();
        phone="";
        if(report!=null){

            DatabaseReference employeedetails= FirebaseDatabase.getInstance().getReference().child("Reports").child(report)
                    .child("TakenBy");
            employeedetails.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        emplyeeid=dataSnapshot.getValue().toString();
                        Toast.makeText(Main3Activity.this, emplyeeid, Toast.LENGTH_SHORT).show();
                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Employee").child(emplyeeid);
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                for(DataSnapshot detail:dataSnapshot.getChildren()){
                                    String key=detail.getKey().toString();
                                    String ans=detail.getValue().toString();
                                    //Toast.makeText(Main3Activity.this, ans, Toast.LENGTH_SHORT).show();
                                    if(key.equalsIgnoreCase("name")){
                                        Name.setText(ans);
                                        Name.setVisibility(View.VISIBLE);
                                       // Toast.makeText(Main3Activity.this, ans, Toast.LENGTH_SHORT).show();

                                    }
                                    if(key.equalsIgnoreCase("phone")){
                                        Phone.setText(ans);
                                        Phone.setVisibility(View.VISIBLE);
                                        phone=ans;

                                    }

                                    if(key.equalsIgnoreCase("email")){
                                        Email.setText(ans);
                                        Email.setVisibility(View.VISIBLE);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }




    }
    void askPhonePermission()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Main3Activity.this, new String[] {Manifest.permission.CALL_PHONE}, MY_Phone_REQUEST_CODE);

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_Phone_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Phone permission granted", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Phone permission denied", Toast.LENGTH_LONG).show();
            }
        }


    }

    public void call(View view) {
        Toast.makeText(this, phone, Toast.LENGTH_SHORT).show();
        if(phone.length()>0){

            String num="tel:"+phone;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(num)));
        }
    }

}

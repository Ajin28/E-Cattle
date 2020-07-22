package com.example.e_cattle_employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectReport extends AppCompatActivity {

    TextView Name,Email,Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_report);
        Name=findViewById(R.id.EmployeeName);
        Email=findViewById(R.id.EmployeeEmail);
        Phone=findViewById(R.id.EmployeePhone);
        String currentEmployee=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        DatabaseReference Employee= FirebaseDatabase.getInstance().getReference().child("Employee").child(currentEmployee);
        Employee.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot value:dataSnapshot.getChildren())
                {
                    String key=value.getKey().toString();
                    String ans=value.getValue().toString();
                    if(key.equalsIgnoreCase("name")){
                        Name.setText("Name: "+ans);
                        Name.setVisibility(View.VISIBLE);

                    }
                    if(key.equalsIgnoreCase("phone")){
                        Phone.setText("Phone: "+ans);
                        Phone.setVisibility(View.VISIBLE);

                    }

                    if(key.equalsIgnoreCase("email")){
                        Email.setText("Email: "+ans);
                        Email.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openReportView(View view) {

        Intent intent= new Intent(getApplicationContext(),ReportView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }





    public void openOnGoing(View view) {
        Intent intent= new Intent(getApplicationContext(),OnGoingReports.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void openCompleted(View view) {

        Intent intent= new Intent(getApplicationContext(),CompletedReports.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void openIncompleted(View view) {

        Intent intent= new Intent(getApplicationContext(),IncompleteReports.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

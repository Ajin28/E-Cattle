package com.example.e_cattle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SigleReport extends AppCompatActivity {

    TextView ReportID,Animal,Description,Latitude,Longitude;
    String report;

    Button button;
    String status;
    RecyclerView recyclerView;
    ArrayList<String> list;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigle_report);
        ReportID= findViewById(R.id.reportsingle);
        Animal= findViewById(R.id.animal);
        Description= findViewById(R.id.DES);
        Latitude= findViewById(R.id.sinLAT);
        Longitude= findViewById(R.id.sinLONG);

        button=findViewById(R.id.button);


        recyclerView=findViewById(R.id.recyclerView);
        layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        list= new ArrayList<String>();
        list.clear();
        report=getIntent().getStringExtra("reportID");

       if(report!=null)
       {

           DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Reports").child(report);

           ReportID.setText("⦿ ReportID: "+report);
           databaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   for(DataSnapshot ReportDetails:dataSnapshot.getChildren())
                   {


                       String key=ReportDetails.getKey();
                       String value=ReportDetails.getValue().toString();
                       if(key.equalsIgnoreCase("latitude")){
                           Latitude.setText("⦿ Latitude: "+value);
                       }
                       if(key.equalsIgnoreCase("longitude")){
                           Longitude.setText("⦿ Longitude: "+value);
                       }
                       if(key.equalsIgnoreCase("animal")){
                           Animal.setText("⦿ Animal: "+value);
                       }
                       if(key.equalsIgnoreCase("description")){
                           Description.setText("⦿ Description: "+value);
                       }
                       if(key.equalsIgnoreCase("status")){
                          status=value;
                           if(status.equalsIgnoreCase("on going")){
                               button.setVisibility(View.VISIBLE);
                           }
                       }
                       if(key.equalsIgnoreCase("images")){


                          for(DataSnapshot Pictureurl:ReportDetails.getChildren()){
                              list.add(Pictureurl.getValue().toString());
                          }


                           SingleReportAdapter recyclerAdapter= new SingleReportAdapter(list);
                           recyclerView.setAdapter(recyclerAdapter);
                       }


                   }



               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

       }
       else{
           Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
       }

    }
   /* public void opentag(View view) {


        if(status.equalsIgnoreCase("pending"))
        {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Reports").child(report).child("Status");
            databaseReference.setValue("On Going");
            DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference().child("Reports").child(report).child("TakenBy");
            databaseReference1.setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
            DatabaseReference database= FirebaseDatabase.getInstance().getReference().child("Employee").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("OngoingReports");
            database.push().setValue(report);

            Toast.makeText(this, "Report Accepted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Already Taken", Toast.LENGTH_SHORT).show();
        }

    }
    */
    public void onBackPressed() {

        Intent intent= new Intent(getApplicationContext(),ReportView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void openEmployeeDetails(View view) {

        Intent intent= new Intent(getApplicationContext(),Main3Activity.class);
        intent.putExtra("reportID",report);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}

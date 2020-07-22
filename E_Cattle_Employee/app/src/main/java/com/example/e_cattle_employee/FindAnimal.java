package com.example.e_cattle_employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindAnimal extends AppCompatActivity {

    private String report;
    private String Lat,Lang;
    private TextView ReportID,Animal,Description,Latitude,Longitude;
    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_animal);
        report=getIntent().getStringExtra("reportID");
        ReportID= findViewById(R.id.reportsingleF);
        Animal= findViewById(R.id.animalF);
        Description= findViewById(R.id.DESF);
        Latitude= findViewById(R.id.sinLATF);
        Longitude= findViewById(R.id.sinLONGF);


        recyclerView=findViewById(R.id.recyclerViewF);
        layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        list= new ArrayList<String>();
        list.clear();
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
                            Lat=value;
                        }
                        if(key.equalsIgnoreCase("longitude")){
                            Longitude.setText("⦿ Longitude: "+value);
                            Lang=value;
                        }
                        if(key.equalsIgnoreCase("animal")){
                            Animal.setText("⦿ Animal: "+value);
                        }
                        if(key.equalsIgnoreCase("description")){
                            Description.setText("⦿ Description: "+value);
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

    /*
    public void found(View view) {
        Intent intent= new Intent(getApplicationContext(),TagId.class);
        intent.putExtra("reportID",report);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void notfound(View view) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Reports").child(report)
                .child("Status");
        databaseReference.setValue("Incomplete");
        DatabaseReference database= FirebaseDatabase.getInstance().getReference().child("Employee").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("IncompleteReports");
        database.push().setValue(report);
        Toast.makeText(this, "Status updated to Incomplete", Toast.LENGTH_SHORT).show();
    }


     */
    public void onBackPressed() {

        Intent intent= new Intent(getApplicationContext(),OnGoingReports.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void find(View view) {
        Intent intent= new Intent(getApplicationContext(),MapAnimal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("reportID",report);
        intent.putExtra("LAT",Lat);
        intent.putExtra("LANG",Lang);
        startActivity(intent);
    }
}

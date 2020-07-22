package com.example.e_cattle_employee;


import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CompletedReports  extends AppCompatActivity implements MultipleReportsAdapter.OnItemClickListener {

    DatabaseReference Report;
    MultipleReportsAdapter multipleReportsAdapter;
    RecyclerView recyclerView;

    ArrayList<MultipleReports> list;
    RecyclerView.LayoutManager layoutManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_reports);
        recyclerView=findViewById(R.id.recyclercomplete);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        list= new ArrayList<MultipleReports>();
        list.clear();
        multipleReportsAdapter= new MultipleReportsAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(multipleReportsAdapter);
        multipleReportsAdapter.set(CompletedReports.this);



        final DatabaseReference database= FirebaseDatabase.getInstance().getReference().child("Employee").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("CompletedReports");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ReportIDs:dataSnapshot.getChildren())
                {
                    final  String  reportIDs=ReportIDs.getValue().toString();

                    if(reportIDs!=null)
                    {

                        Report=FirebaseDatabase.getInstance().getReference().child("Reports").child(reportIDs);
                        Report.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                                MultipleReports multipleReports=new MultipleReports("","","","https://firebasestorage.googleapis.com/v0/b/focus-electron-262521.appspot.com/o/download.png?alt=media&token=9638d8fa-1365-4d6c-84c7-82a25eb4a3aa","Pending");
                                multipleReports.setReportID(reportIDs);
                                for(DataSnapshot ReportDetails:dataSnapshot1.getChildren())
                                {


                                    String key=ReportDetails.getKey();
                                    String value =ReportDetails.getValue().toString();
                                    if(key.equalsIgnoreCase("latitude")){
                                        multipleReports.setLatitude(value);
                                    }
                                    if(key.equalsIgnoreCase("longitude")){
                                        multipleReports.setLongitude(value);
                                    }
                                    if(key.equalsIgnoreCase("images")){
                                        multipleReports.setImageURL( ReportDetails.child("image_0").getValue().toString());

                                    }

                                    if(key.equalsIgnoreCase("status")){
                                        multipleReports.setStatus(value);
                                    }

                                }
                                list.add(multipleReports);
                                multipleReportsAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError1) {

                            }
                        });





                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void OnSingleItemClick(int position) {





    }

}

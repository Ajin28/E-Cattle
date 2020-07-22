package com.example.e_cattle_employee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReportView extends AppCompatActivity implements MultipleReportsAdapter.OnItemClickListener{

    private DatabaseReference CurrentUser;
    private DatabaseReference Report;
    private MultipleReportsAdapter multipleReportsAdapter;
    private RecyclerView recyclerView;
    private String statuscheck;
    private ArrayList<MultipleReports> list;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_view);

        statuscheck="";

        recyclerView=findViewById(R.id.recyclerMultiple);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        list= new ArrayList<MultipleReports>();
        multipleReportsAdapter= new MultipleReportsAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(multipleReportsAdapter);
        CurrentUser= FirebaseDatabase.getInstance().getReference().child("Reports");


        multipleReportsAdapter.set(ReportView.this);


        CurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                if(dataSnapshot.exists()){
                    for(DataSnapshot Reportid:dataSnapshot.getChildren()){
                        final  String  reportIDs=Reportid.getKey().toString();
                        if(reportIDs!=null)
                        {

                            Report=FirebaseDatabase.getInstance().getReference().child("Reports").child(reportIDs);
                            MultipleReports multipleReports=new MultipleReports("","","","https://firebasestorage.googleapis.com/v0/b/focus-electron-262521.appspot.com/o/download.png?alt=media&token=9638d8fa-1365-4d6c-84c7-82a25eb4a3aa","Pending");
                            multipleReports.setReportID(reportIDs);
                            for(DataSnapshot ReportDetails:dataSnapshot.child(reportIDs).getChildren())
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
                                    statuscheck=value;

                                }

                            }
                            if(statuscheck.equalsIgnoreCase("pending"))
                            {
                                list.add(multipleReports);
                                multipleReportsAdapter.notifyDataSetChanged();
                            }

                        /*
                        Report.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        statuscheck=value;

                                    }

                                }
                                if(statuscheck.equalsIgnoreCase("pending"))
                                {
                                    list.add(multipleReports);
                                    multipleReportsAdapter.notifyDataSetChanged();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError1) {

                            }
                        });



                        */

                        /*---

                        Report.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                               String key=dataSnapshot.getKey().toString();
                                Toast.makeText(ReportView.this, key, Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        */
                        }





                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




    @Override
    public void onBackPressed() {


        list.clear();
        multipleReportsAdapter.notifyDataSetChanged();
        Intent intent= new Intent(getApplicationContext(),SelectReport.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

    @Override
    public void OnSingleItemClick(int position) {




        MultipleReports note = list.get(position);
        int p = position;
        String reportidd = note.getReportID();
        Intent intent= new Intent(getApplicationContext(),SigleReport.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("reportID",reportidd);
        startActivity(intent);

    }
}

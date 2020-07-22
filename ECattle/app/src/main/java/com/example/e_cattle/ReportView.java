package com.example.e_cattle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReportView extends AppCompatActivity implements MultipleReportsAdapter.OnItemClickListener{

    private DatabaseReference CurrentUser;
    private DatabaseReference Report;
    private DatabaseReference ReportS;
    private MultipleReportsAdapter multipleReportsAdapter;
    private RecyclerView recyclerView;
    private String statuscheck;
    ArrayList<String> reportids;
    private ArrayList<MultipleReports> list;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_view);


        recyclerView=findViewById(R.id.recyclerMultiple);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        list= new ArrayList<MultipleReports>();
        multipleReportsAdapter= new MultipleReportsAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(multipleReportsAdapter);
        CurrentUser= FirebaseDatabase.getInstance().getReference().child("User")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Reports");

        reportids=new ArrayList<String>();

        multipleReportsAdapter.set(ReportView.this);


        ReportS=FirebaseDatabase.getInstance().getReference().child("Reports");
        ReportS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                list.clear();
                CurrentUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                        list.clear();
                        for(DataSnapshot Reportid:dataSnapshots.getChildren()){

                            final  String  reportIDs=Reportid.getValue().toString();
                            if(reportIDs!=null)
                            {

                                MultipleReports multipleReports=new MultipleReports("","","","https://firebasestorage.googleapis.com/v0/b/focus-electron-262521.appspot.com/o/download.png?alt=media&token=9638d8fa-1365-4d6c-84c7-82a25eb4a3aa","Pending");
                                multipleReports.setReportID(reportIDs);
                                for(DataSnapshot fuck:dataSnapshot.child(reportIDs).getChildren())
                                {

                                    String key=fuck.getKey();
                                    String value =fuck.getValue().toString();
                                    if(key.equalsIgnoreCase("latitude")){
                                        multipleReports.setLatitude(value);
                                    }
                                    if(key.equalsIgnoreCase("longitude")){
                                        multipleReports.setLongitude(value);
                                    }
                                    if(key.equalsIgnoreCase("images")){
                                        multipleReports.setImageURL( fuck.child("image_0").getValue().toString());

                                    }

                                    if(key.equalsIgnoreCase("status")){
                                        multipleReports.setStatus(value);
                                        statuscheck=value;

                                    }
                                }



                                list.add(multipleReports);
                                multipleReportsAdapter.notifyDataSetChanged();


                            }





                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
        super.onBackPressed();

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


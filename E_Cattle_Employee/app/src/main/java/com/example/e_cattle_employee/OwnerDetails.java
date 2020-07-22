package com.example.e_cattle_employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerDetails extends AppCompatActivity {

    String tagid;
    String report;
    TextView tagName,tagPhone,tagAdhar;



   ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);
        report=getIntent().getStringExtra("reportID");
        tagid=getIntent().getStringExtra("tagID");
        tagName = findViewById(R.id.tagName);
        tagAdhar = findViewById(R.id.tagAdhar);
        tagPhone = findViewById(R.id.tagPhone);

        fetchDetails();

    }

    public void sendsms(View view) {

        SmsManager sms= SmsManager.getDefault();
        String Phone="9644669293";


        String Hin="\nYour Cattle is caught Stray and is sent to Government Cattle Pound.Collect Your Cattle from there\n For more information Contact 9644669293\n\n\nआपकी गाय सडकों पर विचरण करते हुए पाई गई है जिसे  सरकारी  मवेशी - खाने  में रखा गया है ।  कृपया  वहां से अपनी  गाय को ले जाइए।\n" +
                "अधिक जानकारी के लिए इस  नंबर पर संपर्क 9644669293 करें।";
        ArrayList<String> finalmes=sms.divideMessage(Hin);

        sms.sendMultipartTextMessage(Phone,null,finalmes,null,null);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Reports").child(report).child("Status");
        databaseReference.setValue("Completed");
        DatabaseReference database= FirebaseDatabase.getInstance().getReference().child("Employee").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("CompletedReports");
        database.push().setValue(report);
        Toast.makeText(this, "Sms sent", Toast.LENGTH_LONG).show();

        Intent intent= new Intent(getApplicationContext(),CompletedReports.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void fetchDetails() {


        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("TagID")
                .child(tagid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  Toast.makeText(getApplicationContext(), "Tag  exists", Toast.LENGTH_SHORT).show();

                if(dataSnapshot.exists())
                {
                    Toast.makeText(getApplicationContext(), "Tag  exists", Toast.LENGTH_SHORT).show();
                    for(DataSnapshot item:dataSnapshot.getChildren())
                    {
                        String key=item.getKey();
                        if(key.equalsIgnoreCase("Name")){
                            tagName.setText(item.getValue().toString());
                            tagName.setVisibility(View.VISIBLE);
                        }
                        if(key.equalsIgnoreCase("Phone")){
                            tagPhone.setText(item.getValue().toString());
                            tagPhone.setVisibility(View.VISIBLE);
                        }
                        if(key.equalsIgnoreCase("Aadhar")){
                            tagAdhar.setText(item.getValue().toString());
                            tagAdhar.setVisibility(View.VISIBLE);
                        }
                    }

                }
                else{
                    Toast.makeText(OwnerDetails.this, "Tag Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

}

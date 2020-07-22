package com.example.e_cattle;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportSuccesfullySent extends AppCompatActivity {

    String reportID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_succesfully_sent);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        reportID=bundle.getString("reportID");
        if(reportID!=null)
        {
            Toast.makeText(this, reportID, Toast.LENGTH_SHORT).show();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Reports").child(reportID).child("Status");
            ref.setValue("Pending");


            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("User")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Reports");
            ref1.push().setValue(reportID);

        }
        else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }
    public void openAction(View view) {


        Intent intent= new Intent(getApplicationContext(),Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}

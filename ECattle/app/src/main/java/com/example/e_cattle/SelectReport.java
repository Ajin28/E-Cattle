package com.example.e_cattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_report);
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
}

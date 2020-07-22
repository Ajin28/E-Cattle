package com.example.e_cattle_employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import java.io.IOException;
import java.util.ArrayList;

public class TagId extends AppCompatActivity {

    TextView tagName,tagPhone,tagAdhar;
    EditText tagId;
    String tagid;

    String report;
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private BarcodeDetector detector;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_SMS_REQUEST_CODE = 101;
    Activity activity = this;
    private TextView textView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_id);
        tagName = findViewById(R.id.tagName);
        tagAdhar = findViewById(R.id.tagAdhar);
        tagPhone = findViewById(R.id.tagPhone);


        tagId = findViewById(R.id.editTexttag);
        surfaceView = findViewById(R.id.surfaceView);

        report=getIntent().getStringExtra("reportID");
        detector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        askCameraPermission();
        askSMSPermission();
        cameraSource = new CameraSource.Builder(this,detector).setRequestedPreviewSize(640,480).setAutoFocusEnabled(true).setRequestedFps(10f).build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback()
        {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {


                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        detector.setProcessor(new BarcodeDetector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode>qrCodes = detections.getDetectedItems();
                if(qrCodes.size() != 0)
                {
                    tagId.post(new Runnable() {
                        @Override
                        public void run() {
                            tagId.setText(qrCodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MY_SMS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_LONG).show();
            }
        }


    }
    void askCameraPermission()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);

        }
    }

    void askSMSPermission()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.SEND_SMS}, MY_SMS_REQUEST_CODE);

        }
    }



    public void submitTag(View view) {
        tagid=tagId.getText().toString();
        if(tagid.isEmpty())
        {
            tagId.setError("Tag- id not entered\nPlease Type or Scan");
            tagId.requestFocus();
            return;
        }



        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("TagID")
                .child(tagid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists())
                {
                    //Toast.makeText(getApplicationContext(), "Tag  exists", Toast.LENGTH_SHORT).show();
                    /*
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
                     */
                    Toast.makeText(TagId.this, "Tag Found", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),OwnerDetails.class);
                    intent.putExtra("reportID",report);
                    intent.putExtra("tagID",tagid);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(TagId.this, "Tag Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    /*
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



    }

     */
    @Override
    public void onBackPressed() {

        Intent intent= new Intent(getApplicationContext(),OnGoingReports.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}

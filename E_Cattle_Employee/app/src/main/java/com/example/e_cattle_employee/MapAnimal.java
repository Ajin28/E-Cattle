package com.example.e_cattle_employee;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapAnimal extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView addresssss;
    String report;
    String lat,lang;
    Double LAT,LANG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_animal);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapF);
        mapFragment.getMapAsync(this);
        addresssss=findViewById(R.id.address);
        report=getIntent().getStringExtra("reportID");
        lat=getIntent().getStringExtra("LAT");
        if(lat!=null){
            LAT=Double.parseDouble(lat);
        }
        lang=getIntent().getStringExtra("LANG");
        if(lang!=null){
            LANG=Double.parseDouble(lang);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        if(LAT!=null&& LANG!=null  && report!=null)
        {
            LatLng latLng = new LatLng(LAT, LANG);
            mMap.addMarker(new MarkerOptions().position(latLng).title(report));
         //   mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));

            String address="⦿ Address: ";
            String add=getAddress(latLng);
            String fina=address+add;
            addresssss.setText(fina);


        }
    }


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
        Intent intent= new Intent(getApplicationContext(),IncompleteReports.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }


    public String getAddress(LatLng latLng)
    {

        String address="";
        Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses=geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if(addresses!=null)
            {
                Address add=addresses.get(0);
                StringBuilder stringBuilder= new StringBuilder();


                for(int  i=0; i<=add.getMaxAddressLineIndex();i++)
                {
                    stringBuilder.append("\n").append(add.getAddressLine(i)).append("\n");

                }

                address= stringBuilder.toString();
            }
            else{
                Toast.makeText(this, "Address not found", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  address;
    }
}

package com.example.e_cattle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {



    ProgressBar progressBar;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final int request_permission = 2;
    String reportID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        progressBar=findViewById(R.id.progressBar);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        reportID=bundle.getString("reportID");


    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},request_permission);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {
                    currentLocation=location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()+""
                            +currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();


                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
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

        LatLng latLng= new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions= new MarkerOptions().position(latLng).title("Current Location").
                icon(BitmapDescriptorFactory.defaultMarker());

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
        googleMap.addMarker(markerOptions);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==request_permission)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                fetchLastLocation();
            }

        }
    }

    public void sendCurrentLocation(View view) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    public void submitReport(View view) {
        if(currentLocation==null)
        {
            Toast.makeText(getApplicationContext(),"Location not Sent",Toast.LENGTH_SHORT).show();

        }
        else{


            if(reportID!=null)
            {
                Double latitude,longitude;
                latitude=currentLocation.getLatitude();
                longitude=currentLocation.getLongitude();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Reports").child(reportID);

                DatabaseReference Lat=ref.child("Latitude");
                Lat.setValue(latitude);
                DatabaseReference Long=ref.child("Longitude");
                Long.setValue(longitude);

                Toast.makeText(getApplicationContext(),"Location Submitted",Toast.LENGTH_SHORT).show();




                Toast.makeText(getApplicationContext(),"Submitting Reports",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),ReportSuccesfullySent.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle=new Bundle();
                bundle.putString("reportID",reportID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

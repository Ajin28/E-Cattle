package com.example.e_cattle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Camera extends AppCompatActivity {

    Activity activity = this;
    private  static  final  int request_image=101;
    Bitmap[] imageArray=new Bitmap[12];
    int i=0;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private  RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    String reportID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        askCameraPermission();
        recyclerView=findViewById(R.id.recycler);
        layoutManager= new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        reportID=bundle.getString("reportID");
        i=0;
    }



    void askCameraPermission()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);

        }
    }

    public void takePicture(View view) {

        Intent takeImage= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takeImage.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(takeImage,request_image);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==request_image && resultCode==RESULT_OK){

            if(i>=12)
            {
                i=0;
            }
            Bundle extras=data.getExtras();
            Bitmap imageBitmap=(Bitmap) extras.get("data");
            imageArray[i]=imageBitmap;
            i++;
            recyclerAdapter =new RecyclerAdapter(imageArray);
            recyclerView.setAdapter(recyclerAdapter);

        }

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

    }
    public void deletePicture(View view) {

        if(imageArray[0]!=null)
        {

            imageArray[i-1]=null;
            recyclerAdapter =new RecyclerAdapter(imageArray);
            recyclerView.setAdapter(recyclerAdapter);
            i=i-1;

        }
        else{
            Toast.makeText(getApplicationContext(),"No Pictures Taken",Toast.LENGTH_SHORT).show();
        }
    }

    public void openMaps(View view) {

        if(reportID!=null)
        {
            Toast.makeText(getApplicationContext(),reportID,Toast.LENGTH_SHORT).show();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Reports")
                    .child(reportID);
            if(i>0){
                for(int j=0;j<=i-1;j++)
                {

                    uploadImage(imageArray[j],ref,j);
                }
            }

            Intent intent= new Intent(getApplicationContext(),MapsActivity.class);
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



    public void uploadImage(Bitmap bitmap, final DatabaseReference reference,final int j) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String image_no=Integer.toString(j);
        String imageID="image_"+image_no;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://focus-electron-262521.appspot.com");
        StorageReference imagesRef = storageRef.child(reportID).child(imageID);

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url=uri.toString();
                        String image_no=Integer.toString(j);
                        String imageID="image_"+image_no;
                        DatabaseReference image=reference.child("Images").child(imageID);
                        image.setValue(url);
                    }
                });
                // Do what you want
            }
        });
    }


}

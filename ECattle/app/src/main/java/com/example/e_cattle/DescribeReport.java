package com.example.e_cattle;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class DescribeReport extends AppCompatActivity  {

    String reportid;
    String Animal,description,condition;
    EditText DescriptionBox;
    Spinner spinner,conditionspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_report);
        DescriptionBox= findViewById(R.id.editText2);

        spinner=findViewById(R.id.spinner);
        conditionspinner=findViewById(R.id.spinner1);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.CattleType,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.ConditionType,android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(adapter);
        conditionspinner.setAdapter(adapter1);


         Animal= spinner.getSelectedItem().toString();
         condition=conditionspinner.getSelectedItem().toString();

    }



    public void skiptoCam(View view) {
        UUID uuid=UUID.randomUUID();
        reportid=uuid.toString();
        Intent intent= new Intent(getApplicationContext(),Camera.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle=new Bundle();
        bundle.putString("reportID",reportid);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openCam(View view) {

        Animal= spinner.getSelectedItem().toString();
        condition=conditionspinner.getSelectedItem().toString();

        if(Animal.equalsIgnoreCase("none"))
        {
            Toast.makeText(getApplicationContext(),"No animal selected" , Toast.LENGTH_SHORT).show();
            spinner.requestFocus();
            return;
        }
        if(condition.equalsIgnoreCase("none"))
        {
            Toast.makeText(getApplicationContext(),"No condition selected" , Toast.LENGTH_SHORT).show();
            conditionspinner.requestFocus();
            return;
        }
        description="("+condition+") "+DescriptionBox.getText().toString();
        UUID uuid=UUID.randomUUID();
        reportid=uuid.toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Reports").child(reportid);


        DatabaseReference AnimalType=ref.child("Animal");
        AnimalType.setValue(Animal);
        if(description.length()>0)
        {
            DatabaseReference Des=ref.child("Description");
            Des.setValue(description);
        }
        Intent intent= new Intent(getApplicationContext(),Camera.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle=new Bundle();
        bundle.putString("reportID",reportid);
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
        finish();
    }

}

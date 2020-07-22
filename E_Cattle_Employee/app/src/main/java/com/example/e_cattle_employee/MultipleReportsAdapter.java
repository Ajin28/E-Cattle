package com.example.e_cattle_employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MultipleReportsAdapter extends RecyclerView.Adapter<MultipleReportsAdapter.MyViewHolder> {


    Context context;
    ArrayList<MultipleReports> multipleReports;

    //****************************************

    //First Create a interface for itemc click
    public interface OnItemClickListener
    {
        void OnSingleItemClick(int position);
    }

    //create a OnItemClickListener
    private static OnItemClickListener listener;

    //create this method to register listener
    public void set(OnItemClickListener itemClickListener)
    {
        listener = itemClickListener;
    }
    //********
    public MultipleReportsAdapter(Context c, ArrayList<MultipleReports> m)
    {
        context=c;
        multipleReports=m;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.multiple_reports,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.reportID.setText(multipleReports.get(position).getReportID());
        holder.Latitude.setText("Latitude:"+multipleReports.get(position).getLatitude());
        holder.Longitude.setText("Longitude:"+multipleReports.get(position).getLongitude());
        holder.Status.setText("Status:"+multipleReports.get(position).getStatus());
        Picasso.get().load(multipleReports.get(position).getImageURL()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return multipleReports.size();
    }

   public    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView reportID,Latitude,Longitude,Status;
        ImageView imageView;
        Button button;
        public MyViewHolder(View itemView){
            super(itemView);
            reportID=itemView.findViewById(R.id.reportID);
            Latitude=itemView.findViewById(R.id.latitude);
            Longitude=itemView.findViewById(R.id.longitude);
            imageView=itemView.findViewById(R.id.reportImage);
            Status=itemView.findViewById(R.id.status);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int postion = getAdapterPosition();
                    listener.OnSingleItemClick(postion);
                }
            });


        }




    }
}

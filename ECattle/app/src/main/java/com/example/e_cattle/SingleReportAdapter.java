package com.example.e_cattle;



import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleReportAdapter extends RecyclerView.Adapter<SingleReportAdapter.MyViewHolder> {

    private ArrayList<String> Images;

    public SingleReportAdapter(ArrayList<String> images) {
        Images = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ImageView singleimage= (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.single,parent,false);

        MyViewHolder myViewHolder= new MyViewHolder(singleimage);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Picasso.get().load(Images.get(position)).into(holder.images);

    }

    @Override
    public int getItemCount() {
        return  Images.size();
    }

    public static   class MyViewHolder extends RecyclerView.ViewHolder{


        ImageView images;
        public MyViewHolder(ImageView itemView) {
            super(itemView);
            images=itemView;

    }




}

}
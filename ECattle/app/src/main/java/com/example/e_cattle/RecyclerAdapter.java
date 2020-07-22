package com.example.e_cattle;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Bitmap[] list;
    public RecyclerAdapter(Bitmap[] list1)
    {
        list=list1;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ImageView img= (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view_layout,parent,false);
        MyViewHolder myViewHolder= new MyViewHolder(img);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.images.setImageBitmap(list[position]);

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView images;
        public MyViewHolder(ImageView itemView) {
            super(itemView);
            images=itemView;
        }
    }
}

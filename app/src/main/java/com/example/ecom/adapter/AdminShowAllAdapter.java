package com.example.ecom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecom.R;
import com.example.ecom.activities.DetailedActivity2;
import com.example.ecom.models.AdminShowAllModel;
import com.example.ecom.models.ShowAllModel;

import java.util.List;

public class AdminShowAllAdapter extends RecyclerView.Adapter<AdminShowAllAdapter.MyViewHolder> {


    private Context context;
    private List<AdminShowAllModel> list;

    public AdminShowAllAdapter(Context context, List<AdminShowAllModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public AdminShowAllAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdminShowAllAdapter.MyViewHolder holder, int position) {
        AdminShowAllModel adminShowAllModel = list.get(position);

        holder.AName.setText(adminShowAllModel.getName());
        holder.ACost.setText((String.valueOf(adminShowAllModel.getPrice())));
        holder.AType.setText(adminShowAllModel.getType());
        holder.AQty.setText(String.valueOf(adminShowAllModel.getQty()));


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView AQty;

        private TextView AType;

        private TextView ACost;
        private TextView AName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            AQty = itemView.findViewById(R.id.itemQty);
            AType = itemView.findViewById(R.id.itemType);
            ACost = itemView.findViewById(R.id.itemPrice);
            AName = itemView.findViewById(R.id.itemName);
        }
    }
}

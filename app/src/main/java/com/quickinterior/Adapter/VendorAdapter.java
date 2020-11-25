package com.quickinterior.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickinterior.Activity.StockInfo;
import com.quickinterior.Module.VendorResponse;
import com.quickinterior.R;

import java.util.List;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.MyViewHolder> {

    Context context;
    List<VendorResponse> projectList;

    public VendorAdapter(Context context, List<VendorResponse> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vendor_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.name.setText(projectList.get(i).getUser_fullname());
        myViewHolder.address.setText(projectList.get(i).getPo_site_address());

        myViewHolder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,StockInfo.class);
                intent.putExtra("poId",projectList.get(i).getPo_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,address;
        ImageView info;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.vendorName);
            address=itemView.findViewById(R.id.location);
            info=itemView.findViewById(R.id.info);
        }
    }
}

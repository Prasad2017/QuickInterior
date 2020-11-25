package com.quickinterior.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quickinterior.Activity.ConsumedStock;
import com.quickinterior.Module.AvailableStockResponse;
import com.quickinterior.R;

import java.util.List;

public class AvailableStockAdapter extends RecyclerView.Adapter<AvailableStockAdapter.MyViewHolder> {

    Context context;
    List<AvailableStockResponse> projectList;

    public AvailableStockAdapter(Context context, List<AvailableStockResponse> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_available_stock_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        String p = String.valueOf(i+1);

        myViewHolder.availsrNo.setText(p);
        myViewHolder.availProdName.setText(projectList.get(i).getBrand_name()+"-"+projectList.get(i).getMaterial_detail_product_name());
        myViewHolder.availQty.setText(projectList.get(i).getBalace_qty());

        myViewHolder.availStockEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ConsumedStock.class);
                intent.putExtra("prodid", projectList.get(i).getPo_product_id());
                intent.putExtra("poid", projectList.get(i).getPo_id_fk());
                intent.putExtra("prodName", projectList.get(i).getMaterial_detail_product_name());
                intent.putExtra("prodqty", projectList.get(i).getBalace_qty());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView availProdName, availsrNo, availQty;
        ImageView availStockEdit;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            availProdName = itemView.findViewById(R.id.availProdName);
            availsrNo = itemView.findViewById(R.id.availsrNo);
            availQty = itemView.findViewById(R.id.availQty);
            availStockEdit = itemView.findViewById(R.id.availStockEdit);
        }
    }
}

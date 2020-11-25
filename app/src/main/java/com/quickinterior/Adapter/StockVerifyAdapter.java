package com.quickinterior.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.quickinterior.Activity.EditStock;
import com.quickinterior.Module.StockVerification;
import com.quickinterior.R;

import java.util.List;


public class StockVerifyAdapter extends RecyclerView.Adapter<StockVerifyAdapter.MyViewHolder>{

    public static List<StockVerification> data;
    public Context context;
    String type;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Ameneties;
        TextView LaboursrNo;
        TextView OrderProdName;
        Context context;
        ImageView pencil;
        TextView pending;
        TextView received;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            Ameneties = (TextView) view.findViewById(R.id.Labour_name);
            LaboursrNo = (TextView) view.findViewById(R.id.LaboursrNo);
            OrderProdName = (TextView) view.findViewById(R.id.OrderProdName);
            pending = (TextView) view.findViewById(R.id.pending);
            received = (TextView) view.findViewById(R.id.received);
            pencil = (ImageView) view.findViewById(R.id.pencil);
        }
    }

    public StockVerifyAdapter(Context context2, List<StockVerification> list) {
        data = list;
        this.context = context2;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_add_labour, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        myViewHolder.Ameneties.setText(((StockVerification) data.get(i)).getBrand_name()+"-"+(data.get(i)).getMaterial_detail_product_name());
        myViewHolder.OrderProdName.setText(((StockVerification) data.get(i)).getPo_product_quantity());
        myViewHolder.pending.setText(((StockVerification) data.get(i)).getRecieved_qty());
        myViewHolder.received.setText(((StockVerification) data.get(i)).getPending_qty());
        myViewHolder.LaboursrNo.setText(String.valueOf(i + 1));

        myViewHolder.pencil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {



                Intent intent = new Intent(StockVerifyAdapter.this.context, EditStock.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("po_product", ((StockVerification) StockVerifyAdapter.data.get(i)).getPo_product_id());
                intent.putExtra("po_rcvqty", ((StockVerification) StockVerifyAdapter.data.get(i)).getRecieved_qty());
                intent.putExtra("poid", ((StockVerification) StockVerifyAdapter.data.get(i)).getPo_id_fk());
                intent.putExtra("po_avlqty", ((StockVerification) StockVerifyAdapter.data.get(i)).getPending_qty());
                intent.putExtra("productname", ((StockVerification) StockVerifyAdapter.data.get(i)).getMaterial_detail_product_name());
                context.startActivity(intent);


               /* if (((StockVerification) StockVerifyAdapter.data.get(i)).getPo_product_status().equalsIgnoreCase("Complete")) {
                    Toast.makeText(context, "Product Already Received", Toast.LENGTH_SHORT).show();
                } else if (((StockVerification) StockVerifyAdapter.data.get(i)).getPo_product_status().equalsIgnoreCase("active")) {

                }*/
            }
        });

    }

    public int getItemCount() {
        return data.size();
    }
}

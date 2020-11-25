package com.quickinterior.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickinterior.Extra.Blur;
import com.quickinterior.Module.CompleteWorkResponse;
import com.quickinterior.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class CompleteWorkAdapter extends RecyclerView.Adapter<CompleteWorkAdapter.MyViewHolder> {

    Context context;
    List<CompleteWorkResponse> projectList;

    public CompleteWorkAdapter(Context context, List<CompleteWorkResponse> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complete_work_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.percentage.setText("Work Percentage: "+projectList.get(i).getWork_activity_percent());
        myViewHolder.quatation.setText("Category: "+projectList.get(i).getService_name());


        try {

            Transformation blurTransformation = new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    Bitmap blurred = Blur.fastblur(context, source, 10);
                    source.recycle();
                    return blurred;
                }

                @Override
                public String key() {
                    return "blur()";
                }
            };
            Picasso.with(context)
                    .load("http://prabhagmaza.com/androidApp/QuickInterior/Executive/"+projectList.get(i).getWork_image())
                    .placeholder(R.drawable.defaultman)
                    .transform(blurTransformation)
                    .skipMemoryCache()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(myViewHolder.imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                            Picasso.with(context)
                                    .load(projectList.get(i).getWork_image())
                                    .placeholder(myViewHolder.imageView.getDrawable())
                                    .into(myViewHolder.imageView);

                        }

                        @Override
                        public void onError() {
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView percentage, quatation;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            percentage=itemView.findViewById(R.id.percentage);
            quatation=itemView.findViewById(R.id.quatation);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}

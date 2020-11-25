package com.quickinterior.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quickinterior.Activity.MainPage;
import com.quickinterior.Extra.Common;
import com.quickinterior.Module.ProjectResponse;
import com.quickinterior.R;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {

    Context context;
    List<ProjectResponse> projectList;

    public ProjectAdapter(Context context, List<ProjectResponse> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.projectname.setText(projectList.get(i).getProject_name());

        myViewHolder.projectNumber.setText(""+ (i+1)+".");

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, MainPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("projectId",projectList.get(i).getProject_id());
                Common.saveUserData(context, "projectId", projectList.get(i).getProject_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView projectname, projectNumber;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            projectname = itemView.findViewById(R.id.projectname);
            projectNumber = itemView.findViewById(R.id.projectNumber);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}

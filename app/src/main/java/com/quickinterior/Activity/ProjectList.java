package com.quickinterior.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.quickinterior.Adapter.ProjectAdapter;
import com.quickinterior.Extra.DividerItemDecorator;
import com.quickinterior.Module.AllList;
import com.quickinterior.Module.ProjectResponse;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectList extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<ProjectResponse> projectResponseList = new ArrayList<>();
    ProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        ButterKnife.bind(this);
    }

    private void getProjects() {

        ProgressDialog progressDialog = new ProgressDialog(ProjectList.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        recyclerView.clearOnScrollListeners();
        projectResponseList.clear();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getProjects();
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(@NonNull Call<AllList> call, @NonNull Response<AllList> response) {

                AllList allList = response.body();
                projectResponseList = allList.getProjectResponseList();

                if (projectResponseList.size()==0){
                    progressDialog.dismiss();
                    recyclerView.setVisibility(View.GONE);

                }else {

                    for (int i = 0; i< projectResponseList.size(); i++){

                        adapter = new ProjectAdapter(getApplicationContext(), projectResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);
                        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(ProjectList.this, R.drawable.divider_size));
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(projectResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setVisibility(View.VISIBLE);

                        progressDialog.dismiss();

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<AllList> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                recyclerView.setVisibility(View.GONE);
                Toasty.error(Objects.requireNonNull(getApplicationContext()), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getProjects();
    }
}

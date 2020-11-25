package com.quickinterior.Activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.quickinterior.Adapter.CompleteWorkAdapter;
import com.quickinterior.Module.AllList;
import com.quickinterior.Module.CompleteWorkResponse;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectWorksheetActivity extends AppCompatActivity {

    String date;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<CompleteWorkResponse> completeWorkResponses = new ArrayList<>();
    CompleteWorkAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_project_worksheet);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        date=intent.getStringExtra("date");

        getCompleteWork(date);
    }

    @OnClick({ R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back:
                Intent intent = new Intent(ProjectWorksheetActivity.this, MainPage.class);
                startActivity(intent);
                break;
        }
    }
    private void getCompleteWork(String date) {
        recyclerView.clearOnScrollListeners();
        completeWorkResponses.clear();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getCompleteWork(date, MainPage.projectId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(@NonNull Call<AllList> call, @NonNull Response<AllList> response) {

                AllList allList = response.body();
                completeWorkResponses = allList.getCompleteWorkResponses();

                if (completeWorkResponses.size()==0){
                    recyclerView.setVisibility(View.GONE);

                }else {

                    for (int i = 0; i< completeWorkResponses.size(); i++){

                        adapter = new CompleteWorkAdapter(getApplicationContext(), completeWorkResponses);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(completeWorkResponses.size() - 1);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setVisibility(View.VISIBLE);

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<AllList> call, @NonNull Throwable t) {
                recyclerView.setVisibility(View.GONE);
                Toasty.error(Objects.requireNonNull(getApplicationContext()), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });
    }

}

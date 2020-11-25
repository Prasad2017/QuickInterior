package com.quickinterior.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quickinterior.Activity.MainPage;
import com.quickinterior.Adapter.ProjectAdapter;
import com.quickinterior.Extra.DetectConnection;
import com.quickinterior.Module.AllList;
import com.quickinterior.Module.LoginResponce;
import com.quickinterior.Module.ProjectResponse;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Projects extends Fragment {

    View view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<ProjectResponse> projectResponseList = new ArrayList<>();
    ProjectAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_projects, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainPage.toolbar_container.setVisibility(View.VISIBLE);
        ((MainPage) Objects.requireNonNull(getActivity())).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())){
            getProjects();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }


    private void getProjects() {
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
                    recyclerView.setVisibility(View.GONE);

                }else {

                    for (int i = 0; i< projectResponseList.size(); i++){

                        adapter = new ProjectAdapter(getActivity(), projectResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(projectResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setVisibility(View.VISIBLE);

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<AllList> call, @NonNull Throwable t) {
                recyclerView.setVisibility(View.GONE);
                Toasty.error(Objects.requireNonNull(getActivity()), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });
    }
}

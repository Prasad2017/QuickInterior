package com.quickinterior.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quickinterior.Activity.MainPage;
import com.quickinterior.Adapter.VendorAdapter;
import com.quickinterior.Extra.DetectConnection;
import com.quickinterior.Module.AllList;
import com.quickinterior.Module.VendorResponse;
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

public class WorkOrder extends Fragment {

    View view;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<VendorResponse> vendorResponseList = new ArrayList<>();
    VendorAdapter adapter;

    String project_Id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_work_order, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    private void getSupplierList() {

        recyclerView.clearOnScrollListeners();
        vendorResponseList.clear();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getVendorList(MainPage.projectId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(@NonNull Call<AllList> call, @NonNull Response<AllList> response) {

                AllList allList = response.body();
                vendorResponseList = allList.getVendorResponseList();

                if (vendorResponseList.size()==0){
                    recyclerView.setVisibility(View.GONE);
                    Toasty.normal(Objects.requireNonNull(getActivity()), "No data found", Toasty.LENGTH_SHORT).show();

                }else {

                    for (int i = 0; i< vendorResponseList.size(); i++){

                        adapter = new VendorAdapter(getActivity(), vendorResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(vendorResponseList.size() - 1);
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


    @Override
    public void onStart() {
        super.onStart();
        MainPage.toolbar_container.setVisibility(View.VISIBLE);
        ((MainPage) Objects.requireNonNull(getActivity())).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())){
            getSupplierList();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }

}

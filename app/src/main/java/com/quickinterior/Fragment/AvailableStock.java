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
import com.quickinterior.Adapter.AvailableStockAdapter;
import com.quickinterior.Extra.DetectConnection;
import com.quickinterior.Module.AllList;
import com.quickinterior.Module.AvailableStockResponse;
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


public class AvailableStock extends Fragment {

    View view;
    @BindView(R.id.availableStockList)
    RecyclerView recyclerView;
    List<AvailableStockResponse> availableStockList = new ArrayList<>();
    AvailableStockAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_available_stock, container, false);
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
            getStockList();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }

    public void getStockList() {

       /* final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), 5);
        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.setTitleText("Loading...");
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.show();*/

        recyclerView.clearOnScrollListeners();
        availableStockList.clear();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getAvailableStock(MainPage.projectId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(@NonNull Call<AllList> call, @NonNull Response<AllList> response) {

                AllList allList = response.body();
                availableStockList = allList.getAvailableStockResponses();

                if (availableStockList.size()==0){


                    recyclerView.setVisibility(View.GONE);
                    Toasty.normal(Objects.requireNonNull(getActivity()), "No data found", Toasty.LENGTH_SHORT).show();

                }else {

                    for (int i = 0; i< availableStockList.size(); i++){

                        adapter = new AvailableStockAdapter(getActivity(), availableStockList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(availableStockList.size() - 1);
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

package com.quickinterior.Activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.quickinterior.Adapter.AvailableStockAdapter;
import com.quickinterior.Adapter.StockVerifyAdapter;
import com.quickinterior.Module.AllList;
import com.quickinterior.Module.StockVerification;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StockInfo extends AppCompatActivity {


    public static String getStockUrl = "http://prabhagmaza.com/androidApp/QuickInterior/Executive/getStock.php";
    @BindView(R.id.StockProductList)
    RecyclerView recyclerView;
    List<StockVerification> aProductPoList = new ArrayList();
    StockVerifyAdapter adapter;
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    String date;
    Button oderSubmit;
    TextView orderName1;
    TextView orderdate1;
    String po_id;
    String project_name;
    String username;
    @BindView(R.id.StockVL2)
    LinearLayout StockVL2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_slock_info);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent=getIntent();
        po_id=intent.getStringExtra("poId");

    }

    @OnClick({ R.id.back})
    public void onClick(View view){
        switch (view.getId()){

            case R.id.back:
                Intent intent=new Intent(StockInfo.this, Stock.class);
                startActivity(intent);
                break;
        }
    }


    private void getStockVerification() {
        final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, 5);
        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.setTitleText("Loading...");
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.show();

        recyclerView.clearOnScrollListeners();
        aProductPoList.clear();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getStock(po_id);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(@NonNull Call<AllList> call, @NonNull Response<AllList> response) {

                AllList allList = response.body();
                aProductPoList = allList.getStockResopnse();

                if (aProductPoList.size()==0){
                    sweetAlertDialog.dismiss();
                    recyclerView.setVisibility(View.GONE);
                    StockVL2.setVisibility(View.GONE);
                    Toasty.normal(Objects.requireNonNull(getApplication()), "No data found", Toasty.LENGTH_SHORT).show();

                }else {
                    sweetAlertDialog.dismiss();

                    for (int i = 0; i < aProductPoList.size(); i++) {
                        adapter = new StockVerifyAdapter(getApplication(), aProductPoList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(aProductPoList.size() - 1);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setVisibility(View.VISIBLE);
                        StockVL2.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllList> call, @NonNull Throwable t) {
                sweetAlertDialog.dismiss();

                recyclerView.setVisibility(View.GONE);
                Toasty.error(Objects.requireNonNull(getApplication()), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });
    }
    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        getStockVerification();
    }
}
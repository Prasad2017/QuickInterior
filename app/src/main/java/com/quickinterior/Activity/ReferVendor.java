package com.quickinterior.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.quickinterior.Module.AllList;
import com.quickinterior.Module.LoginResponce;
import com.quickinterior.Module.QuatationResponse;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferVendor extends AppCompatActivity {

    @BindViews({R.id.userName, R.id.userNumber, R.id.userAddress, R.id.userEmail})
    List<FormEditText> editTexts;
    @BindView(R.id.quatationList)
    SearchableSpinner searchableSpinner;
    List<QuatationResponse> quatationResponseList = new ArrayList<>();
    String quatationName, quatationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_vendor);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        getQuotationList();

        searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                quatationName= String.valueOf(searchableSpinner.getSelectedItem());
                quatationId= quatationResponseList.get(i).getSer_id();
                Log.e("",""+quatationId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @OnClick({R.id.SaveButton, R.id.back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.SaveButton:
                if(validate(editTexts.get(0)) && validate(editTexts.get(1)) && validate(editTexts.get(3)) && validate(editTexts.get(3))){
                    referVendor();
                }else {

                }
                break;

            case R.id.back:
                Intent intent=new Intent(ReferVendor.this, MainPage.class);
                startActivity(intent);
                break;
        }
    }



    private void referVendor() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponce> call = apiInterface.referVendor(quatationName, editTexts.get(0).getText().toString().trim(), editTexts.get(1).getText().toString().trim(),
                editTexts.get(2).getText().toString().trim(), editTexts.get(3).getText().toString().trim(), MainPage.userId);

        call.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {

                    Toasty.success(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(ReferVendor.this, MainPage.class);
                    startActivity(intent);


                } else if (data.equalsIgnoreCase("0")) {
                    Toasty.error(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponce> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    private void getQuotationList() {

        quatationResponseList.clear();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getQuatationServices();
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(@NonNull Call<AllList> call, @NonNull Response<AllList> response) {

                AllList allList = response.body();
                quatationResponseList = allList.getQuatationResponseList();

                if (quatationResponseList.size()==0){

                }else {

                    for (int i = 0; i< quatationResponseList.size(); i++){

//                        quatationId = quatationResponseList.get(i).getSer_id();

                        ArrayAdapter<QuatationResponse> adp = new ArrayAdapter<QuatationResponse>
                                (getApplicationContext(),android.R.layout.simple_dropdown_item_1line, quatationResponseList);
                        searchableSpinner.setAdapter(adp);

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<AllList> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getApplicationContext()), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });
    }
}

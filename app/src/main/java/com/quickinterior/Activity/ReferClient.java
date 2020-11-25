package com.quickinterior.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.andreabaccega.widget.FormEditText;
import com.quickinterior.Module.LoginResponce;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;

import java.util.List;
import java.util.Objects;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferClient extends AppCompatActivity {

    @BindViews({R.id.userName, R.id.userNumber, R.id.userAddress, R.id.userEmail})
    List<FormEditText> editTexts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_client);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);



    }
    @OnClick({R.id.SaveButton, R.id.back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.SaveButton:
                if(validate(editTexts.get(0)) && validate(editTexts.get(1)) && validate(editTexts.get(3)) && validate(editTexts.get(3))){
                    referClient();
                }else {

                }
                break;

            case R.id.back:
                Intent intent=new Intent(ReferClient.this, MainPage.class);
                startActivity(intent);
                break;
        }
    }



    private void referClient() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponce> call = apiInterface.referClient(editTexts.get(0).getText().toString().trim(), editTexts.get(1).getText().toString().trim(),
                editTexts.get(2).getText().toString().trim(), editTexts.get(3).getText().toString().trim(), MainPage.userId);

        call.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {

                    Toasty.success(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(ReferClient.this, MainPage.class);
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

}

package com.quickinterior.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.andreabaccega.widget.FormEditText;
import com.google.android.gms.common.api.Api;
import com.quickinterior.Extra.Common;
import com.quickinterior.Module.LoginResponce;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;

import java.io.File;
import java.util.List;
import java.util.Objects;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {


    @BindViews({R.id.emailSignIn1, R.id.PasswordSignIn})
    List<FormEditText> formEditTexts;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @BindViews({R.id.iv_passShow})
    List<ImageView> imageViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        File file = new File("data/data/com.quickinterior/shared_prefs/user.xml");
        if (file.exists())
        {
            Intent intent = new Intent(Login.this, ProjectList.class);
            startActivity(intent);
            finish();
        }


    }

    @OnClick({R.id.login_button_card_view, R.id.iv_passShow})
    public void onClick(View view){
        switch (view.getId()){

            case R.id.login_button_card_view:

                if(formEditTexts.get(0).testValidity() && formEditTexts.get(1).testValidity()){
                    login();
                }

                break;

            case R.id.iv_passShow:

                if (formEditTexts.get(1).getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    imageViews.get(0).setImageResource(R.drawable.ic_hide);
                    formEditTexts.get(1).setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    imageViews.get(0).setImageResource(R.drawable.ic_look);
                    formEditTexts.get(1).setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                formEditTexts.get(1).setSelection(formEditTexts.get(1).getText().toString().length());

                break;
        }
    }


    private void login() {

        ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Account is verifying");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponce> call = apiInterface.login(formEditTexts.get(1).getText().toString(), formEditTexts.get(0).getText().toString());

        call.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {

                    progressDialog.dismiss();

                    pref = getSharedPreferences("user", Context.MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putString("UserLogin","UserLoginSuccessful");
                    editor.apply();

                    MainPage.userId=response.body().getUserId();
                    Common.saveUserData(getApplicationContext(),"userId", response.body().getUserId());
                    Toasty.success(Objects.requireNonNull(getApplicationContext()), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();

                    Intent intent=new Intent(getApplicationContext(), ProjectList.class);
                    startActivity(intent);
                    finishAffinity();

                } else if (data.equalsIgnoreCase("0")) {
                    progressDialog.dismiss();
                    Toasty.error(Objects.requireNonNull(getApplicationContext()), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponce> call, Throwable t) {
                progressDialog.dismiss();
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

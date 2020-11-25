package com.quickinterior.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aminography.choosephotohelper.ChoosePhotoHelper;
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quickinterior.Module.AllList;
import com.quickinterior.Module.LoginResponce;
import com.quickinterior.Module.MasterSubCatResopnse;
import com.quickinterior.Module.QuatationResponse;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
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

public class WorkCompleteActivity extends AppCompatActivity {

    @BindView(R.id.uploadData)
    ImageView imageView;
    @BindView(R.id.percentage)
    EditText editText;
    @BindView(R.id.quatationList)
    SearchableSpinner searchableSpinner;
    @BindView(R.id.subquatationList)
    SearchableSpinner subquatationSpinner;

    List<QuatationResponse> quatationResponseList = new ArrayList<>();
    String quatationName, quatationId;

    List<MasterSubCatResopnse> subquatationList = new ArrayList<>();
    String subId, subName, percentage;

    private ChoosePhotoHelper choosePhotoHelper;
    Bitmap bitmap;
    public String userProfileImage = "";
    String ImageString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_complete);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getQuotationList();
//        getSubCatList();
        choosePhotoHelper = ChoosePhotoHelper.with(this)
                .asFilePath()
                .build(new ChoosePhotoCallback<String>() {
                    @Override
                    public void onChoose(String photo) {
                        Glide.with(imageView)
                                .load(photo)
                                .apply(RequestOptions.placeholderOf(R.drawable.defaultman))
                                .into(imageView);
                    }
                });


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
                getSubCatList(quatationId);

                Log.e("",""+quatationId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subquatationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                subName= String.valueOf(subquatationSpinner.getSelectedItem());
                subId= subquatationList.get(i).getSub_cat_id();
                percentage= subquatationList.get(i).getCat_percentage();
                editText.setText(""+percentage+" %");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String s1=editable.toString().trim();

                try {
                    if(editText.getText().toString().length() > 0){

                        if(Integer.parseInt(editText.getText().toString())> 0 && Integer.parseInt(editText.getText().toString()) < 100){

                        }else {
                            editText.requestFocus();
                            editText.setError("Please Enter valid %");
                        }
                    }else {
                        editText.requestFocus();
                        editText.setError("Enter Percentage");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
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

    private void getSubCatList(String quatationId) {

        subquatationList.clear();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getSubCatList(quatationId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(@NonNull Call<AllList> call, @NonNull Response<AllList> response) {

                AllList allList = response.body();
                subquatationList = allList.getMasterSubCatResopnses();

                if (subquatationList.size()==0){

                }else {

                    for (int i = 0; i< subquatationList.size(); i++){

//                        quatationId = quatationResponseList.get(i).getSer_id();

                        ArrayAdapter<MasterSubCatResopnse> adp = new ArrayAdapter<MasterSubCatResopnse>
                                (getApplicationContext(),android.R.layout.simple_dropdown_item_1line, subquatationList);
                        subquatationSpinner.setAdapter(adp);

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllList> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getApplicationContext()), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.submitWork, R.id.uploadData, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitWork:
                if(imageView.getDrawable()!=null){
                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    userProfileImage = getStringImage(bitmap);
                    UploadWork();
                }else {
                    Toasty.error(getApplicationContext(), "Please Select Image", Toasty.LENGTH_SHORT).show();
                }

                break;

            case R.id.uploadData:
                ImageString="img";
                choosePhotoHelper.showChooser();

                break;

            case R.id.back:
                Intent intent=new Intent(WorkCompleteActivity.this, MainPage.class);
                startActivity(intent);
                break;
        }
    }

    private void UploadWork() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponce> call = apiInterface.uploadWork(MainPage.projectId, quatationId, subId, editText.getText().toString().trim(), userProfileImage);

        call.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {

                    Toasty.success(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(WorkCompleteActivity.this, MainPage.class);
                    startActivity(intent);

                  /*  new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Updated successful")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
//                                    Toasty.success(getActivity(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                                    ((MainPage)getApplicationContext()).removeCurrentFragmentAndMoveBack();

                                }
                            })
                            .show();*/

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

    @Override
    public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(ImageString.equalsIgnoreCase("img")){
            choosePhotoHelper.onActivityResult(requestCode, resultCode, data);

        }else{

        }
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ImageString.equalsIgnoreCase("img")){
            choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }else {

        }
    }

    public String getStringImage (Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        return encodedImage;
    }
}


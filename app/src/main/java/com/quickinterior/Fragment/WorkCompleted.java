package com.quickinterior.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aminography.choosephotohelper.ChoosePhotoHelper;
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quickinterior.Activity.MainPage;
import com.quickinterior.Extra.DetectConnection;
import com.quickinterior.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class WorkCompleted extends Fragment {

    View view;
    @BindView(R.id.uploadData)
    ImageView imageView;
    @BindView(R.id.percentage)
    EditText editText;
    private ChoosePhotoHelper choosePhotoHelper;
    Bitmap bitmap;
    public String userProfileImage = "";
    String ImageString;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_work_completed, container, false);
        ButterKnife.bind(this, view);

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

        return view;
    }

    @OnClick({R.id.submitWork, R.id.uploadData})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitWork:
                if(imageView.getDrawable()!=null){
                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    userProfileImage = getStringImage(bitmap);
//                    UploadWork();
                }else {
                    Toasty.error(getActivity(), "Please Select Image", Toasty.LENGTH_SHORT).show();
                }

                break;

            case R.id.uploadData:
                ImageString="img";
                choosePhotoHelper.showChooser();

                break;
        }
    }

 /*   private void UploadWork() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponce> call = apiInterface.UploadVehicle(MainPage.userId, userProfileImage, "","");

        call.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Updated successful")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
//                                    Toasty.success(getActivity(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                                    ((MainPage)getActivity()).removeCurrentFragmentAndMoveBack();

                                }
                            })
                            .show();

                } else if (data.equalsIgnoreCase("0")) {
                    Toasty.error(getActivity(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponce> call, Throwable t) {
                Toasty.error(getActivity(), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });
    }
*/
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

    @Override
    public void onStart() {
        super.onStart();
        MainPage.toolbar_container.setVisibility(View.VISIBLE);
        ((MainPage) Objects.requireNonNull(getActivity())).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())){

        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }

}

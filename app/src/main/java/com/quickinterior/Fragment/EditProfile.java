package com.quickinterior.Fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.aminography.choosephotohelper.ChoosePhotoHelper;
import com.andreabaccega.widget.FormEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.quickinterior.Activity.MainPage;
import com.quickinterior.Extra.DetectConnection;
import com.quickinterior.Module.LoginResponce;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfile extends Fragment {

    View view;
    @BindViews({R.id.userName, R.id.userNumber, R.id.userEmail, R.id.userAddress})
    List<FormEditText> formEditTexts;
    public String user_fullname, user_mobileno, user_emailid, user_address;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.profile_add)
    ImageView profile_add;
    private ChoosePhotoHelper choosePhotoHelper;
    Bitmap bitmap;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);


        Bundle intent = getArguments();

        user_fullname = intent.getString("userName");
        user_mobileno = intent.getString("userMobile");
        user_emailid = intent.getString("userEmail");
        user_address = intent.getString("userAddress");

        formEditTexts.get(0).setText(user_fullname);
        formEditTexts.get(1).setText(user_mobileno);
        formEditTexts.get(2).setText(user_emailid);
        formEditTexts.get(3).setText(user_address);

        formEditTexts.get(0).setSelection(formEditTexts.get(0).getText().toString().length());
        formEditTexts.get(1).setSelection(formEditTexts.get(1).getText().toString().length());
        formEditTexts.get(2).setSelection(formEditTexts.get(2).getText().toString().length());
        formEditTexts.get(3).setSelection(formEditTexts.get(3).getText().toString().length());


        return view;

    }

    @OnClick({R.id.profile_add, R.id.SaveButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveButton:
                UpdateProfileData();
                break;

            case R.id.profile_add:
                choosePhotoHelper.showChooser();
                break;
        }
    }

    private String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onStart() {
        super.onStart();
        Log.d("onStart", "called");
        ((MainPage) Objects.requireNonNull(getActivity())).lockUnlockDrawer(0);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())){
            requestPermission();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }

    public void UpdateProfileData() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(true);
        pDialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<LoginResponce> call = apiService.UpdateProfile(formEditTexts.get(0).getText().toString(), formEditTexts.get(1).getText().toString(),
                                                                formEditTexts.get(2).getText().toString(), formEditTexts.get(3).getText().toString(), MainPage.userId);
            call.enqueue(new Callback<LoginResponce>() {

                @Override
                public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {

                    if (response.isSuccessful()) {
                        pDialog.dismiss();
                        Toasty.success(getActivity(), "Profile Update Successfully", Toasty.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.error(getActivity(), "Profile Failed Update", Toasty.LENGTH_SHORT, true).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponce> call, Throwable t) {
                    pDialog.dismiss();

                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void requestPermission() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}

package com.quickinterior.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.quickinterior.Activity.ColorCodeSelection;
import com.quickinterior.Activity.MainPage;
import com.quickinterior.Extra.Blur;
import com.quickinterior.Extra.DetectConnection;
import com.quickinterior.Module.AllList;
import com.quickinterior.Module.ProfileResponse;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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

public class Home extends Fragment {

    View view;
    @BindView(R.id.Homeimage)
    ImageView imageViewHome;
    @BindViews({R.id.HomeName, R.id.HomeLoc})
    List<TextView> textViews;

    List<ProfileResponse> profileResponceList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        getProfile();


        return view;
    }

    @OnClick({R.id.viewClientDetails, R.id.colorCode})
    public  void onClick(View view){
        switch (view.getId()){

            case R.id.viewClientDetails:

                break;

            case R.id.colorCode:
                Intent intent=new Intent(getActivity(), ColorCodeSelection.class);
                startActivity(intent);

                break;
        }
    }

    private void getProfile() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getProfile(MainPage.userId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                profileResponceList = allList.getProfileResponseList();
                if (profileResponceList.size() == 0) {
                    Toasty.error(getActivity(), "No data found", Toasty.LENGTH_SHORT).show();

                } else {

                    for (int i = 0; i < profileResponceList.size(); i++) {

                        MainPage.user_profile_photo = profileResponceList.get(i).getUser_profile_photo();
                        MainPage.user_mobileno = profileResponceList.get(i).getUser_mobileno();
                        MainPage.user_fullname = profileResponceList.get(i).getUser_fullname();
                        MainPage.user_emailid = profileResponceList.get(i).getUser_emailid();
                        MainPage.user_address = profileResponceList.get(i).getUser_address();

                        textViews.get(0).setText(profileResponceList.get(i).getUser_fullname());
                        textViews.get(1).setText(profileResponceList.get(i).getUser_address());

                        try {
                            Transformation blurTransformation = new Transformation() {
                                @Override
                                public Bitmap transform(Bitmap source) {
                                    Bitmap blurred = Blur.fastblur(getActivity(), source, 10);
                                    source.recycle();
                                    return blurred;
                                }

                                @Override
                                public String key() {
                                    return "blur()";
                                }
                            };

                            final int finalI = i;
                            Picasso.with(getActivity())
                                    .load("http://prabhagmaza.com/androidApp/QuickInterior/Executive/"+profileResponceList.get(i).getUser_profile_photo())
                                    .placeholder(R.drawable.defaultman)
                                    .transform(blurTransformation)
                                    .into(imageViewHome, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {

                                            Picasso.with(getActivity())
                                                    .load("http://prabhagmaza.com/androidApp/QuickInterior/Executive/"+profileResponceList.get(finalI).getUser_profile_photo())
                                                    .placeholder(imageViewHome.getDrawable())
                                                    .into(imageViewHome);

                                        }

                                        @Override
                                        public void onError() {
                                        }
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {
                Toasty.error(getActivity(), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });
    }



    public void onStart() {
        super.onStart();
        Log.d("onStart", "called");
        MainPage.toolbar_container.setVisibility(View.VISIBLE);
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(0);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())){
            getProfile();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }
}

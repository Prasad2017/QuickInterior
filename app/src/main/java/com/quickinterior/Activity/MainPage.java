package com.quickinterior.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.quickinterior.Extra.Blur;
import com.quickinterior.Extra.Common;
import com.quickinterior.Fragment.Home;
import com.quickinterior.Fragment.Profile;
import com.quickinterior.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainPage extends AppCompatActivity {

    public static String userId;
    AlertDialog alertDialog1;
    AlertDialog.Builder dialogBuilder;
    LayoutInflater inflater;
    public static LinearLayout toolbar_container;
    public static RelativeLayout titleLayout;
    View dialogView;
    public static ImageView menu;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    public static String user_fullname, user_mobileno, user_profile_photo, user_emailid, user_address, projectId, project_Id, projectName;
    boolean doubleBackToExitPressedOnce = false;
    public  static TextView navName, title;
    public  static ImageView imageView,back;
    public static DrawerLayout drawerLayout;

    public static  Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar_container = findViewById(R.id.toolbar_container);

        initViews();
        loadFragment(new Home(), false);

        try {

            userId = Common.getSavedUserData(MainPage.this, "userId");
            user_fullname = Common.getSavedUserData(MainPage.this, "user_name");
            user_profile_photo = Common.getSavedUserData(MainPage.this, "user_photo");
            Common.saveUserData(MainPage.this, "projectId", projectId);
            projectName = Common.getSavedUserData(MainPage.this, "project_name");
            user_address = Common.getSavedUserData(MainPage.this, "user_address");
            projectId=Common.getSavedUserData(MainPage.this, "projectId");

            Log.e("userId", "" + userId+"--"+project_Id);


            View hview = ((NavigationView) findViewById(R.id.navigationView)).getHeaderView(0);
            navName = hview.findViewById(R.id.navName);
            imageView = hview.findViewById(R.id.imageView);

            navName.setText(user_fullname);

            try {
                Transformation blurTransformation = new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        Bitmap blurred = Blur.fastblur(MainPage.this, source, 10);
                        source.recycle();
                        return blurred;
                    }

                    @Override
                    public String key() {
                        return "blur()";
                    }
                };


                Picasso.with(MainPage.this)
                        .load("http://prabhagmaza.com/androidApp/QuickInterior/Executive/"+user_profile_photo)
                        .placeholder(R.drawable.defaultman)
                        .transform(blurTransformation)
                        .into(imageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                                Picasso.with(MainPage.this)
                                        .load("http://prabhagmaza.com/androidApp/QuickInterior/Executive/"+user_profile_photo)
                                        .placeholder(imageView.getDrawable())
                                        .into(imageView);

                            }

                            @Override
                            public void onError() {
                            }
                        });
            }catch (Exception e){
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Profile:
                        loadFragment(new Profile(), true);
                        break;

                    case R.id.Worksheet:
                        intent =new Intent(MainPage.this, WorksheetActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.Work_Completed:
                        intent =new Intent(MainPage.this, WorkCompleteActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.Stock:
                        intent =new Intent(MainPage.this, Stock.class);
                        intent.putExtra("projectId",projectId);
                        startActivity(intent);
                        break;

                    case R.id.Refer_Client:
                        intent =new Intent(MainPage.this, ReferClient.class);
                        startActivity(intent);
                        break;

                    case R.id.Refer_Vendor:
                        intent =new Intent(MainPage.this, ReferVendor.class);
                        startActivity(intent);
                        break;

                    case R.id.Expense:
                        intent =new Intent(MainPage.this, Expense.class);
                        startActivity(intent);
                        break;

                    case R.id.LabourPayment:
                        intent =new Intent(MainPage.this, LabourPayment.class);
                        startActivity(intent);
                        break;

                }

                return false;
            }
        });

    }


    private void initViews() {

        drawerLayout = findViewById(R.id.drawer_layout);
        title = findViewById(R.id.title);
        titleLayout = findViewById(R.id.titleLayout);
        menu = (ImageView) findViewById(R.id.menu);
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);

    }

    @OnClick({R.id.back, R.id.menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                removeCurrentFragmentAndMoveBack();
                break;

            case R.id.menu:
                if (!MainPage.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    MainPage.drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;

        }
    }


    @Override
    public void onBackPressed() {
        if (menu.getVisibility() == View.VISIBLE) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
        } else {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back once more to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    public void removeCurrentFragmentAndMoveBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    public void loadFragment(Fragment fragment, Boolean bool) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        if (bool) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void lockUnlockDrawer(int lockMode) {
        drawerLayout.setDrawerLockMode(lockMode);
        if (lockMode == DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            menu.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
        } else {
            menu.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
        }
    }

}


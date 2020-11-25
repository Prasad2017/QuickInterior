package com.quickinterior.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.quickinterior.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashScreen extends AppCompatActivity {


    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    private static int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                finish();

            }
        }, SPLASH_TIME_OUT);


    }


}
package com.quickinterior.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.quickinterior.Adapter.Pager;
import com.quickinterior.Extra.Common;
import com.quickinterior.Fragment.AvailableStock;
import com.quickinterior.Fragment.WorkOrder;
import com.quickinterior.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Stock extends AppCompatActivity {

    public static Activity activity;
    @BindView(R.id.simpleTabLayout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager viewPager;
    public static String  project_Id,projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

       /* Intent intent=getIntent();
        projectId=intent.getStringExtra("projectId");
        Common.saveUserData(Stock.this, project_Id, projectId);*/


//        setupViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Work Order"));
        tabLayout.addTab(tabLayout.newTab().setText("Available Stock"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override



            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        tabLayout.setupWithViewPager(viewPager);
//        setupTabIcons();

    }


    @OnClick({ R.id.back})
    public void onClick(View view){
        switch (view.getId()){

            case R.id.back:
                Intent intent=new Intent(Stock.this, MainPage.class);
                startActivity(intent);
                break;
        }
    }

    private void setupTabIcons() {

    TextView tabOne = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.customtext, null);
        tabOne.setText("Work Order");

    TextView tabTwo = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.customtext, null);
        tabTwo.setText("Available Stock");

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WorkOrder(), "Work Order");
        adapter.addFragment(new AvailableStock(), "Available Stock");
        viewPager.setAdapter(adapter);

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}

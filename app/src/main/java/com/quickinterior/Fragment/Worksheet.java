package com.quickinterior.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.quickinterior.Activity.MainPage;
import com.quickinterior.Extra.DetectConnection;
import com.quickinterior.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class Worksheet extends Fragment {

    View view;
    CalendarView calender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_worksheet, container, false);
        ButterKnife.bind(this, view);
        calender = view. findViewById(R.id.calender);
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, final int year, final int month, final int dayOfMonth) {

                String  date = "";

                if((month + 1)>9) {
                    if ((dayOfMonth) > 9) {

                        date = year + "-" + (month + 1) + "-" + dayOfMonth;

                    }else {
                        date= year + "-" + (month + 1) + "-" + "0"+dayOfMonth;

                    }
                }else {
                    if((dayOfMonth)>9) {
                        date= year + "-" + "0"+(month + 1) + "-" + dayOfMonth;

                    }else {
                        date = year + "-" + "0" + (month + 1) + "-" + "0" + dayOfMonth;
                    }
                }

                final String finalDate = date;
                Toast.makeText(getActivity(),"date=="+finalDate , Toast.LENGTH_SHORT).show();

                ProjectWorksheet worksheet=new ProjectWorksheet();
                Bundle bundle=new Bundle();
                bundle.putString("date",finalDate);
                worksheet.setArguments(bundle);
                ((MainPage)getActivity()).loadFragment(worksheet, false);


            }
        });

     /*   MainPage.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainPage)getActivity()).removeCurrentFragmentAndMoveBack();
            }
        });*/
        return view;
    }

    @OnClick({ R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back:
//                Intent intent = new Intent(LabourPayment.this, MainPage.class);
//                startActivity(intent);
                break;
        }
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

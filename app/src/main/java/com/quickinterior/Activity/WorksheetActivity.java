package com.quickinterior.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.quickinterior.Fragment.ProjectWorksheet;
import com.quickinterior.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class WorksheetActivity extends AppCompatActivity {

    CalendarView calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worksheet);
        ButterKnife.bind(this);
        calender = findViewById(R.id.calender);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, final int year, final int month, final int dayOfMonth) {

                String date = "";

                if ((month + 1) > 9) {
                    if ((dayOfMonth) > 9) {

                        date = year + "-" + (month + 1) + "-" + dayOfMonth;

                    } else {
                        date = year + "-" + (month + 1) + "-" + "0" + dayOfMonth;

                    }
                } else {
                    if ((dayOfMonth) > 9) {
                        date = year + "-" + "0" + (month + 1) + "-" + dayOfMonth;

                    } else {
                        date = year + "-" + "0" + (month + 1) + "-" + "0" + dayOfMonth;
                    }
                }

                final String finalDate = date;
                Toast.makeText(getApplicationContext(), "date==" + finalDate, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(WorksheetActivity.this, ProjectWorksheetActivity.class);
                intent.putExtra("date",finalDate);
                startActivity(intent);

               /* ProjectWorksheet worksheet = new ProjectWorksheet();
                Bundle bundle = new Bundle();
                bundle.putString("date", finalDate);
                worksheet.setArguments(bundle);
                ((MainPage) getApplicationContext()).loadFragment(worksheet, true);*/
            }

        });

    }

    @OnClick({ R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back:
                Intent intent = new Intent(WorksheetActivity.this, MainPage.class);
                startActivity(intent);
                break;
        }
    }
}

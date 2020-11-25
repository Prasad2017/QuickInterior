package com.quickinterior.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.andreabaccega.widget.FormEditText;
import com.quickinterior.Module.LoginResponce;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabourPayment extends AppCompatActivity {

    @BindViews({R.id.count, R.id.amount, R.id.name, R.id.dateTime})
    List<FormEditText> editTexts;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_payment);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


    @OnClick({R.id.SaveButton, R.id.back, R.id.dateTime})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.SaveButton:
                if(validate(editTexts.get(2)) && validate(editTexts.get(1))&& validate(editTexts.get(3))){
                    addLabourPayment();
                }else {

                }
                break;

            case R.id.back:
                Intent intent=new Intent(LabourPayment.this, MainPage.class);
                startActivity(intent);
                break;


            case R.id.dateTime:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(LabourPayment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                editTexts.get(3).setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

                break;
        }
    }

    private void addLabourPayment() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponce> call = apiInterface.addLabourPayment(MainPage.userId, "1", editTexts.get(1).getText().toString().trim(), editTexts.get(2).getText().toString().trim(), editTexts.get(3).getText().toString().trim());

        call.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {

                    Toasty.success(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(LabourPayment.this, MainPage.class);
                    startActivity(intent);


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

    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }
}

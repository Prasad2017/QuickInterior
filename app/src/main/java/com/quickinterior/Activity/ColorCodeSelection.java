package com.quickinterior.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.quickinterior.Module.AllList;
import com.quickinterior.Module.CategoryResopnse;
import com.quickinterior.Module.LoginResponce;
import com.quickinterior.Module.SubCategoryResopnse;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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

public class ColorCodeSelection extends AppCompatActivity {

    @BindView(R.id.roomSpinner)
    SearchableSpinner roomSpinner;
    @BindView(R.id.paintSpinner)
    SearchableSpinner paintSpinner;
    @BindViews({R.id.east, R.id.west, R.id.north, R.id.south})
    List<CheckBox> checkBoxes;
   /* @BindViews({R.id.white, R.id.plainWhite})
    List<RadioButton> radioButtons;*/
    @BindViews({R.id.colorName, R.id.colorCode, R.id.awColorName, R.id.awColorCode, R.id.hwColorName, R.id.hwColorCode})
    List<EditText> editTexts;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    List<CategoryResopnse> categoryResopnseList = new ArrayList<>();
    String catName, catId;
    String[] catNameList, catIdList;
    List<SubCategoryResopnse> sunCategoryResopnseList = new ArrayList<>();
    String subCatName, subCatId;
    String[]  subCatNameList, subCatIdList;

    String livingSides = "";
    RadioButton radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_code_selection);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getRoomName();
        getPaintList();

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                catName= String.valueOf(roomSpinner.getSelectedItem());
                catId= categoryResopnseList.get(i).getInterior_id();
                Log.e("",""+catId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        paintSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                subCatName= String.valueOf(paintSpinner.getSelectedItem());
                subCatId= sunCategoryResopnseList.get(i).getSub_id();
                Log.e("",""+catId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if(checkBoxes.get(0).isChecked()){
            livingSides = livingSides + "East";
        } if(checkBoxes.get(1).isChecked()){
            livingSides = livingSides + "West";
        } if(checkBoxes.get(2).isChecked()){
            livingSides = livingSides + "North";
        } if(checkBoxes.get(3).isChecked()){
            livingSides = livingSides + "South";
        }

      /*  int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButtons = (RadioButton) findViewById(selectedId);*/

    }

    @OnClick({R.id.submitColor, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitColor:

                addColorCode();

               /* if(validate(editTexts.get(0)) && validate(editTexts.get(1)) && validate(editTexts.get(3)) && validate(editTexts.get(4))){
                    addColorCode();
                }else {

                }*/
                break;

            case R.id.back:
                Intent intent=new Intent(ColorCodeSelection.this, MainPage.class);
                startActivity(intent);
                break;
        }
    }

    private void addColorCode() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponce> call = apiInterface.AddColorCode(MainPage.projectId,  MainPage.userId, catId, subCatId, livingSides,
                editTexts.get(0).getText().toString(), editTexts.get(1).getText().toString(), editTexts.get(2).getText().toString(),
                editTexts.get(3).getText().toString(), editTexts.get(4).getText().toString(), editTexts.get(5).getText().toString(), "White");

        call.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {

                    Toasty.success(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(ColorCodeSelection.this, MainPage.class);
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


    private void getRoomName() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getCategory();
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                categoryResopnseList = allList.getCategoryResopnses();

                if (categoryResopnseList.size()==0){

                }else {

                    catIdList = new String[categoryResopnseList.size()];
                    catNameList = new String[categoryResopnseList.size()];

                    for (int i = 0; i< categoryResopnseList.size(); i++){

                        catIdList[i] = categoryResopnseList.get(i).getInterior_id();
                        catNameList[i] = categoryResopnseList.get(i).getInterior_name();

                    }

                    final ArrayAdapter arrayAdapter = new ArrayAdapter(ColorCodeSelection.this, android.R.layout.simple_spinner_item, categoryResopnseList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
                    roomSpinner.setAdapter(arrayAdapter);

                }

            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {

                Toasty.error(ColorCodeSelection.this, "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });


    }


    private void getPaintList() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getSubType("1");
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                sunCategoryResopnseList = allList.getSubCategoryResopnses();

                if (sunCategoryResopnseList.size()==0){

                }else {

                    subCatIdList = new String[sunCategoryResopnseList.size()];
                    subCatNameList = new String[sunCategoryResopnseList.size()];

                    for (int i = 0; i< sunCategoryResopnseList.size(); i++){

                        subCatIdList[i] = sunCategoryResopnseList.get(i).getSub_id();
                        subCatNameList[i] = sunCategoryResopnseList.get(i).getSubtype_name();

                    }

                    final ArrayAdapter arrayAdapter = new ArrayAdapter(ColorCodeSelection.this, android.R.layout.simple_spinner_item, sunCategoryResopnseList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
                    paintSpinner.setAdapter(arrayAdapter);

                }

            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {

                Toasty.error(ColorCodeSelection.this, "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });


    }
}

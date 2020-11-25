package com.quickinterior.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.quickinterior.Module.LoginResponce;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;

import java.util.List;
import java.util.Objects;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditStock extends AppCompatActivity {

    @BindViews({R.id.edtStockproduct, R.id.edtStockAvail, R.id.edtStockreceived})
    List<EditText> editTexts;

    int pending;
    String po_avlqty;
    String po_id;
    String po_id_fk;
    String po_product_status = "";
    String productname;
    String recQty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        po_id = getIntent().getStringExtra("po_product");
        po_id_fk = getIntent().getStringExtra("poid");
        po_avlqty = getIntent().getStringExtra("po_avlqty");
        productname = getIntent().getStringExtra("productname");
        recQty = getIntent().getStringExtra("po_rcvqty");

        editTexts.get(0).setText(productname);
        editTexts.get(1).setText(po_avlqty);

    }

    @OnClick({R.id.edtAvalStockSubmit, R.id.back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.edtAvalStockSubmit:
                verifystock();
                break;

            case R.id.back:
                Intent intent=new Intent(EditStock.this, Stock.class);
                startActivity(intent);
                break;
        }
    }

    public void verifystock() {
        final int parseInt = Integer.parseInt(editTexts.get(2).getText().toString());
        if (parseInt > Integer.parseInt(this.po_avlqty)) {
            Toast.makeText(this, "Please enter received quantity less than available quantity ", Toast.LENGTH_SHORT).show();
            return;


        }
        this.pending = Integer.parseInt(po_avlqty) - parseInt;
        if (pending > 1) {
            po_product_status = "Pending";
        } else {
            po_product_status = "Complete";
        }
        final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, 5);
        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.setTitleText("Loading...");
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.show();


        int recq =parseInt+ Integer.parseInt(recQty);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponce> call = apiInterface.editStock(po_id, po_id_fk, String.valueOf(recq), String.valueOf(pending), po_product_status);

        call.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {

                    sweetAlertDialog.dismiss();
                    Toasty.success(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    finish();

                } else if (data.equalsIgnoreCase("0")) {
                    sweetAlertDialog.dismiss();
                    Toasty.error(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(Call<LoginResponce> call, Throwable t) {
                sweetAlertDialog.dismiss();
                Toasty.error(getApplicationContext(), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });

    }

}

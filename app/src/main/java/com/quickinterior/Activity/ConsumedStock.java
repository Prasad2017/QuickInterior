package com.quickinterior.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.quickinterior.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class ConsumedStock extends AppCompatActivity {

    @BindViews({R.id.edtStockproduct, R.id.edtStockAvail, R.id.edtStockreceived})
    List<EditText> editTexts;

    @BindViews({R.id.textRecived, R.id.textConsumed})
    List<TextView> textViews;

    int pending;
    String po_avlqty;
    String po_id;
    String prodt_id;
    String po_product_status = "";
    String productname;
    String recQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prodt_id = getIntent().getStringExtra("prodid");
        po_id = getIntent().getStringExtra("poid");
        productname = getIntent().getStringExtra("prodName");
        recQty = getIntent().getStringExtra("prodqty");


//        Toast.makeText(this, "po_id"+prodt_id, Toast.LENGTH_SHORT).show();

        editTexts.get(0).setText(productname);
        editTexts.get(1).setText(recQty);

        textViews.get(0).setText("Received Stock");
        textViews.get(1).setText("Consumed Stock");

    }

    @OnClick({R.id.edtAvalStockSubmit, R.id.back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.edtAvalStockSubmit:
                verifystock();
                break;

            case R.id.back:
                Intent intent=new Intent(ConsumedStock.this, Stock.class);
                startActivity(intent);
                break;
        }
    }

    public void verifystock() {


        if (Integer.parseInt(ConsumedStock.this.editTexts.get(2).getText().toString().trim()) > Integer.parseInt(recQty)) {
            Toast.makeText(ConsumedStock.this, "Please enter consumable quantity less than received quantity ", Toast.LENGTH_SHORT).show();
            return;
        }
        final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(ConsumedStock.this, 5);
        sweetAlertDialog.getProgressHelper().setBarColor(ConsumedStock.this.getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.setTitleText("Loading...");
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.show();
        RequestParams requestParams = new RequestParams();
        requestParams.put("po_product_id", prodt_id);
        requestParams.put("consumabale_qty", editTexts.get(2).getText().toString().trim());

        new AsyncHttpClient().post("http://prabhagmaza.com/androidApp/QuickInterior/Executive/editConsumedStock.php", requestParams, new AsyncHttpResponseHandler() {
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                String str = FirebaseAnalytics.Param.SUCCESS;
                try {
                    JSONObject jSONObject = new JSONObject(new String(bArr));
                    sweetAlertDialog.dismiss();
                    if (jSONObject.getString(str).equalsIgnoreCase("1")) {
                        sweetAlertDialog.dismiss();
                        Toast.makeText(ConsumedStock.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();

                    } else if (jSONObject.getString(str).equalsIgnoreCase("0")) {
                        sweetAlertDialog.dismiss();
                        Toast.makeText(ConsumedStock.this, "Not Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                sweetAlertDialog.dismiss();
                Toast.makeText(ConsumedStock.this, " check network connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

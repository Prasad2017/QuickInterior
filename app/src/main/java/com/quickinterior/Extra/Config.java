package com.quickinterior.Extra;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;


import androidx.core.content.ContextCompat;

import com.quickinterior.Activity.Login;
import com.quickinterior.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Config {

    public static String VehicleNumber="^[A-Z]{2}\\s[0-9|\\s]{1,2}\\s[A-Z|\\s]{1,2}\\s[0-9]{1,4}$";
    public static String PanCardNumber="^[A-Z]{5}[0-9]{4}[A-Z]{1}$";
    public static String GSTNumber="\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}";
    public static String ADDRESSS="";

    public static void moveTo(Context context, Class targetClass) {
        Intent intent = new Intent(context, targetClass);
        context.startActivity(intent);
    }
    public static boolean validateEmail(EditText editText, Context context) {
        String email = editText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
//            editText.setError(context.getString(R.string.err_msg_email));
            editText.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean validateNumber(EditText editText, Context context) {
        String number = editText.getText().toString().trim();

        if (number.isEmpty() || number.matches(VehicleNumber)) {
            editText.setError("Please enter a valid vehicle number");
            editText.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean validateGSTNumber(EditText editText, Context context) {
        String number = editText.getText().toString().trim();

        if (number.isEmpty() || number.matches(GSTNumber)) {
            editText.setError("Please enter a valid GST number");
            editText.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean validatePanNumber(EditText editText, Context context) {
        String number = editText.getText().toString().trim();

        if (number.isEmpty() || number.matches(PanCardNumber)) {
            editText.setError("Please enter a valid Pan number");
            editText.requestFocus();
            return false;
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void showCustomAlertDialog(Context context, String title, String msg, int type) {
        SweetAlertDialog alertDialog = new SweetAlertDialog(context, type);
        alertDialog.setTitleText(title);

        if (msg.length() > 0)
            alertDialog.setContentText(msg);
        alertDialog.show();
        Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
        btn.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    public static void showLoginCustomAlertDialog(final Context context, String title, String msg, int type) {
        SweetAlertDialog alertDialog = new SweetAlertDialog(context, type);
        alertDialog.setTitleText(title);
        alertDialog.setCancelText("Login");
        alertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Config.moveTo(context, Login.class);

            }
        });
        if (msg.length() > 0)
            alertDialog.setContentText(msg);
        alertDialog.show();
        Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
        btn.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        Button btn1 = (Button) alertDialog.findViewById(R.id.cancel_button);
        btn1.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

    }
}

package com.quickinterior.Fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.quickinterior.Activity.MainPage;
import com.quickinterior.Extra.Blur;
import com.quickinterior.Extra.DetectConnection;
import com.quickinterior.Module.AllList;
import com.quickinterior.Module.ProfileResponse;
import com.quickinterior.R;
import com.quickinterior.Retrofit.ApiClient;
import com.quickinterior.Retrofit.ApiInterface;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.WHITE;


public class Profile extends Fragment {


    View view;
    @BindView(R.id.profileImage)
    ImageView profileImage;
    @BindView(R.id.qrCodeImage)
    ImageView qrCodeImage;
    @BindViews({R.id.userName, R.id.userNumber, R.id.userEmail, R.id.userAddress})
    List<TextView> textViews;
    List<ProfileResponse> profileResponseList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);


        return view;

    }

    public void GenerateClick(){

        try {
            //setting size of qr code
            int width =300;
            int height = 300;
            int smallestDimension = width < height ? width : height;

            //setting parameters for qr code
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap =new HashMap<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("userId", MainPage.userId);
                jsonObject.put("userName", MainPage.user_fullname);
                jsonObject.put("userNumber", MainPage.user_mobileno);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Log.e("Profile", ""+String.valueOf(jsonObject));

            CreateQRCode(String.valueOf(jsonObject), charset, hintMap, smallestDimension, smallestDimension);

        } catch (Exception ex) {
            Log.e("QrGenerate",ex.getMessage());
        }

    }

    public  void CreateQRCode(String qrCodeData, String charset, Map hintMap, int qrCodeheight, int qrCodewidth){

        try {
            //generating qr code in bitMatrix type
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
            //converting bitMatrix to bitmap

            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            // All are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    //pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
                    pixels[offset + x] = matrix.get(x, y) ?
                            ResourcesCompat.getColor(getResources(),R.color.black,null) :WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            //setting bitmap to image view

            Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.name);
            qrCodeImage.setImageBitmap(mergeBitmaps(overlay,bitmap));

        }catch (Exception er){
            Log.e("QrGenerate",er.getMessage());
        }

    }

    public Bitmap mergeBitmaps(Bitmap overlay, Bitmap bitmap) {

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        Bitmap combined = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bitmap, new Matrix(), null);

        int centreX = (canvasWidth  - overlay.getWidth()) /2;
        int centreY = (canvasHeight - overlay.getHeight()) /2 ;
        canvas.drawBitmap(overlay, centreX, centreY, null);

        return combined;

    }

    public void onStart() {
        super.onStart();
        Log.d("onStart", "called");
        MainPage.toolbar_container.setVisibility(View.GONE);
        ((MainPage) Objects.requireNonNull(getActivity())).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())){
            getProfile();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }

    private void getProfile() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getProfile(MainPage.userId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                profileResponseList = allList.getProfileResponseList();
                if (profileResponseList.size()==0){
                    Toasty.error(getActivity(), "", Toasty.LENGTH_SHORT).show();

                }else {

                    for (int i=0;i<profileResponseList.size();i++){

                        MainPage.user_profile_photo = profileResponseList.get(i).getUser_profile_photo();
                        MainPage.user_mobileno = profileResponseList.get(i).getUser_mobileno();
                        MainPage.user_fullname = profileResponseList.get(i).getUser_fullname();
                        MainPage.user_emailid = profileResponseList.get(i).getUser_emailid();
                        MainPage.user_address = profileResponseList.get(i).getUser_address();

                        textViews.get(0).setText(profileResponseList.get(i).getUser_fullname());
                        textViews.get(1).setText(profileResponseList.get(i).getUser_mobileno());
                        textViews.get(2).setText(profileResponseList.get(i).getUser_emailid());
                        textViews.get(3).setText(profileResponseList.get(i).getUser_address());


                        try {
                            Transformation blurTransformation = new Transformation() {
                                @Override
                                public Bitmap transform(Bitmap source) {
                                    Bitmap blurred = Blur.fastblur(getActivity(), source, 10);
                                    source.recycle();
                                    return blurred;
                                }

                                @Override
                                public String key() {
                                    return "blur()";
                                }
                            };

                            final int finalI = i;
                            Picasso.with(getActivity())
                                    .load("http://prabhagmaza.com/androidApp/QuickInterior/Executive/"+profileResponseList.get(i).getUser_profile_photo())
                                    .placeholder(R.drawable.defaultman)
                                    .transform(blurTransformation)
                                    .into(profileImage, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {

                                            Picasso.with(getActivity())
                                                    .load("http://prabhagmaza.com/androidApp/QuickInterior/Executive/"+profileResponseList.get(finalI).getUser_profile_photo())
                                                    .placeholder(profileImage.getDrawable())
                                                    .into(profileImage);

                                        }

                                        @Override
                                        public void onError() {
                                        }
                                    });

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //To Generate QR code using userName and userMobile..
                      //  GenerateClick();
                    }
                }
            }
            @Override
            public void onFailure(Call<AllList> call, Throwable t) {
                Toasty.error(getActivity(), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });

    }
}

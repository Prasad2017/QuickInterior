package com.quickinterior.Retrofit;


import com.quickinterior.Module.AllList;
import com.quickinterior.Module.LoginResponce;
import com.quickinterior.Module.VendorResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {


    @FormUrlEncoded
    @POST("androidApp/QuickInterior/Executive/Login.php")
    Call<LoginResponce> login(@Field("user_password") String user_password,
                              @Field("user_emailid") String user_emailid);


    @GET("androidApp/QuickInterior/Executive/getProjects.php")
    Call<AllList> getProjects();


    @GET("androidApp/QuickInterior/Executive/getQuatationServices.php")
    Call<AllList> getQuatationServices();


    @GET("androidApp/QuickInterior/Executive/getCompleteWork.php")
    Call<AllList> getCompleteWork(@Query("work_date") String work_date,
                                  @Query("project_fk_id")String project_fk_id);


    @GET("androidApp/QuickInterior/Executive/getProfile.php")
    Call<AllList> getProfile(@Query("user_id") String user_id);


    @GET("androidApp/QuickInterior/Executive/getSubCatList.php")
    Call<AllList> getSubCatList(@Query("cat_id_fk") String cat_id_fk);


    @FormUrlEncoded
    @POST("androidApp/QuickInterior/Executive/UpdateProfile.php")
    Call<LoginResponce> UpdateProfile(@Field("user_fullname") String userName,
                                      @Field("user_emailid") String userMobile,
                                      @Field("user_mobileno") String userEmail,
                                      @Field("user_address") String userBirthDate,
                                      @Field("user_id") String userId);


    @FormUrlEncoded
    @POST("androidApp/QuickInterior/Executive/referClient.php")
    Call<LoginResponce> referClient(@Field("user_fullname") String userName,
                                      @Field("user_mobileno") String userMobile,
                                      @Field("user_address") String user_address,
                                      @Field("user_emailid") String user_emailid,
                                      @Field("client_refer_by") String client_refer_by);


    @FormUrlEncoded
    @POST("androidApp/QuickInterior/Executive/referVendor.php")
    Call<LoginResponce> referVendor(@Field("vendor_category") String vendor_category,
                                      @Field("user_fullname") String userName,
                                      @Field("user_mobileno") String userMobile,
                                      @Field("user_address") String user_address,
                                      @Field("user_emailid") String user_emailid,
                                      @Field("vendor_refer_by") String vendor_refer_by);


    @FormUrlEncoded
    @POST("androidApp/QuickInterior/Executive/uploadWork.php")
    Call<LoginResponce> uploadWork(@Field("project_fk_id")String project_fk_id,
                                      @Field("quotation_id_fk")String quotation_id_fk,
                                      @Field("sub_cat_id_fk")String sub_cat_id_fk,
                                      @Field("work_activity_percent")String work_activity_percent,
                                      @Field("work_image")String work_image);

    @FormUrlEncoded
    @POST("androidApp/QuickInterior/Executive/add_exec_expense.php")
    Call<LoginResponce> addExpense(@Field("executive_id_fk")String executive_id_fk,
                                      @Field("expense_amt")String expense_amt,
                                      @Field("expense_desc")String expense_desc,
                                      @Field("expense_type")String expense_type);


    @FormUrlEncoded
    @POST("androidApp/QuickInterior/Executive/addLabourPayment.php")
    Call<LoginResponce> addLabourPayment(@Field("executive_id_fk")String executive_id_fk,
                                      @Field("labour_count")String labour_count,
                                      @Field("labour_amt")String labour_amt,
                                      @Field("labour_name")String labour_name,
                                      @Field("payment_date")String payment_date);


    @GET("androidApp/QuickInterior/Executive/getVendorList.php")
    Call<AllList> getVendorList(@Query("project_id_fk")String project_fk_id);

    @GET("androidApp/QuickInterior/Executive/getAvailableStock.php")
    Call<AllList> getAvailableStock(@Query("project_id_fk")String project_fk_id);

    @GET("androidApp/QuickInterior/Executive/getStock.php")
    Call<AllList> getStock(@Query("po_id_fk")String po_id_fk);

    @GET("androidApp/QuickInterior/Executive/getSubCategory.php")
    Call<AllList> getCategory();

    @GET("androidApp/QuickInterior/Admin/getSubType.php")
    Call<AllList> getSubType(@Query("ser_id") String ser_id);

    @FormUrlEncoded
    @POST("androidApp/QuickInterior/Executive/editStock.php")
    Call<LoginResponce> editStock(@Field("po_id")String po_id,
                                   @Field("po_id_fk")String po_id_fk,
                                   @Field("recieved_qty")String recieved_qty,
                                   @Field("pending_qty")String pending_qty,
                                   @Field("po_product_status")String po_product_status);

 @FormUrlEncoded
    @POST("androidApp/QuickInterior/Executive/editConsumedStock.php")
    Call<LoginResponce> consumedStock(@Field("po_id")String po_id,
                                   @Field("po_id_fk")String po_id_fk,
                                   @Field("recieved_qty")String recieved_qty,
                                   @Field("pending_qty")String pending_qty,
                                   @Field("po_product_status")String po_product_status);

    @FormUrlEncoded
    @POST("androidApp/QuickInterior/Executive/AddColorCode.php")
    Call<LoginResponce> AddColorCode(@Field("project_id") String project_id,
                                    @Field("user_id_fk") String user_id_fk,
                                    @Field("room_id") String room_id,
                                    @Field("paint_id") String paint_id,
                                    @Field("living_sides") String living_sides,
                                    @Field("living_colorname") String living_colorname,
                                    @Field("living_colorcode") String living_colorcode,
                                    @Field("awall_colorname") String awall_colorname,
                                    @Field("awall_colorcode") String awall_colorcode,
                                    @Field("hwall_colorname") String hwall_colorname,
                                    @Field("hwall_colorcode") String hwall_colorcode,
                                    @Field("celing_color") String celing_color);



}

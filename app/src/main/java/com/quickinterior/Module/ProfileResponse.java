package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("user_id")
    String userId;

    @SerializedName("user_fullname")
    String user_fullname;

    @SerializedName("user_emailid")
    String user_emailid;

    @SerializedName("user_mobileno")
    String user_mobileno;

    @SerializedName("user_address")
    String user_address;

    @SerializedName("user_profile_photo")
    String user_profile_photo;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getUser_emailid() {
        return user_emailid;
    }

    public void setUser_emailid(String user_emailid) {
        this.user_emailid = user_emailid;
    }

    public String getUser_mobileno() {
        return user_mobileno;
    }

    public void setUser_mobileno(String user_mobileno) {
        this.user_mobileno = user_mobileno;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_profile_photo() {
        return user_profile_photo;
    }

    public void setUser_profile_photo(String user_profile_photo) {
        this.user_profile_photo = user_profile_photo;
    }
}

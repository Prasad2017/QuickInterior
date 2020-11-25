package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class LoginResponce {

    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("user_id")
    String userId;

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
}



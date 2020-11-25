package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class CategoryResopnse {
    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("interior_id")
    String interior_id;

    @SerializedName("interior_name")
    String interior_name;

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

    public String getInterior_id() {
        return interior_id;
    }

    public void setInterior_id(String interior_id) {
        this.interior_id = interior_id;
    }

    public String getInterior_name() {
        return interior_name;
    }

    public void setInterior_name(String interior_name) {
        this.interior_name = interior_name;
    }

    @Override
    public String toString() {
        return  interior_name ;
    }
}

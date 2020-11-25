package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class SubCategoryResopnse {
    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("sub_id")
    String sub_id;

    @SerializedName("subtype_name")
    String subtype_name;

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

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getSubtype_name() {
        return subtype_name;
    }

    public void setSubtype_name(String subtype_name) {
        this.subtype_name = subtype_name;
    }

    @Override
    public String toString() {
        return  subtype_name ;
    }
}

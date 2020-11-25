package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class MasterSubCatResopnse {
    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("sub_cat_id")
    String sub_cat_id;

    @SerializedName("cat_id_fk")
    String cat_id_fk;

    @SerializedName("sub_cat_name")
    String sub_cat_name;

    @SerializedName("cat_percentage")
    String cat_percentage;

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

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getCat_id_fk() {
        return cat_id_fk;
    }

    public void setCat_id_fk(String cat_id_fk) {
        this.cat_id_fk = cat_id_fk;
    }

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    @Override
    public String toString() {
        return  sub_cat_name ;
    }

    public String getCat_percentage() {
        return cat_percentage;
    }

    public void setCat_percentage(String cat_percentage) {
        this.cat_percentage = cat_percentage;
    }
}

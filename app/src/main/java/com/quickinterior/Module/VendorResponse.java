package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class VendorResponse {


    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("po_id")
    String po_id;

    @SerializedName("project_id_fk")
    String project_id_fk;

    @SerializedName("supplier_id_fk")
    String supplier_id_fk;

    @SerializedName("po_site_address")
    String po_site_address;

    @SerializedName("user_fullname")
    String user_fullname;

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

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getProject_id_fk() {
        return project_id_fk;
    }

    public void setProject_id_fk(String project_id_fk) {
        this.project_id_fk = project_id_fk;
    }

    public String getSupplier_id_fk() {
        return supplier_id_fk;
    }

    public void setSupplier_id_fk(String supplier_id_fk) {
        this.supplier_id_fk = supplier_id_fk;
    }

    public String getPo_site_address() {
        return po_site_address;
    }

    public void setPo_site_address(String po_site_address) {
        this.po_site_address = po_site_address;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }
}

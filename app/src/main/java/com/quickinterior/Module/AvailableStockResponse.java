package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class AvailableStockResponse {


    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("material_detail_product_name")
    String material_detail_product_name;

    @SerializedName("brand_name")
    String brand_name;

    @SerializedName("recieved_qty")
    String balace_qty;

    @SerializedName("po_product_id")
    String po_product_id;

    @SerializedName("po_id_fk")
    String po_id_fk;

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

    public String getMaterial_detail_product_name() {
        return material_detail_product_name;
    }

    public void setMaterial_detail_product_name(String material_detail_product_name) {
        this.material_detail_product_name = material_detail_product_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBalace_qty() {
        return balace_qty;
    }

    public void setBalace_qty(String balace_qty) {
        this.balace_qty = balace_qty;
    }

    public String getPo_product_id() {
        return po_product_id;
    }

    public void setPo_product_id(String po_product_id) {
        this.po_product_id = po_product_id;
    }

    public String getPo_id_fk() {
        return po_id_fk;
    }

    public void setPo_id_fk(String po_id_fk) {
        this.po_id_fk = po_id_fk;
    }
}

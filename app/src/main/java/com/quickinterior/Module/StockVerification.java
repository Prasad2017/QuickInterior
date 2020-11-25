package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class StockVerification {


    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("po_product_id")
    String po_product_id;

    @SerializedName("po_id_fk")
    String po_id_fk;

    @SerializedName("material_detail_id_fk")
    String material_detail_id_fk;

    @SerializedName("po_product_quantity")
    String po_product_quantity;

    @SerializedName("po_product_rate_per")
    String po_product_rate_per;

    @SerializedName("po_product_status")
    String po_product_status;

    @SerializedName("recieved_qty")
    String recieved_qty;

    @SerializedName("pending_qty")
    String pending_qty;

    @SerializedName("subtype_name")
    String material_name;

    @SerializedName("po_product_total")
    String po_product_total;

    @SerializedName("material_detail_product_name")
    String material_detail_product_name;

    @SerializedName("brand_name")
    String brand_name;

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

    public String getMaterial_detail_id_fk() {
        return material_detail_id_fk;
    }

    public void setMaterial_detail_id_fk(String material_detail_id_fk) {
        this.material_detail_id_fk = material_detail_id_fk;
    }

    public String getPo_product_quantity() {
        return po_product_quantity;
    }

    public void setPo_product_quantity(String po_product_quantity) {
        this.po_product_quantity = po_product_quantity;
    }

    public String getPo_product_rate_per() {
        return po_product_rate_per;
    }

    public void setPo_product_rate_per(String po_product_rate_per) {
        this.po_product_rate_per = po_product_rate_per;
    }

    public String getPo_product_status() {
        return po_product_status;
    }

    public void setPo_product_status(String po_product_status) {
        this.po_product_status = po_product_status;
    }

    public String getRecieved_qty() {
        return recieved_qty;
    }

    public void setRecieved_qty(String recieved_qty) {
        this.recieved_qty = recieved_qty;
    }

    public String getPending_qty() {
        return pending_qty;
    }

    public void setPending_qty(String pending_qty) {
        this.pending_qty = pending_qty;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getPo_product_total() {
        return po_product_total;
    }

    public void setPo_product_total(String po_product_total) {
        this.po_product_total = po_product_total;
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
}

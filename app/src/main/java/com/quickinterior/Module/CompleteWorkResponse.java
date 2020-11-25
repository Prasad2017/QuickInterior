package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class CompleteWorkResponse {

    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;
    @SerializedName("work_activity_percent")
    String work_activity_percent;

    @SerializedName("work_image")
    String work_image;

    @SerializedName("quotation_id_fk")
    String quotation_id_fk;

    @SerializedName("service_name")
    String service_name;

    @SerializedName("work_date")
    String work_date;

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

    public String getWork_activity_percent() {
        return work_activity_percent;
    }

    public void setWork_activity_percent(String work_activity_percent) {
        this.work_activity_percent = work_activity_percent;
    }

    public String getWork_image() {
        return work_image;
    }

    public void setWork_image(String work_image) {
        this.work_image = work_image;
    }

    public String getQuotation_id_fk() {
        return quotation_id_fk;
    }

    public void setQuotation_id_fk(String quotation_id_fk) {
        this.quotation_id_fk = quotation_id_fk;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getWork_date() {
        return work_date;
    }

    public void setWork_date(String work_date) {
        this.work_date = work_date;
    }
}

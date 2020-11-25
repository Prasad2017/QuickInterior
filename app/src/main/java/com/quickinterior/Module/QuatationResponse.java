package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class QuatationResponse {


    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("ser_id")
    String ser_id;

    @SerializedName("service_name")
    String service_name;

    @SerializedName("services_image")
    String services_image;

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

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getServices_image() {
        return services_image;
    }

    public void setServices_image(String services_image) {
        this.services_image = services_image;
    }


    @Override
    public String toString() {
        return service_name ;
    }
}

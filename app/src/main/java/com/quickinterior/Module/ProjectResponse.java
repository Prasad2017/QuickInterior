package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

public class ProjectResponse {


    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("user_id")
    String userId;

    @SerializedName("project_name")
    String project_name;

    @SerializedName("project_id")
    String project_id;

    @SerializedName("client_id_fk")
    String client_id_fk;

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

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getClient_id_fk() {
        return client_id_fk;
    }

    public void setClient_id_fk(String client_id_fk) {
        this.client_id_fk = client_id_fk;
    }
}

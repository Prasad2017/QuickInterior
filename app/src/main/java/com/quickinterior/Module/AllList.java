package com.quickinterior.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllList {

    @SerializedName("success")
    private List<ProfileResponse> profileResponseList;

    @SerializedName("projectSuccess")
    private List<ProjectResponse> projectResponseList;

    @SerializedName("quatationSuccess")
    private List<QuatationResponse> quatationResponseList;

    @SerializedName("workCompleteSuccess")
    private List<CompleteWorkResponse> completeWorkResponses;

    @SerializedName("vendorSuccess")
    private List<VendorResponse> vendorResponseList;

    @SerializedName("availableStockSuccess")
    private List<AvailableStockResponse> availableStockResponses;

    @SerializedName("stockSuccess")
    private List<StockVerification> stockResopnse;

    @SerializedName("cateSuccess")
    private List<CategoryResopnse> categoryResopnses;

    @SerializedName("getSubType")
    private List<SubCategoryResopnse> subCategoryResopnses;

    @SerializedName("subCatSuccess")
    private List<MasterSubCatResopnse> masterSubCatResopnses;

    public List<ProfileResponse> getProfileResponseList() {
        return profileResponseList;
    }

    public void setProfileResponseList(List<ProfileResponse> profileResponseList) {
        this.profileResponseList = profileResponseList;
    }

    public List<ProjectResponse> getProjectResponseList() {
        return projectResponseList;
    }

    public void setProjectResponseList(List<ProjectResponse> projectResponseList) {
        this.projectResponseList = projectResponseList;
    }

    public List<QuatationResponse> getQuatationResponseList() {
        return quatationResponseList;
    }

    public void setQuatationResponseList(List<QuatationResponse> quatationResponseList) {
        this.quatationResponseList = quatationResponseList;
    }

    public List<CompleteWorkResponse> getCompleteWorkResponses() {
        return completeWorkResponses;
    }

    public void setCompleteWorkResponses(List<CompleteWorkResponse> completeWorkResponses) {
        this.completeWorkResponses = completeWorkResponses;
    }

    public List<VendorResponse> getVendorResponseList() {
        return vendorResponseList;
    }

    public void setVendorResponseList(List<VendorResponse> vendorResponseList) {
        this.vendorResponseList = vendorResponseList;
    }

    public List<AvailableStockResponse> getAvailableStockResponses() {
        return availableStockResponses;
    }

    public void setAvailableStockResponses(List<AvailableStockResponse> availableStockResponses) {
        this.availableStockResponses = availableStockResponses;
    }

    public List<StockVerification> getStockResopnse() {
        return stockResopnse;
    }

    public void setStockResopnse(List<StockVerification> stockResopnse) {
        this.stockResopnse = stockResopnse;
    }

    public List<CategoryResopnse> getCategoryResopnses() {
        return categoryResopnses;
    }

    public void setCategoryResopnses(List<CategoryResopnse> categoryResopnses) {
        this.categoryResopnses = categoryResopnses;
    }

    public List<SubCategoryResopnse> getSubCategoryResopnses() {
        return subCategoryResopnses;
    }

    public void setSubCategoryResopnses(List<SubCategoryResopnse> subCategoryResopnses) {
        this.subCategoryResopnses = subCategoryResopnses;
    }

    public List<MasterSubCatResopnse> getMasterSubCatResopnses() {
        return masterSubCatResopnses;
    }

    public void setMasterSubCatResopnses(List<MasterSubCatResopnse> masterSubCatResopnses) {
        this.masterSubCatResopnses = masterSubCatResopnses;
    }
}

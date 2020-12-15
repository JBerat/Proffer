package com.beratucgul.proffer.ApiModel;

import com.beratucgul.proffer.ApiData.SignUpData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public SignUpData data = null;
}

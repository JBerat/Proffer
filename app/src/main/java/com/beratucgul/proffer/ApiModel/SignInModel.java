package com.beratucgul.proffer.ApiModel;

import com.beratucgul.proffer.ApiData.SignInData;
import com.beratucgul.proffer.ApiData.SignUpData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public SignInData signInDataList = null;

}

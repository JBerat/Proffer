package com.beratucgul.proffer.ApiModel;

import com.beratucgul.proffer.ApiData.SignInFacebookData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInFacebookModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public SignInFacebookData loginData = null;

}

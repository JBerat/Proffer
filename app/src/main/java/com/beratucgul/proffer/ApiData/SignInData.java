package com.beratucgul.proffer.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInData {

    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;

    public SignInData(String email, String password) {
        this.email = email;
        this.password = password;
    }



}

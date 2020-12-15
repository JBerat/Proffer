package com.beratucgul.proffer.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInFacebookData {

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("picture")
    @Expose
    public String picture;

    @SerializedName("fb_id")
    @Expose
    public String fb_id;


    public SignInFacebookData(String name, String email, String picture, String fb_id) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.fb_id = fb_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }

    public String getFb_id() {
        return fb_id;
    }

}

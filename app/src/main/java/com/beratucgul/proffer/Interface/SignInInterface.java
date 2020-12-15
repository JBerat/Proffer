package com.beratucgul.proffer.Interface;

import com.beratucgul.proffer.ApiModel.SignInModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SignInInterface {

    @FormUrlEncoded
    @POST("users/login")
    Call<SignInModel> at(@Field("email") String signInEmail,
                         @Field("password") String signInPassword);

}

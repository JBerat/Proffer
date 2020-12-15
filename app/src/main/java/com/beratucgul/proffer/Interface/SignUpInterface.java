package com.beratucgul.proffer.Interface;

import com.beratucgul.proffer.ApiModel.SignUpModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SignUpInterface {

    @FormUrlEncoded
    @POST("users/register")
    Call<SignUpModel> at(@Field("email") String email,
                         @Field("password") String password,
                         @Field("name") String name);
}

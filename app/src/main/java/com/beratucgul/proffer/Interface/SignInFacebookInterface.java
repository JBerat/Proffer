package com.beratucgul.proffer.Interface;

import com.beratucgul.proffer.ApiModel.SignInFacebookModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SignInFacebookInterface {

    @FormUrlEncoded
    @POST("users/fb_login")
    Call<SignInFacebookModel> at(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("picture") String picture,
                                 @Field("fb_id") String fbId
    );

}

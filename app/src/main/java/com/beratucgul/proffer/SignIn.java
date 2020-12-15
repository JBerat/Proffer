package com.beratucgul.proffer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.beratucgul.proffer.ApiModel.SignInFacebookModel;
import com.beratucgul.proffer.ApiModel.SignInModel;
import com.beratucgul.proffer.Interface.SignInFacebookInterface;
import com.beratucgul.proffer.Interface.SignInInterface;
import com.beratucgul.proffer.Interface.SignUpInterface;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignIn extends AppCompatActivity {

    public final String BASE_URL = "https://proffer-api-scu.herokuapp.com";

    EditText emailSignInEditText, passwordSignInEditText;
    Button signInButton;
    TextView signUpText;
    private LoginButton facebooklogin;
    CallbackManager callbackManager;
    String name, imageURL, email, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailSignInEditText = findViewById(R.id.emailSignInEditText);
        passwordSignInEditText = findViewById(R.id.passwordSignInEditText);
        signInButton = findViewById(R.id.signInButton);
        signUpText = findViewById(R.id.SignUpText);

        facebooklogin = findViewById(R.id.login);
        callbackManager = CallbackManager.Factory.create();

        facebooklogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            //   private ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(LoginResult loginResult) {

                setFacebookConnection();

            /*    if(Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.v("facebook - profile", currentProfile.getFirstName());
                            username.setText(currentProfile.getName());
                            info.setText("User id : " + loginResult.getAccessToken().getUserId());

                            String imageURL = currentProfile.getProfilePictureUri(70,70).toString();
                            Picasso.get().load(imageURL).into(profile);


                            mProfileTracker.stopTracking();
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                }
                else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.v("facebook - profile", profile.getFirstName());
                }
            }

             */

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });






        String text ="Hesabın yok ise üye ol";

        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpan, 0,22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUpText.setText(ss);
        signUpText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void SignIn(View view) {

       /* Intent intent = new Intent(SignIn.this, HomePage.class);
        startActivity(intent);
        finish();

        */
        SignInPost();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void setFacebookConnection() {

        LoginManager.getInstance().logOut();


        List<String> permissionNeeds = Arrays.asList("public_profile, email");

        LoginManager.getInstance().logInWithReadPermissions(SignIn.this, permissionNeeds);

        FacebookSdk.sdkInitialize(SignIn.this);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        //  Log.d("res", object.toString());
                        // Log.d("res_obj", response.toString());
                        try {

                            id = object.getString("id");
                            try {
                                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150&redirect=0");
                                //  Log.i("profile_pic", profile_pic + "");

                                String f_name = object.getString("first_name");
                                String l_name = object.getString("last_name");
                                name = f_name + " " + l_name;
                                email = object.getString("email");
                                //     String image = profile_pic.toString();


                                Profile currentProfile = Profile.getCurrentProfile();

                                imageURL = currentProfile.getProfilePictureUri(70, 70).toString();
                                //   Picasso.get().load(imageURL).into(profile);


                                Log.d("datadeneme", email + " " + name + " " + imageURL + " " + id);
                                String type = "facebook";

                                //   info.setText(name);
                                //  username.setText(email);
                                //  Picasso.get().load(image).into(profile);

                                if (email == null) {

                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS)
                                .retryOnConnectionFailure(true)
                                .build();


                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                                .client(okHttpClient)
                                .build();

                        SignInFacebookInterface signInFacebookInterface = retrofit.create(SignInFacebookInterface.class);

                        Call<SignInFacebookModel> call = signInFacebookInterface.at(name, email, imageURL, id);

                        //  Call<LoginModel> call = loginInterface.at(name, email);

                        call.enqueue(new Callback<SignInFacebookModel>() {
                            @Override
                            public void onResponse(Call<SignInFacebookModel> call, Response<SignInFacebookModel> response) {

                                if(response.body().status.equals("success")) {


                                    Log.d("DENEME", response.body().status);

                                    Intent intent = new Intent(SignIn.this, HomePage.class);
                                    startActivity(intent);


                                }
                            }

                            @Override
                            public void onFailure(Call<SignInFacebookModel> call, Throwable t) {

                                Log.d("DENEME1", t.getMessage());

                            }
                        });
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

                Log.d("fb_exception", "cancel by user");
            }

            @Override
            public void onError(FacebookException exception) {

                Log.d("fb_exception", exception.toString());

            }
        });

    }
    public void SignInPost() {

     /*  OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + userToken)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

      */

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SignInInterface signInInterface = retrofit.create(SignInInterface.class);
        Call<SignInModel> call = signInInterface.at(emailSignInEditText.getText().toString(), passwordSignInEditText.getText().toString());

        call.enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {

                if(response.body().status.equals("success")){

                    Intent intent = new Intent(SignIn.this, HomePage.class);
                    startActivity(intent);

                    Log.d(" Kullanıcı Girişi Başarılı ",response.message());

                }
                else{
                    Log.d(" Kullanıcı Girişi Başarısız ", response.message());
                }

            }

            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {
                Log.d(" Kullanıcı Girişi Başarısız ve fail ", t.getLocalizedMessage());

            }
        });


    }



}
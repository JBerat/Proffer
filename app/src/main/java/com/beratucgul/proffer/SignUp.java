package com.beratucgul.proffer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.beratucgul.proffer.ApiModel.SignUpModel;
import com.beratucgul.proffer.Interface.SignUpInterface;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    EditText usernameEditText, emailEditText, passwordEditText;
    Button signUpButton;
    TextView signInText;
    TextInputLayout text_input_email, text_input_username, text_input_password;
    AppCompatCheckBox checkBox;

    public final String BASE_URL = "https://proffer-api-scu.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

     //   usernameEditText = findViewById(R.id.usernameEditText);
      //  emailEditText = findViewById(R.id.emailEditText);
       // passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        signInText = findViewById(R.id.signInText);

        checkBox = findViewById(R.id.checkbox);



        text_input_email = findViewById(R.id.text_input_email);
        text_input_username = findViewById(R.id.text_input_username);
        text_input_password = findViewById(R.id.text_input_password);


      /*  checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

       */





        String text = "Hesabın var mı ? Giriş Yap!";

        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpan, 0,27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signInText.setText(ss);
        signInText.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public void BackToSignIn(View view) {

        signUpPost();

        Intent intent = new Intent(SignUp.this, SignIn.class);
        startActivity(intent);
    }



    public void signUpPost() {

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
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        SignUpInterface signUpInterface = retrofit.create(SignUpInterface.class);
        Call<SignUpModel> call =signUpInterface.at(text_input_email.getEditText().getText().toString(), text_input_password.getEditText().getText().toString(),
                text_input_username.getEditText().getText().toString());

        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                Log.d(" User Kayıt Edildi", response.message());
                Toast.makeText(SignUp.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                Toast.makeText(SignUp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Uset Kayıt Fail", t.getMessage());


            }
        });

    }

    private boolean validateEmail() {
        String emailInput = text_input_email.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {
            text_input_email.setError("Field can't be empty");
            return false;
        } else{
            text_input_email.setError(null);
            return true;
        }
    }
    private boolean validateUsername() {
        String username = text_input_username.getEditText().getText().toString().trim();
        if(username.isEmpty()){
            text_input_username.setError("Field can't be empty");
            return false;
        } else if(username.length() > 15) {
            text_input_username.setError("Username too long");
            return false;
        } else {
            text_input_username.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String emailInput = text_input_password.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {
            text_input_password.setError("Field can't be empty");
            return false;
        } else{
            text_input_password.setError(null);
            return true;
        }
    }


    public void confirmInput(View v) {

        if(!validateEmail() | !validateUsername() | validatePassword()) {
            return;
        }

        String input = "Email: " + text_input_email.getEditText().getText().toString();
        input += "\n";
        input = "Username: " + text_input_username.getEditText().getText().toString();
        input += "\n";
        input = "Password: " + text_input_password.getEditText().getText().toString();







    }

}
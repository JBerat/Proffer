package com.beratucgul.proffer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class FirmSignIn extends AppCompatActivity {

    TextInputLayout text_input_firm_email, text_input_firm_password;
    Button signInFirmButton;
    TextView signUpFirmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm_sign_in);

        text_input_firm_email = findViewById(R.id.text_input_firm_email);
        text_input_firm_password = findViewById(R.id.text_input_firm_password);
        signInFirmButton = findViewById(R.id.signInFirmButton);
        signUpFirmText = findViewById(R.id.signUpFirmText);

        String text ="Hesabın yok ise üye ol";

        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(FirmSignIn.this, FirmSignUp.class);
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpan, 0,22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUpFirmText.setText(ss);
        signUpFirmText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void SignIn(View view) {
    }
}
package com.example.collagemanegment.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collagemanegment.R;
import com.hbb20.CountryCodePicker;

public class SignUpActivity extends AppCompatActivity {
    private EditText phoneNumber,password,name;
    private Button signup;
    private ImageView signupBack;
    private CountryCodePicker ccp;
    private TextView TitleText,loginAc;
    String sName,sPhoneNo,sPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        phoneNumber = findViewById(R.id.signUpPhoneNo);
        TitleText = findViewById(R.id.TextviewTitleText);
        password = findViewById(R.id.signUpPassword);
        name = findViewById(R.id.signUpFullName);
        ccp = findViewById(R.id.ccp);
        loginAc = findViewById(R.id.loginActivity);
        signup = findViewById(R.id.signupBtn);
        signupBack = findViewById(R.id.signUpBack);
       signupBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),LoginActivity.class));
               finish();
           }
       });
      loginAc.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(getApplicationContext(),LoginActivity.class));
          }
      });


    }

    public void VerifyOtp(View view){
        String s_userPhoneNo=phoneNumber.getText().toString().trim();
        sPhoneNo = ccp.getSelectedCountryCodeWithPlus()+s_userPhoneNo;
        sName = name.getText().toString().trim();
        sPassword = password.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(),VerifyOtpActivity.class);
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View,String>(signupBack,"backBtn");
        pairs[1] = new Pair<View,String>(TitleText,"titleText");
        pairs[2] = new Pair<View,String>(signup,"btn");
        pairs[3] = new Pair<View,String>(name,"editText");

        intent.putExtra("Name",sName);
        intent.putExtra("Password",sPassword);
        intent.putExtra("PhoneNo",sPhoneNo);
<<<<<<< HEAD
        intent.putExtra("status","no");
=======
>>>>>>> origin/main

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this,pairs);
        startActivity(intent,options.toBundle());

    }
}
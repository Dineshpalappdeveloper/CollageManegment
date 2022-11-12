package com.example.collagemanegment.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collagemanegment.MainActivity;
import com.example.collagemanegment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneNumber, password;
    private Button loginBtn;
    private  ProgressDialog pd;
    private ImageView loginBack;
    private TextView forget, createNewAc;
    private CountryCodePicker ccp;
    String sPassword,sPhoneNo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pd = new ProgressDialog(this);
        phoneNumber = findViewById(R.id.loginPhoneNo);
        loginBtn = findViewById(R.id.loginBtn);
        forget = findViewById(R.id.loginForgot);
        ccp = findViewById(R.id.loginCcp);
        forget =findViewById(R.id.loginForgot);
        createNewAc = findViewById(R.id.newAccount);
        password = findViewById(R.id.loginPassword);
        loginBack = findViewById(R.id.loginBack);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });
        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPassword = password.getText().toString();
                sPhoneNo = phoneNumber.getText().toString();

                if (sPhoneNo.isEmpty()){
                    phoneNumber.setText("Empty");
                    phoneNumber.requestFocus();
                }else if (sPassword.isEmpty()){
                    password.setError("Empty");
                    password.requestFocus();
                }else {
                    pd.setMessage("Please Wait");
                    pd.show();
                    login();
                }
            }
        });
        createNewAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

    }
    private void login() {
       sPassword = password.getText().toString().trim();
       sPhoneNo = phoneNumber.getText().toString().trim();

       String sUserPhoneNo = ccp.getSelectedCountryCodeWithPlus()+sPhoneNo;
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(sUserPhoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phoneNumber.setError(null);
                    String _sysPassword = snapshot.child(sUserPhoneNo).child("password").getValue(String.class);
                    if (Objects.equals(_sysPassword,sPassword)){
                        password.setError(null);

                        SharedPreferences pref = getSharedPreferences("Login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putBoolean("flag",true);
                        editor.apply();

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        pd.dismiss();
                    }else {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, "Incorrect Password ", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "No Such User exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
package com.example.collagemanegment.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.collagemanegment.MainActivity;
import com.example.collagemanegment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class ForgotOtpActivity extends AppCompatActivity {
    private TextView userPhoneNo;
    private Button forgotVerifyBtn;
    private ImageView forgotBack;
    private PinView pinView;
    String Name, PhoneNo, Password, codeBySystem;
    private FirebaseAuth mAuth;
    ProgressDialog pd;

    String sPassword1;
    String sPassword2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_otp);

        pinView = findViewById(R.id.forgotPasswordPinView);
        forgotBack = findViewById(R.id.forgotVerifyBack);
        forgotVerifyBtn = findViewById(R.id.forgotVerifyBtn);
        userPhoneNo = findViewById(R.id.forgotVerifyNumber);
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait");
          PhoneNo = getIntent().getStringExtra("phoneNo");

        forgotBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
                finish();
            }
        });
        sendVerificationToUser(PhoneNo);
        forgotVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(pinView.getText().toString());
                UpdatePassword();
               // startActivity(new Intent(getApplicationContext(), UpdatePasswordActivity.class));
                finish();
            }
        });
    }
    private void sendVerificationToUser(String phoneNo) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(PhoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeBySystem = s;
            Toast.makeText(ForgotOtpActivity.this, "otp send", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code !=null){
                pinView.setText(code);
                verifyCode(code);
                UpdatePassword();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(ForgotOtpActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential =PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredentail(credential);
    }

    private void signInWithPhoneAuthCredentail(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UpdatePassword();
                            pd.dismiss();
                           // startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            pd.dismiss();
                            Toast.makeText(ForgotOtpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void UpdatePassword(){
        Intent intent = new Intent(getApplicationContext(),UpdatePasswordActivity.class);
        intent.putExtra("phoneNo",PhoneNo);
        pd.dismiss();
        startActivity(intent);
    }


}
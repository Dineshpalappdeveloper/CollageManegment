package com.example.collagemanegment.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends AppCompatActivity {
     Button verifyOtp;
     EditText  otp;
     TextView userPhoneNo;
     ImageView VerifyBack;
     PinView pinView;
    String Name,PhoneNo,Password,codeBySystem;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        VerifyBack = findViewById(R.id.verifyBack);
        Name = getIntent().getStringExtra("Name");
        Password = getIntent().getStringExtra("Password");
        PhoneNo = getIntent().getStringExtra("PhoneNo");

        userPhoneNo = findViewById(R.id.signUpPhoneNo);
//        userPhoneNo.setText(PhoneNo);
        mAuth = FirebaseAuth.getInstance();
       VerifyBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
               finish();
           }
       });
        sendVerificationToUser(PhoneNo);
    }
     public void Verify(View view){
        verifyCode(pinView.getText().toString());
        storeNewUserData();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
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
             Toast.makeText(VerifyOtpActivity.this, "otp send", Toast.LENGTH_SHORT).show();
         }

         @Override
         public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
             String code = phoneAuthCredential.getSmsCode();
             if (code !=null){
               pinView.setText(code);
                 verifyCode(code);
                 storeNewUserData();
                 startActivity(new Intent(getApplicationContext(),MainActivity.class));
                 finish();
             }

         }

         @Override
         public void onVerificationFailed(@NonNull FirebaseException e) {
             Toast.makeText(VerifyOtpActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
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
                            storeNewUserData();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(VerifyOtpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void storeNewUserData() {
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference("Users");

        AddNewUser addNewUser = new AddNewUser(Name,PhoneNo,Password);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reference.child(PhoneNo).setValue(addNewUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VerifyOtpActivity.this, "Failed New User Data Saved", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
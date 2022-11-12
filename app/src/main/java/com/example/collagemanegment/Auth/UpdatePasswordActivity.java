package com.example.collagemanegment.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.collagemanegment.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class UpdatePasswordActivity extends AppCompatActivity {
    private EditText Password1,Password2;
    private Button submitBtn;
    private ProgressDialog pd;
    private ImageView forgotOtpBack;
    String sPassword1;
    String sPassword2;
    String sPhoneNo,sPassword;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        Password1 = findViewById(R.id.newPassword1);
        Password2 = findViewById(R.id.newPassword2);
        forgotOtpBack = findViewById(R.id.forgotOtpBack);
        submitBtn = findViewById(R.id.UpdatePasswordBtn);
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait");

        sPhoneNo = getIntent().getStringExtra("phoneNo");
        sPassword2 = Password1.getText().toString().trim();
        forgotOtpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
                finish();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPassword1 = Password1.getText().toString().trim();
                sPassword2 = Password2.getText().toString().trim();

                if (sPassword1!=sPassword2 && sPassword2.isEmpty()){
                    Toast.makeText(UpdatePasswordActivity.this, "Confirm Password not matching", Toast.LENGTH_SHORT).show();
                    Password2.requestFocus();
                }else{
                    pd.show();
                    PasswordUpdate();
                }
            }
        });

    }

    private void PasswordUpdate() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(sPhoneNo).child("password").setValue(sPassword1);
        pd.dismiss();
        Toast.makeText(this, "Password Updated", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}
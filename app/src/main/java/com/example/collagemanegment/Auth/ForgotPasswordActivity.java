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

import com.example.collagemanegment.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText phoneNumber;
    private Button forgotBtn;
    private ProgressDialog pd;
    private ImageView forgotBack;
    private CountryCodePicker ccp;
    String sPhoneNo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        phoneNumber = findViewById(R.id.forgotPhoneNo);
        forgotBtn = findViewById(R.id.forgotBtn);
        forgotBack = findViewById(R.id.forgotBack);
        ccp = findViewById(R.id.forgotCcp);
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait");


        forgotBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 sPhoneNo = phoneNumber.getText().toString().trim();
                if (sPhoneNo.isEmpty()){
                    phoneNumber.setError("Empty");
                    phoneNumber.requestFocus();
                }else {
                    pd.show();
                    forgotPassword();
                }
            }
        });

    }

    private void forgotPassword() {
      String sUserPhone = phoneNumber.getText().toString().trim();
      String sPhoneNumber = ccp.getSelectedCountryCodeWithPlus()+sUserPhone;

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(sPhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phoneNumber.setError(null);

                    Intent intent = new Intent(getApplicationContext(),ForgotOtpActivity.class);
                    intent.putExtra("phoneNo",sPhoneNumber);
                    pd.dismiss();
                    startActivity(intent);
                }else {
                    pd.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, "User Does not exits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
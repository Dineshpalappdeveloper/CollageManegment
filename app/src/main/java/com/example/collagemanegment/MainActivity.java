package com.example.collagemanegment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.collagemanegment.Auth.LoginActivity;
import com.example.collagemanegment.Notice.DeleteNoticeActivity;
import com.example.collagemanegment.Notice.UploadNoticeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView UploadNotice;
    CardView UploadImage;
    CardView UploadFaculty;
    CardView UploadEbook;
    TextView logout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    CardView DeleteNotice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UploadNotice = findViewById(R.id.UploadNotice);
        UploadEbook = findViewById(R.id.UplaodEbook);
        logout = findViewById(R.id.logout);
        UploadImage = findViewById(R.id.UploadImage);
        UploadFaculty = findViewById(R.id.UploadFaculty);
        DeleteNotice = findViewById(R.id.DeleteNotice);


        UploadNotice.setOnClickListener(this);
        UploadEbook.setOnClickListener(this);
        UploadImage.setOnClickListener(this);
        UploadFaculty.setOnClickListener(this);
        DeleteNotice.setOnClickListener(this);
        sharedPreferences = this.getSharedPreferences("login",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getString("isLogin","false").equals("yes")){
           openLogin();
        }

    }

    private void openLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }

    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.UploadNotice:
              Intent intent = new Intent(MainActivity.this, UploadNoticeActivity.class);
              startActivity(intent);
              finish();
              break;
          case R.id.UplaodEbook:
              Intent intent1 = new Intent(MainActivity.this,UploadEbookActivity.class);
              startActivity(intent1);
              finish();
              break;
          case R.id.UploadImage:
              Intent intent2 = new Intent(MainActivity.this,UploadImageActivity.class);
              startActivity(intent2);
              finish();
              break;
          case R.id.UploadFaculty:
              Intent intent3 = new Intent(MainActivity.this,UploadFacultyActivity.class);
              startActivity(intent3);
              finish();
              break;
          case R.id.DeleteNotice:
              Intent intent4 = new Intent(MainActivity.this, DeleteNoticeActivity.class);
              startActivity(intent4);
              finish();
              break;
          case R.id.logout:
              editor.putString("isLogin","false");
              editor.commit();
            openLogin();
            break;
      }
    }
}
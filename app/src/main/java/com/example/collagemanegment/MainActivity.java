package com.example.collagemanegment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collagemanegment.Auth.LoginActivity;
import com.example.collagemanegment.Notice.DeleteNoticeActivity;
import com.example.collagemanegment.Notice.UploadNoticeActivity;
import com.example.collagemanegment.Syllabus.UploadSyllabusActivity;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView UploadNotice;
    CardView UploadImage;
    CardView UploadFaculty;
    CardView UploadEbook;
    CardView UploadSyllabus;
    TextView logout1;
    Button send;
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
        logout1 = findViewById(R.id.loguut1);
        UploadImage = findViewById(R.id.UploadImage);
        UploadFaculty = findViewById(R.id.UploadFaculty);
        DeleteNotice = findViewById(R.id.DeleteNotice);
        UploadSyllabus = findViewById(R.id.UploadSyllabus1);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SendNotifacionActivity.class));
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("new Notification ");
        UploadNotice.setOnClickListener(this);
        UploadEbook.setOnClickListener(this);
        UploadImage.setOnClickListener(this);
        UploadFaculty.setOnClickListener(this);
        DeleteNotice.setOnClickListener(this);
        UploadSyllabus.setOnClickListener(this);

      //  logout1.setOnClickListener(this);


    }

    private void openLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();

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
          case R.id.UploadSyllabus1:
                 startActivity(new Intent(getApplicationContext(), UploadSyllabusActivity.class));
                 finish();
                 break;

      }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()){
          case R.id.loguut1:


              SharedPreferences pref = getSharedPreferences("Login",MODE_PRIVATE);
              SharedPreferences.Editor editor = pref.edit();

              editor.putBoolean("flag",false);
              editor.apply();
              openLogin();
              break;

      }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
}
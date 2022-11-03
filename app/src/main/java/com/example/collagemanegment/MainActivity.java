package com.example.collagemanegment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.collagemanegment.Notice.DeleteNoticeActivity;
import com.example.collagemanegment.Notice.UploadNoticeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView UploadNotice;
    CardView UploadImage;
    CardView UploadFaculty;
    CardView UploadEbook;
    CardView DeleteNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UploadNotice = findViewById(R.id.UploadNotice);
        UploadEbook = findViewById(R.id.UplaodEbook);
        UploadImage = findViewById(R.id.UploadImage);
        UploadFaculty = findViewById(R.id.UploadFaculty);
        DeleteNotice = findViewById(R.id.DeleteNotice);


        UploadNotice.setOnClickListener(this);
        UploadEbook.setOnClickListener(this);
        UploadImage.setOnClickListener(this);
        UploadFaculty.setOnClickListener(this);
        DeleteNotice.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.UploadNotice:
              Intent intent = new Intent(MainActivity.this, UploadNoticeActivity.class);
              startActivity(intent);
              break;
          case R.id.UplaodEbook:
              Intent intent1 = new Intent(MainActivity.this,UploadEbookActivity.class);
              startActivity(intent1);
              break;
          case R.id.UploadImage:
              Intent intent2 = new Intent(MainActivity.this,UploadImageActivity.class);
              startActivity(intent2);
              break;
          case R.id.UploadFaculty:
              Intent intent3 = new Intent(MainActivity.this,UploadFacultyActivity.class);
              startActivity(intent3);
              break;
          case R.id.DeleteNotice:
              Intent intent4 = new Intent(MainActivity.this, DeleteNoticeActivity.class);
              startActivity(intent4);
              break;

      }
    }
}
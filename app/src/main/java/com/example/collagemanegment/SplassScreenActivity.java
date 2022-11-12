package com.example.collagemanegment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collagemanegment.Auth.LoginActivity;

public class SplassScreenActivity extends AppCompatActivity {
    ImageView logo;
    TextView name;
    Animation fade,slide;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splass_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        name = findViewById(R.id.CollageName);
        logo = findViewById(R.id.CollageLogo);
        fade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        slide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in);

        logo.setAnimation(fade);
        name.setAnimation(slide);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = getSharedPreferences("Login",MODE_PRIVATE);
                boolean check = pref.getBoolean("flag",false);

                if (check){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }else {

                    startActivity(new Intent(SplassScreenActivity.this, LoginActivity.class));
                    finish();
                }



            }
        },2000);




    }
}
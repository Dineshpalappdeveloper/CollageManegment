package com.example.collagemanegment;

import static com.example.collagemanegment.ModelNo.Constants.TOPIC;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collagemanegment.ModelNo.NotificationData;
import com.example.collagemanegment.ModelNo.PushNotification;
import com.example.collagemanegment.api.ApiUtiliyies;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotifacionActivity extends AppCompatActivity {
     private  EditText title,message;
     private Button send;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notifacion);

         FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);


        title = findViewById(R.id.Titleno);
        message = findViewById(R.id.Mesasageno);
        send = findViewById(R.id.sendNo);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stitle = title.getText().toString();
                String smessage = message.getText().toString();
               if (!stitle.isEmpty() && !smessage.isEmpty()){
                   PushNotification notification = new PushNotification(new NotificationData(stitle,smessage),TOPIC);
                   sendNotifiaction(notification);
               }
            }
        });
    }

    private void sendNotifiaction(PushNotification notification) {

        ApiUtiliyies.getClint().sendNotification(notification).enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SendNotifacionActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SendNotifacionActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(SendNotifacionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
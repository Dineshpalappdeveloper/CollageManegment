package com.example.collagemanegment.api;

import static com.example.collagemanegment.ModelNo.Constants.CONTENT_TYPE;
import static com.example.collagemanegment.ModelNo.Constants.SERVER_KEY;

import com.example.collagemanegment.ModelNo.PushNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers({"Authorization: key"+SERVER_KEY,"Content-Type:"+CONTENT_TYPE})
    @POST("fcm/send")
    Call<PushNotification> sendNotification (@Body PushNotification notification);

}

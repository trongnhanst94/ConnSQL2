package com.example.windows10gamer.connsql.Other;

import com.example.windows10gamer.connsql.Object.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by EVRESTnhan on 1/4/2018.
 */

public interface FirebaseAPI {

    @POST("/fcm/send")
    Call<Message> sendMessage(@Body Message message);

}

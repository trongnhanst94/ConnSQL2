package com.example.windows10gamer.connsql.Other;

/**
 * Created by EVRESTnhan on 2/22/2018.
 */

import com.example.windows10gamer.connsql.Other.notifications.NotificationData;
import com.example.windows10gamer.connsql.Other.notifications.NotificationRequestModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;


public class ApplicationClass {


    public static void main(String[] args) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost("https://fcm.googleapis.com/fcm/send");
        NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
        NotificationData notificationData = new NotificationData();

        notificationData.setDetail("this is firebase push notification from java client (server)");
        notificationData.setTitle("Hello Firebase Push Notification");
        notificationRequestModel.setData(notificationData);
        notificationRequestModel.setTo("/topics/all");


        Gson gson = new Gson();
        Type type = new TypeToken<NotificationRequestModel>() {
        }.getType();

        String json = gson.toJson(notificationRequestModel, type);

        StringEntity input = new StringEntity(json);
        input.setContentType("application/json");

        postRequest.addHeader("Authorization", "key=AAAAvlkWcQA:APA91bEqA621By39e_NeaScI3cuBiQe9ZPaxCqzQiY0cq5Ysvot9vEZIJ-HeI9n-a9JjO-gR8q9QDVYQzV9xE6d-6iB6k9E6po2dZiYl46rKIEXBNfk72wupRulJdCrCTaAphnWh_B2n");
        postRequest.setEntity(input);

        System.out.println("request:" + json);

        HttpResponse response = httpClient.execute(postRequest);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        } else if (response.getStatusLine().getStatusCode() == 200) {

            System.out.println("response:" + EntityUtils.toString(response.getEntity()));

        }
    }
}
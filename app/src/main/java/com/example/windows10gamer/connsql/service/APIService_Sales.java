package com.example.windows10gamer.connsql.service;

import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.OrderList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by EVRESTnhan on 10/8/2017.
 */


public interface APIService_Sales {
    @GET(Keys.USER_CONTENT_KEY)
    Call<OrderList> getAllProduct();
}

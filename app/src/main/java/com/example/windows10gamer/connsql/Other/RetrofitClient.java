package com.example.windows10gamer.connsql.Other;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by EVRESTnhan on 10/8/2017.
 */

public class RetrofitClient {

    private static final String RETROFIT_ORDER_URL = Keys.LINK_SERVER;

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(RETROFIT_ORDER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static APIService_Sales getApiService() {
        return getRetrofitInstance().create(APIService_Sales.class);
    }

    private static Retrofit getRetrofitInstance_kho() {
        return new Retrofit.Builder()
                .baseUrl(RETROFIT_ORDER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static APIService_Kho getApiService_kho() {
        return getRetrofitInstance_kho().create(APIService_Kho.class);
    }
}
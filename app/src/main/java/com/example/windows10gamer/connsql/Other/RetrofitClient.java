package com.example.windows10gamer.connsql.Other;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by EVRESTnhan on 10/8/2017.
 */

public class RetrofitClient {

    /********
     * URLS
     *******/
    private static final String ROOT_URL = Keys.RETROFIT_ORDER;

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static APIService_Sales getApiService() {
        return getRetrofitInstance().create(APIService_Sales.class);
    }
}
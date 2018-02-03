package com.example.windows10gamer.connsql.Other;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by EVRESTnhan on 1/6/2018.
 */

public interface APIService_Kho {
    @GET(Keys.USER_KHO_KEY)
    Call<KhoList> getAllProduct_kho();
}

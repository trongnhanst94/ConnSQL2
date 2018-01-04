package com.example.windows10gamer.connsql.Other;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by EVRESTnhan on 10/8/2017.
 */


public interface APIService_Sales {
    @GET(Keys.USER_CONTENT_KEY)
    Call<OrderList> getAllProduct();

    @PUT("baohanh_TenvaMa.php?")
    Call<OrderList> getTenvaMa(@Query("tenKhachhang") String tenKhachhang, @Query("maSanpham") String maSanpham);

    @PUT("danhsach_realtime_order.php?")
    Call<OrderList> getRealtime_Order(@Query("chinhanh") String chinhanh, @Query("ngay") String ngay);

    @PUT("danhsach_realtime_order.php?")
    Call<OrderList> getDoanhthu(@Query("chinhanh") String chinhanh, @Query("ngay") String ngay, @Query("calam") String calam);

    @PUT("main_sales.php?")
    Call<OrderList> getOrder(@Query("loadBegin")  String loadBegin,  @Query("loadEnd") String loadEnd);
}

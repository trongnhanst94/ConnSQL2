package com.example.windows10gamer.connsql.Other;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by EVRESTnhan on 10/8/2017.
 */


public interface APIService_Sales {
    @GET(Keys.USER_SALES_KEY)
    Call<OrderList> getAllProduct();

    @PUT("baohanh_TenvaMa.php?")
    Call<OrderList> getTenvaMa(@Query("tenKhachhang") String tenKhachhang, @Query("maSanpham") String maSanpham);

    @PUT("danhsach_realtime_order.php?")
    Call<OrderList> getRealtime_Order(@Query("chinhanh") String chinhanh, @Query("ngay") String ngay);

    @PUT("danhsach_realtime_order.php?")
    Call<OrderList> getDoanhthu(@Query("chinhanh") String chinhanh, @Query("ngay") String ngay, @Query("calam") String calam);

    @PUT(Keys.USER_SALES_KEY+"?")
    Call<OrderList> getOrder(@Query("loadBegin")  String loadBegin,  @Query("loadEnd") String loadEnd);

    @PUT(Keys.USER_SALES_KEY+"?")
    Call<OrderList> getBaohanhKo(@Query("masanpham")  String masanpham, @Query("tacvu")  String tacvu);

    @PUT(Keys.USER_SALES_KEY+"?")
    Call<OrderList> getBaohanhCo(@Query("loadBegin")  String loadBegin,  @Query("loadEnd") String loadEnd, @Query("masanpham")  String masanpham, @Query("tacvu")  String tacvu);

    @PUT(Keys.USER_SALES_KEY+"?")
    Call<OrderList> getLoadJsonScan(@Query("tonghop")  String tonghop);
}

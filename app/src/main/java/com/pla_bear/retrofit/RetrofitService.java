package com.pla_bear.retrofit;

import com.pla_bear.coupon.Coupon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("/api/coupon")
    Call<Object> registerOwner (@FieldMap HashMap<String, String> param);

    @GET("/api/coupon/{uid}")
    Call<List<Coupon>> getCoupon(@Path("uid") String uid);
}

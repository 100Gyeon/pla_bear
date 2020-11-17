package com.pla_bear.retrofit;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.pla_bear.coupon.CouponDTO;
import com.pla_bear.graph.GraphDTOContainer;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
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
    Call<List<CouponDTO>> getCoupon(@Path("uid") String uid);

    @DELETE("/api/coupon/{name}")
    Call<Void> deleteCoupon(@Path("name") String name);

    // recycling-info.or.kr
    @GET("/sds/JsonApi.do")
    Call<GraphDTOContainer> getInfo(@Query("PID") String pid, @Query("YEAR") int year, @Query("USRID") String userId, @Query("KEY") String key);
}

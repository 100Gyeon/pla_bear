package com.pla_bear.coupon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;
import com.pla_bear.retrofit.RetrofitClient;
import com.pla_bear.retrofit.RetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponMainActivity extends BaseActivity {
    private final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private RetrofitService service;
    private List<CouponDTO> couponList;
    static public Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_main);

        mContext = this;
        service = RetrofitClient.getApiService(getString(R.string.api_base));

        Button button = findViewById(R.id.btn_coupon_register);
        button.setOnClickListener(new RegisterCoupon());
    }

    private void addCoupon(final CouponDTO coupon) {
        LinearLayout rootLinear = findViewById(R.id.coupon_list_root);

        CouponView couponView = new CouponView(this);
        couponView.setPrice(coupon.getPrice());
        couponView.setDisposition();
        couponView.setBackground(getResources().getDrawable(R.color.colorCoupon));

        couponView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CouponDetailActivity.class);
            intent.putExtra("coupon", coupon);
            startActivity(intent);
        });

        float couponMargin = (int)getResources().getDimension(R.dimen.coupon_margin);
        LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewLp.setMargins((int)couponMargin, (int)couponMargin, (int)couponMargin, 0);
        rootLinear.addView(couponView, textViewLp);
    }

    private void loadCoupon() {
        for(CouponDTO coupon : this.couponList) {
            addCoupon(coupon);
        }
    }

    private void getCoupon() {
        Call<List<CouponDTO>> call = service.getCoupon(this.uid);
        call.enqueue(new Callback<List<CouponDTO>>() {
            @Override
            public void onResponse(@NotNull Call<List<CouponDTO>> call, @NotNull Response<List<CouponDTO>> response) {
                if(response.isSuccessful()) {
                    CouponMainActivity.this.couponList = response.body();
                    CouponMainActivity.this.loadCoupon();
                }
            }

            @Override
            public void onFailure(Call<List<CouponDTO>> call, Throwable t) {
                Log.e("Retrofit2", "GetCoupon Failed." + t.getMessage());
            }
        });
    }

    private class RegisterCoupon implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EditText editText = findViewById(R.id.edit_coupon_num);
            Editable value = editText.getText();

            HashMap<String, String> body = new HashMap<>();
            body.put("uid", uid);
            body.put("name", value.toString());

            Call<Object> call = service.registerOwner(body);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    int affectedRows = 0;

                    if(response.isSuccessful()) {
                        String rawJson = new Gson().toJson(response.body());
                        JSONObject json;
                        try {
                            json = new JSONObject(rawJson);
                            affectedRows = json.getInt(getString(R.string.affected_rows));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(CouponMainActivity.this);
                        builder.setTitle("알림");

                        if(affectedRows == 0) {
                            builder.setIcon(android.R.drawable.ic_delete);
                            builder.setMessage(R.string.coupon_register_fail);
                            builder.setNegativeButton("OK", null);
                        } else {
                            builder.setMessage(R.string.coupon_register_success);
                            builder.setPositiveButton("OK", (dialogInterface, i) -> CouponMainActivity.this.renewCoupon());
                        }

                        builder.create().show();
                    } else {
                        Log.e("Retrofit2", "Request Failed.");
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.e("Retrofit2", "RegisterOwner Failed." + t.getMessage());
                }
            });
        }
    }

    private void renewCoupon() {
        LinearLayout couponList = findViewById(R.id.coupon_list_root);
        couponList.removeAllViews();
        CouponMainActivity.this.getCoupon();
    }

    @Override
    public void onResume() {
        super.onResume();
        renewCoupon();
    }
}
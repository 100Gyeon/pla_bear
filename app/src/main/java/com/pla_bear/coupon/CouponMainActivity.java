package com.pla_bear.coupon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.pla_bear.R;
import com.pla_bear.retrofit.RetrofitClient;
import com.pla_bear.retrofit.RetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponMainActivity extends AppCompatActivity {

    private String uid = "testuid";
    private RetrofitService service;
    private List<Coupon> couponList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_main);

        service = RetrofitClient.getApiService(getString(R.string.api_base));

        Button button = findViewById(R.id.btn_coupon_register);
        button.setOnClickListener(new RegisterCoupon());
        getCoupon();
    }

    private void addCoupon(final Coupon coupon) {
        LinearLayout rootLinear = findViewById(R.id.coupon_list_root);

        int couponMargin = (int)getResources().getDimension(R.dimen.coupon_margin);
        int couponPadding = (int)getResources().getDimension(R.dimen.coupon_padding);
        TextView textView = new TextView(this);

        NumberFormat formatter = new DecimalFormat("#,###");
        textView.setText(formatter.format(coupon.getPrice()) + " 원 할인");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 42);
        textView.setTextColor(getResources().getColor(R.color.colorCouponText));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        textView.setPadding(0, couponPadding, 0, couponPadding);
        textView.setBackground(getResources().getDrawable(R.color.colorCoupon));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CouponDetailActivity.class);
                intent.putExtra("coupon", coupon);
                startActivity(intent);
            }
        });

        LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewLp.leftMargin = couponMargin;
        textViewLp.topMargin = couponMargin;
        textViewLp.rightMargin = couponMargin;
        rootLinear.addView(textView, textViewLp);
    }

    private void loadCoupon() {
        for(Coupon coupon : this.couponList) {
            addCoupon(coupon);
        }
    }

    private void getCoupon() {
        Call<List<Coupon>> call = service.getCoupon(this.uid);
        call.enqueue(new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
                if(response.isSuccessful()) {
                    CouponMainActivity.this.couponList = response.body();
                    CouponMainActivity.this.loadCoupon();
                }
            }

            @Override
            public void onFailure(Call<List<Coupon>> call, Throwable t) {
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
                        JSONObject json = null;
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
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    LinearLayout couponList = findViewById(R.id.coupon_list_root);
                                    couponList.removeAllViews();
                                    CouponMainActivity.this.getCoupon();
                                }
                            });
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
}
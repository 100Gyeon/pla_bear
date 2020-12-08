package com.pla_bear.coupon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;
import com.pla_bear.retrofit.RetrofitClient;
import com.pla_bear.retrofit.RetrofitService;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponDetailActivity extends BaseActivity {
    private RetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CouponDTO coupon;
        TextView textView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);

        service = RetrofitClient.getApiService(getString(R.string.api_base));

        try {
            Intent intent = getIntent();
            if(intent.hasExtra("coupon")) {
                coupon = (CouponDTO) Objects.requireNonNull(intent.getExtras()).getSerializable("coupon");

                generateBarCode(Objects.requireNonNull(coupon).getBarcode());

                textView = findViewById(R.id.coupon_edate);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                Date today = new Date();
                Date endDate = coupon.getEdate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(endDate);
                cal.add(Calendar.DATE, 1);
                endDate = cal.getTime();

                String edate = simpleDateFormat.format(endDate);
                textView.setText(edate);

                textView = findViewById(R.id.coupon_uname);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null) {
                    textView.setText(user.getDisplayName());
                }

                textView = findViewById(R.id.coupon_price);
                NumberFormat formatter = new DecimalFormat("#,###");
                textView.setText(getString(R.string.won, formatter.format(coupon.getPrice())));

                textView = findViewById(R.id.coupon_cdate);
                String cdate = simpleDateFormat.format(coupon.getCdate());
                textView.setText(cdate);

                int compare = today.compareTo(endDate);
                if(compare > 0) {
                    deleteCoupon(coupon);
                }

                Button deleteBtn = findViewById(R.id.coupon_delete_btn);
                deleteBtn.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CouponDetailActivity.this, R.style.AlertDialog);
                    builder.setTitle(R.string.warning);
                    builder.setMessage(R.string.delete_ask_msg);
                    builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> deleteCoupon(coupon));
                    builder.setNegativeButton(R.string.cancel, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                });
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void deleteCoupon(CouponDTO coupon) {
        Call<Void> call = service.deleteCoupon(coupon.getName());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CouponDetailActivity.this, R.style.AlertDialog);
                    builder.setTitle(R.string.notice);
                    builder.setMessage(R.string.delete_ok_msg);
                    builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> finish());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("Retrofit2", "DeleteCoupon Failed." + t.getMessage());
            }
        });
    }

    private void generateBarCode(String barcode) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            float width = getResources().getDimension(R.dimen.barcode_width);
            float height = getResources().getDimension(R.dimen.barcode_height);

            Bitmap bitmap = barcodeEncoder.encodeBitmap(barcode, BarcodeFormat.CODE_128, (int)width, (int)height);
            ImageView imageViewBarCode = findViewById(R.id.coupon_barcode_img);
            imageViewBarCode.setImageBitmap(bitmap);
        } catch(Exception ignored) {
        }

        TextView textView = findViewById(R.id.coupon_barcode);
        textView.setText(barcode);
    }
}
package com.pla_bear.coupon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.pla_bear.R;
import com.pla_bear.retrofit.RetrofitClient;
import com.pla_bear.retrofit.RetrofitService;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponDetailActivity extends AppCompatActivity {
    private RetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Coupon coupon;
        TextView textView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);

        service = RetrofitClient.getApiService(getString(R.string.api_base));

        try {
            Intent intent = getIntent();
            if(intent.hasExtra("coupon")) {
                coupon = (Coupon)intent.getExtras().getSerializable("coupon");

                generateBarCode(coupon.getBarcode());

                textView = findViewById(R.id.coupon_edate);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String edate = simpleDateFormat.format(coupon.getEdate());
                textView.setText(edate);

                textView = findViewById(R.id.coupon_uname);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null) {
                    textView.setText(user.getDisplayName());
                }

                textView = findViewById(R.id.coupon_price);
                NumberFormat formatter = new DecimalFormat("#,###");
                textView.setText(formatter.format(coupon.getPrice()) + " 원");

                textView = findViewById(R.id.coupon_cdate);
                String cdate = simpleDateFormat.format(coupon.getCdate());
                textView.setText(cdate);

                Button deleteBtn = findViewById(R.id.coupon_delete_btn);
                deleteBtn.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CouponDetailActivity.this);
                    builder.setTitle(R.string.warning);
                    builder.setMessage(R.string.delete_ask_msg);
                    builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> deleteCoupon(coupon));
                    builder.setNegativeButton(R.string.cancle, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                });
            }
        } catch (NullPointerException e) {
        }
    }

    private void deleteCoupon(Coupon coupon) {
        Call<Void> call = service.deleteCoupon(coupon.getName());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CouponDetailActivity.this);
                    builder.setTitle(R.string.notice);
                    builder.setMessage(R.string.delete_ok_msg);
                    builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                        ((CouponMainActivity)CouponMainActivity.CONTEXT).onResume();
                        finish();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit2", "DeleteCoupon Failed." + t.getMessage());
            }
        });
    }

    private void generateBarCode(String barcode) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            int width = (int)getResources().getDimension(R.dimen.barcode_width);
            int height = (int)getResources().getDimension(R.dimen.barcode_height);

            Bitmap bitmap = barcodeEncoder.encodeBitmap(barcode, BarcodeFormat.CODE_128, width, height);
            ImageView imageViewBarCode = findViewById(R.id.coupon_barcode_img);
            imageViewBarCode.setImageBitmap(bitmap);
        } catch(Exception e) {
        }

        TextView textView = findViewById(R.id.coupon_barcode);
        textView.setText(barcode);
    }
}
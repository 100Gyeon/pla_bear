package com.pla_bear.coupon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.pla_bear.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class CouponDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Coupon coupon;
        TextView textView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);

        try {
            Intent intent = getIntent();
            if(intent.hasExtra("coupon")) {
                coupon = (Coupon) intent.getExtras().getSerializable("coupon");

                generateBarCode(coupon.getBarcode());

                textView = findViewById(R.id.coupon_edate);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String edate = simpleDateFormat.format(coupon.getEdate());
                textView.setText(edate);

                textView = findViewById(R.id.coupon_uname);
                textView.setText(coupon.getUid());

                textView = findViewById(R.id.coupon_price);
                NumberFormat formatter = new DecimalFormat("#,###");
                textView.setText(formatter.format(coupon.getPrice()) + " 원");

                textView = findViewById(R.id.coupon_cdate);
                String cdate = simpleDateFormat.format(coupon.getCdate());
                textView.setText(cdate);
            }
        } catch (NullPointerException e) {
        }
    }

    private void generateBarCode(String barcode) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            int width = (int)getResources().getDimension(R.dimen.barcode_width);
            int height = (int)getResources().getDimension(R.dimen.barcode_height);

            Bitmap bitmap = barcodeEncoder.encodeBitmap(barcode, BarcodeFormat.CODE_128, width, height);
            ImageView imageViewBarCode = (ImageView) findViewById(R.id.coupon_barcode_img);
            imageViewBarCode.setImageBitmap(bitmap);
        } catch(Exception e) {

        }

        TextView textView = findViewById(R.id.coupon_barcode);
        textView.setText(barcode);
    }
}
package com.pla_bear.map.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;
import com.pla_bear.R;

public class QRCodeActivity extends AppCompatActivity {
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.setPrompt("Sample Text");
        qrScan.initiateScan();
    }
}
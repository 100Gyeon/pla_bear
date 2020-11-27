package com.pla_bear.map.qrcode;

import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;
import com.pla_bear.base.PointManager;
import com.pla_bear.map.GeoDAO;
import com.pla_bear.map.GeoDTO;

import java.util.ArrayList;
import java.util.List;

public class QRCodeActivity extends BaseActivity {
    static private final int QRCODE_POINT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        IntentIntegrator qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.setPrompt("사각형 안에 QR 코드가 들어오면 자동으로 인식됩니다");
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.qrcode_result);
        TextView titleView = findViewById(R.id.qrcode_title);
        TextView messageView = findViewById(R.id.qrcode_message);
        TextView descriptionView = findViewById(R.id.qrcode_description);

        if(result != null) {
            String contents = result.getContents();
            if(contents != null) {
                List<GeoDTO> addressData = GeoDAO.getAddressData();
                ArrayList<String> placeNames = new ArrayList<>();
                for(GeoDTO geoDTO : addressData) {
                    placeNames.add(geoDTO.getPlaceName());
                }

                // 실제 서비스에선 임의로 QR 코드를 생성하거나 막기 위해
                // 관련 내용을 API 서버에서 처리하고 QR 코드는 암호화한 내용을 저장해야 한다.
                // 또한 QR 코드에 시간 정보를 포함하여 Replace Attack 을 막을 수 있을 것이다.
                String[] pieces = contents.split(getString(R.string.qrcode_delimiter));

                if(placeNames.contains(pieces[0])) {
                    titleView.setText(R.string.qrcode_scan_done);
                    imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pass));
                    messageView.setText(getString(R.string.qrcode_point, QRCODE_POINT));
                    descriptionView.setText(getString(R.string.qrcode_description, pieces[0]));
                    PointManager.addPoint(PointManager.POINT_QRCODE);
                    return;
                }
            }

            titleView.setText(R.string.qrcode_scan_fail);
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fail));
            messageView.setText("다시 시도해주세요.");
            descriptionView.setText("매장을 찾을 수 없습니다.");
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
package com.pla_bear.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.pla_bear.map.qrcode.QRCodeActivity;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;
import com.pla_bear.base.Commons;
import com.pla_bear.board.review.ReviewDetailActivity;
import com.pla_bear.board.review.ReviewWriteActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MapActivity extends BaseActivity implements
        OnMapReadyCallback {

    private Circle previousCircle;
    private GoogleMap map; // 지도 뷰 선언
    private ArrayList<GeoDTO> info;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int REQUEST_CODE_PERMISSIONS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        info = new ArrayList<>(GeoDAO.getAddressData());

        // 지도는 fragment로 제공
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapxml);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng seoul = new LatLng(37.5595, 126.991); // default 위치는 서울

        if (info != null && info.size() > 0) {
            for (int i = 0; i < info.size(); i++) {
                LatLng placeLatLng = new LatLng(info.get(i).getPlaceLat(), info.get(i).getPlaceLng());
                // 마커 추가
                map.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title(info.get(i).getPlaceName())
                        .snippet("자세한 정보를 보려면 클릭해주세요.")
                        .position(placeLatLng));
            }
        }

        // 카메라 이동
        map.moveCamera(CameraUpdateFactory.newLatLng(seoul));
        // 카메라 줌인 (2.0f ~ 21.0f)
        map.animateCamera(CameraUpdateFactory.zoomTo(11.0f));
        // 마커 텍스트 클릭 이벤트
        map.setOnInfoWindowClickListener(new InfoWindowClickListener(info));
    }

    @SuppressWarnings("unused")
    public void QRcode(View v) {
        // QR code 화면으로 이동
        Intent intent = new Intent(MapActivity.this, QRCodeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @SuppressWarnings("unused")
    @SuppressLint("MissingPermission")
    public void mCurrentLocation(View v) {
        if (Commons.hasPermissions(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Commons.setPermissions(this,
                    REQUEST_CODE_PERMISSIONS,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION);
            return;
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(new GetLocationSuccessListener());
    }

    private class GetLocationSuccessListener implements OnSuccessListener<Location> {
        @Override
        public void onSuccess(final Location location) {
            if(location != null){
                LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                map.addMarker(new MarkerOptions()
                        .position(myLocation)
                        .title("현재 위치")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
                map.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                map.animateCamera(CameraUpdateFactory.zoomTo(14.0f));

                // 현재 위치 반경 1km 까지 원으로 표시
                CircleOptions circle = new CircleOptions().center(myLocation)
                        .radius(1000) // 반지름 1km
                        .strokeWidth(0f)
                        .fillColor(Color.parseColor("#400000FF"));

                if(previousCircle != null) previousCircle.remove();
                previousCircle = map.addCircle(circle);
            }
        }
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS:
                if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "권한 거부", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private class InfoWindowClickListener implements GoogleMap.OnInfoWindowClickListener {
        final private ArrayList<GeoDTO> info;

        public InfoWindowClickListener(ArrayList<GeoDTO> info) {
            this.info = info;
        }

        @Override
        public void onInfoWindowClick(final Marker marker) {
            if (!(marker.getTitle().equals("현재 위치"))) { // 현재 위치가 아닌 모든 마커에 이벤트 적용
                AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                View content = getLayoutInflater().inflate(R.layout.map_info_dialog, null);
                int idx = -1;

                for (int i = 0; i < info.size(); i++) {
                    if (marker.getTitle().equals(info.get(i).getPlaceName())) {
                            idx = i;
                            break;
                    }
                }

                final GeoDTO geoData = info.get(idx);
                builder.setIcon(getMarkerIcon(marker, idx));
                builder.setTitle(geoData.getPlaceName());
                builder.setMessage(geoData.getPlaceSnip());
                builder.setView(content);

                // 다이얼로그 생성
                builder.setPositiveButton("닫기", null);

                // 홈페이지 버튼
                AlertDialog alertDialog = builder.create();
                ImageButton button = content.findViewById(R.id.homepage_btn);
                button.setOnClickListener(view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoData.getPlaceWeb()));
                    MapActivity.this.startActivity(intent);
                });

                // 리뷰 작성 버튼
                button = content.findViewById(R.id.review_write_btn);
                button.setOnClickListener(view -> {
                    Intent intent = new Intent(getApplicationContext(), ReviewWriteActivity.class);
                    intent.putExtra("placeName", geoData.getPlaceName());
                    MapActivity.this.startActivity(intent);
                });

                // 리뷰 보기 버튼
                button = content.findViewById(R.id.review_detail_btn);
                button.setOnClickListener(view -> {
                    Intent intent = new Intent(getApplicationContext(), ReviewDetailActivity.class);
                    intent.putExtra("placeName", geoData.getPlaceName());
                    MapActivity.this.startActivity(intent);
                });

                // 전화 걸기 버튼
                button = content.findViewById(R.id.phone_call_btn);
                button.setOnClickListener(view -> {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    for (int i = 0; i < info.size(); i++) {
                        intent.setData(Uri.parse(geoData.getPlaceTel()));
                    }
                    MapActivity.this.startActivity(intent);
                });
                alertDialog.setCanceledOnTouchOutside(false); // 다이얼로그의 바깥을 터치해도 없어지지 않도록 설정
                alertDialog.show();
            }
        }
    }

    private int getMarkerIcon(final Marker marker, int idx) {
        if (marker.getTitle().contains("시장") || marker.getTitle().contains("상회") ||
                marker.getTitle().contains("청과") || marker.getTitle().contains("농산물")) {
            return(R.drawable.ic_market);
        } else if (marker.getTitle().contains("카페") || info.get(idx).getPlaceSnip().contains("카페")) {
            return(R.drawable.ic_cafe);
        } else if (info.get(idx).getPlaceSnip().contains("음식점")) {
            return(R.drawable.ic_restaurant);
        } else {
            return(R.drawable.ic_default);
        }
    }
}


package com.pla_bear.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
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
import com.pla_bear.QRCodeActivity;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;

import java.util.ArrayList;

public class MapActivity extends BaseActivity implements
        OnMapReadyCallback {

    private Circle previousCircle;
    private GoogleMap map; // 지도 뷰 선언
    private ArrayList<Data> list;
    protected Marker marker;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int REQUEST_CODE_PERMISSIONS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        list = GeoData.getAddressData();

        // 지도는 fragment로 제공
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapxml);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public void QRcode(View v) {
        // QR code 화면으로 이동
        Intent intent = new Intent(MapActivity.this, QRCodeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng seoul = new LatLng(37.5595, 126.991); // default 위치는 서울

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                LatLng placeLatLng = new LatLng(list.get(i).placeLat, list.get(i).placeLng);
                // 마커 추가
                marker = map.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title(list.get(i).placeName)
                        .snippet("자세한 정보를 보려면 클릭해주세요.")
                        .position(placeLatLng));
            }
        }

        // 카메라 이동
        map.moveCamera(CameraUpdateFactory.newLatLng(seoul));
        // 카메라 줌인 (2.0f ~ 21.0f)
        map.animateCamera(CameraUpdateFactory.zoomTo(11.0f));
        // 마커 텍스트 클릭 이벤트
        map.setOnInfoWindowClickListener(new InfoWindowClickListener(list, this));
    }

    public void mCurrentLocation(View v) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQUEST_CODE_PERMISSIONS);
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

                // 현재 위치 반경 1km까지 원으로 표시
                // 지도 축소 시 원이 현재 위치 밖으로 벗어나는 문제 해결해야 함
                CircleOptions circle = new CircleOptions().center(myLocation)
                        .radius(1000) // 반지름 1km
                        .strokeWidth(0f)
                        .fillColor(Color.parseColor("#400000FF"));

                if(previousCircle != null) previousCircle.remove();
                previousCircle = map.addCircle(circle);
            }
        }
    }

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
        private ArrayList<Data> list;

        public InfoWindowClickListener(ArrayList<Data> list, Activity context) {
            this.list = list;
        }

        @Override
        public void onInfoWindowClick(final Marker marker) {
            if (!(marker.getTitle().equals("현재 위치"))) { // 현재 위치가 아닌 모든 마커에 이벤트 적용
                AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                View content = getLayoutInflater().inflate(R.layout.info_dialog, null);

                for (int i = 0; i < list.size(); i++) {
                    if (marker.getTitle().equals(list.get(i).placeName)) {
                        // 다이얼로그에 타이틀, 메시지, 아이콘 설정
                        builder.setTitle(list.get(i).placeName);
                        builder.setMessage(list.get(i).placeSnip);
                    }
                }

                builder.setView(content);

                // 다이얼로그 생성
                builder.setPositiveButton("닫기", null);

                AlertDialog alertDialog = builder.create();
                ImageButton button = content.findViewById(R.id.homepage_btn);
                button.setOnClickListener(view -> {
                    for (int i = 0; i < list.size(); i++) {
                        if (marker.getTitle().equals(list.get(i).placeName)) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(i).placeWeb));
                            MapActivity.this.startActivity(intent);
                        }
                    }
                });

                button = content.findViewById(R.id.phone_call_btn);
                button.setOnClickListener(view -> {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    for (int i = 0; i < list.size(); i++) {
                        intent.setData(Uri.parse(list.get(i).placeTel));
                    }
                    MapActivity.this.startActivity(intent);
                });
                alertDialog.setCanceledOnTouchOutside(false); // 다이얼로그의 바깥을 터치해도 없어지지 않도록 설정
                alertDialog.show();
            }
        }
    }
}


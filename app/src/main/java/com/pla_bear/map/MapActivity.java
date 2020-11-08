package com.pla_bear.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.pla_bear.QRCodeActivity;
import com.pla_bear.R;
import com.pla_bear.navigation.NavigationItemHandler;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    private GoogleMap map; // 지도 뷰 선언
    private ArrayList<Data> list;
    protected Marker marker;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int REQUEST_CODE_PERMISSIONS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.main_drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.main_drawer_view);
        navigationView.setNavigationItemSelectedListener(new NavigationItemHandler(this));

        list = GeoData.getAddressData();

        // GoogleApiClient의 인스턴스 생성
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // 지도는 fragment로 제공
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapxml);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    public void QRcode(View v) {
        // QR code 화면으로 이동
        Intent intent = new Intent(MapActivity.this, QRCodeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {map = googleMap;
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
    }

    @Override
    protected void onStart() {
        // GoogleApiClient에 접속하는 일
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        // GoogleApiClient로부터 접속 해제하는 일
        mGoogleApiClient.disconnect();
        super.onStop();
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
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this,
                new OnSuccessListener<Location>() {
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
                            map.addCircle(circle);
                        }
                    }
                });
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
}
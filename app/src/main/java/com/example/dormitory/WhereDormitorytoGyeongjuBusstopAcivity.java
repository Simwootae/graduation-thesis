package com.example.dormitory;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

//연합->경주대 정류장
public class WhereDormitorytoGyeongjuBusstopAcivity extends FragmentActivity implements OnMapReadyCallback
{
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    Marker mk = new Marker();
    public LocationListener locationListener;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    LatLng pre_LOC = null;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        this.fragmentFunction();
        this.buttonfunction();

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        locationListener = new LocationListener()
        {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateMap(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                alertStatus(provider);
            }

            public void onProviderEnabled(@NonNull String provider) {
                alertProvider(provider);
            }

            public void onProviderDisabled(@NonNull String provider) {
                checkProvider(provider);
            }
        };/////////////////////////////////////////////////////////////////////////////////////

        this.locationService();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void locationService()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, LOCATION_PERMISSION_REQUEST_CODE);
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String locationProvider;
        locationProvider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider, 1, 1, locationListener);

        locationProvider = LocationManager.NETWORK_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider,1,1,locationListener);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,  @NonNull int[] grantResults)
    {

        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults))
        {
            if (!locationSource.isActivated()) {
                // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // 버튼 함수
    public void buttonfunction()
    {
        Button Main;
        Main = findViewById(R.id.homebutton);

        //홈 버튼 클릭시 화면 전환
        Main.setOnClickListener(v ->
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        Button button = findViewById(R.id.myGPS);
        button.setOnClickListener(v ->
        {
            if (naverMap != null) {
                // mk 마커를 항상 표시하도록 설정
                mk.setVisible(true);

                // 현재 위치로 지도의 중앙을 이동시킴
                if (pre_LOC != null) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(pre_LOC);
                    naverMap.moveCamera(cameraUpdate);

                    // mk 마커 위치 업데이트
                    mk.setPosition(pre_LOC);
                    mk.setMap(naverMap);

                    // InfoWindow를 이용하여 마커 위에 텍스트 표시
                    InfoWindow infoWindow = new InfoWindow();
                    infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
                        @NonNull
                        @Override
                        public CharSequence getText(@NonNull InfoWindow infoWindow) {
                            return "현재 위치";
                        }
                    });
                    infoWindow.open(mk);
                }
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //지도를 출력할 프래그먼트 영역 인식 함수
    public void fragmentFunction() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }

        //지도 사용이 준비되면 onMapReady 매소드 콜백
        mapFragment.getMapAsync(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        this.naverMap = naverMap;
        //지도 유형 출력 - 네비게이션
        naverMap.setMapType(NaverMap.MapType.Navi);

        //건물 아이콘 활성화
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);

        //심벌 크기
        naverMap.setSymbolScale(1.5f);

        //정류장 위도 경도 변수
        LatLng Busstop = new LatLng(35.84316431570256, 129.18147071390027);

        //지도 중심
        CameraUpdate cameraUpdate1 = CameraUpdate.scrollTo(Busstop);
        naverMap.moveCamera(cameraUpdate1);

        //지도 크기
        CameraUpdate cameraUpdate2 = CameraUpdate.zoomTo(15);
        naverMap.moveCamera(cameraUpdate2);

        //정류장 마커
        Marker markerBusstop = new Marker();
        markerBusstop.setPosition(Busstop);//위도, 경도
        markerBusstop.setMap(naverMap);

        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this)
        {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow)
            {
                return "정류장 위치";
            }
        });
        infoWindow.open(markerBusstop);


        locationListener = new LocationListener()
        {
            @Override
            //위치가 변할때마다 호출
            public void onLocationChanged(@NonNull Location location)
            {
                updateMap(location);
            }

            //위치가 변경될 때
            public void onStatusChanged(String provider, int status, Bundle extras)
            {
                alertStatus(provider);
            }

            //위치 변환이 가능하게 될 때
            public void onProviderEnabled(@NonNull String provider)
            {
                alertProvider(provider);
            }

            //위치 변환이 불가능하게 될 때
            public void onProviderDisabled(@NonNull String provider)
            {
                checkProvider(provider);
            }
        };
    }

    public void checkProvider(String provider)
    {
        Toast.makeText(this, provider + "에 의한 위치서비스가 꺼져 있습니다. 켜주세요", Toast.LENGTH_SHORT).show();
    }

    public void alertProvider(String provider)
    {
        Toast.makeText(this, provider + "서비스가 켜졌습니다", Toast.LENGTH_SHORT).show();
    }

    public void alertStatus(String provider)
    {
        Toast.makeText(this, "위치 서비스가" + provider + "로 변경되었습니다", Toast.LENGTH_LONG).show();
    }

    public void updateMap(Location location)
    {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng curr_LOC = new LatLng(latitude, longitude);

        // 이전 위치가 없는 경우
        if (pre_LOC == null) {
            // 위치 표시
            LocationOverlay locationOverlay = naverMap.getLocationOverlay();
            locationOverlay.setPosition(curr_LOC);

            mk.setVisible(false);
            mk.setPosition(curr_LOC);
            mk.setMap(naverMap);

            // 현재 위치를 이전 위치로 설정
            pre_LOC = curr_LOC;
        }
        // 이전 위치가 있는 경우
        else {
            mk.setVisible(false);
            mk.setPosition(curr_LOC);
            mk.setMap(naverMap);
            // 현재 위치를 이전 위치로 설정
            pre_LOC = curr_LOC;
        }
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if (locationManager != null)
        {locationManager.removeUpdates(locationListener);}
    }
}
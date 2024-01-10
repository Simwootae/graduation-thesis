package com.mypackage.dormitory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dormitory.R;
import com.google.android.material.navigation.NavigationView;

//메인 화면 액티비티
public class MainActivity extends AppCompatActivity //메인화면 액티비티
{
    Button donggukstation;
    Button Gyeongjustation;
    Toolbar toolbar;// 툴바 변수
    NavigationView navigationView;//네비게이션 뷰 변수
    DrawerLayout drawerLayout;//드로어 레이아웃 변수
    private final Context mContext = MainActivity.this;
    private NavigationView nav;//자바를 네비게이션을 통해 호출하기 위해 호출하기 위한 변수
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Nav_class();
        NavigationViewHelper.enableNavigation(mContext,nav);
        this.drawlayouyfunction();//툴바
        this.buttonfunction();//정류장 버튼
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //정류장 버튼 함수
    public void buttonfunction()
    {
        donggukstation = findViewById(R.id.dongguk);
        Gyeongjustation = findViewById(R.id.Gyeongju);

        //동국 정류장 클릭시 화면 전환
        donggukstation.setOnClickListener(v ->
        {
            Intent intent = new Intent(getApplicationContext(), DormitorytoDonggukBusAcivity.class);
            startActivity(intent);
        });
        //경주 정류장 클릭시 화면전환
        Gyeongjustation.setOnClickListener(v ->
        {
            Intent intent = new Intent(getApplicationContext(), DormitorytoGyeongjuBusstopAcivity.class);
            startActivity(intent);
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //드로우 네비게이션을 변수로 쓰기 위한 함수
    private void Nav_class()
    {
        nav = findViewById(R.id.navigation_view);//드로우 네비게이션 호출
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void drawlayouyfunction()
    //드로우 레이아웃 함수
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 툴바의 텍스트 비활성화

        drawerLayout = findViewById(R.id.drawer_layout); //드로어 내용
        navigationView = findViewById(R.id.navigation_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.draw_menu); //왼쪽 상단 버튼 아이콘 지정
    }
    //드로우 레이아웃 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            // 왼쪽 상단 버튼 눌렀을 때
            drawerLayout.open();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //이전 버튼 클릭시 드로어 닫기
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}

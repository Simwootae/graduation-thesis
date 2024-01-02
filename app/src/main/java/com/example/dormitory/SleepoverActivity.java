package com.example.dormitory;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

//입주신청 액티비티
public class SleepoverActivity extends AppCompatActivity //외박 신청 액티비티
{
    Button sleepoverbutton_main; //외박 신청 버튼 생성
    Button homebutton_main;//홈 버튼 생성
    WebView webView;//웹뷰 변수
    Toolbar toolbar;// 툴바 변수
    NavigationView navigationView;//네비게이션 뷰 변수
    DrawerLayout drawerLayout;//드로어 레이아웃 변수
    private Context mContext = SleepoverActivity.this;
    private NavigationView nav; //자바를 네비게이션을 통해 호출하기 위해 호출하기 위한 변수

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dromitory);//액티비티 메인 호출

        Nav_class();
        NavigationViewHelper.enableNavigation(mContext,nav);

        this.weviewfunction();//웹뷰
        this.drawlayouyfunction();//툴바
        this.buttonfunction();//하단버튼
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void Nav_class()
    {
        nav = findViewById(R.id.navigation_view);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void weviewfunction()
    //웹뷰 함수
    {
        webView = findViewById(R.id.announcement);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //자바스크립트 허용

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.gyh-dormitory.com/blank-7"); //외박 신청

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //화면이 계속 켜짐
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void buttonfunction()
    //버튼 함수
    {
        sleepoverbutton_main = findViewById(R.id.sleepoverbutton);//xml 파일에서 id 호출
        homebutton_main = findViewById(R.id.homebutton);

        //하단 버튼
        sleepoverbutton_main.setOnClickListener(v ->
        {
            Intent intent = new Intent(getApplicationContext(), SleepoverActivity.class);
            startActivity(intent);
        });
        homebutton_main.setOnClickListener(v ->
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
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
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        //뒤로가기 했을 때
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else
        {
            super.onBackPressed();
        }
    }
}

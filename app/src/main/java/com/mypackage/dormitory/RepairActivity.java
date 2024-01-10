package com.mypackage.dormitory;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dormitory.R;
import com.google.android.material.navigation.NavigationView;

public class RepairActivity extends AppCompatActivity //하자 보수 신청 액티비티
{
    Button sleepoverbutton_main; //외박 신청 버튼 생성
    Button homebutton_main;//홈 버튼 생성
    WebView webView;//웹뷰 변수
    Toolbar toolbar;// 툴바 변수
    NavigationView navigationView;//네비게이션 뷰 변수
    DrawerLayout drawerLayout;//드로어 레이아웃 변수
    private final Context mContext = RepairActivity.this;
    private NavigationView nav;//자바를 네비게이션을 통해 호출하기 위해 호출하기 위한 변수
    ////////////////////////////////////////////////////////////////////////////////////////
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
    //드로우 네비게이션을 변수로 쓰기 위한 함수
    private void Nav_class()
    {
        nav = findViewById(R.id.navigation_view);//드로우 네비게이션 호출
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void weviewfunction()
    //웹뷰 함수
    {
        webView = findViewById(R.id.announcement);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //자바스크립트 허용

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.gyh-dormitory.com/blank-5"); //하자 보수

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //화면이 계속 켜짐
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void buttonfunction()
    //버튼 함수
    {
        sleepoverbutton_main = findViewById(R.id.sleepoverbutton);//xml 파일에서 id 호출
        homebutton_main = findViewById(R.id.homebutton);

        //하단 버튼
        sleepoverbutton_main.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SleepoverActivity.class);
            startActivity(intent);
        });
        homebutton_main.setOnClickListener(v ->
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
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
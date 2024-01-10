package com.example.dormitory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class RestaurantActivity extends AppCompatActivity
{
    Button rulletButton;//버튼 함수
    Toolbar toolbar;// 툴바 변수
    NavigationView navigationView;//네비게이션 뷰 변수
    DrawerLayout drawerLayout;//드로어 레이아웃 변수
    private final Context mContext = RestaurantActivity.this;
    private NavigationView nav;//자바를 네비게이션을 통해 호출하기 위해 호출하기 위한 변수
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_restaureant);
        Nav_class();
        NavigationViewHelper.enableNavigation(mContext,nav);
        this.Text();//텍스트뷰 함수 호출

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void Text()
    {
        TextView textView1 = findViewById(R.id.textview1);
        TextView textView2 = findViewById(R.id.textview2);
        TextView textView3 = findViewById(R.id.textview3);
        TextView textView4 = findViewById(R.id.textview4);
        TextView textView5 = findViewById(R.id.textview5);
        TextView textView6 = findViewById(R.id.textview6);
        TextView textView7 = findViewById(R.id.textview7);
        TextView textView8 = findViewById(R.id.textview8);
        TextView textView9 = findViewById(R.id.textview9);
        TextView textView10 = findViewById(R.id.textview10);
        TextView textView11 = findViewById(R.id.textview11);
        TextView textView13 = findViewById(R.id.textview13);
        TextView textView14 = findViewById(R.id.textview14);
        TextView textView15 = findViewById(R.id.textview15);
        TextView textView16 = findViewById(R.id.textview16);
        TextView textView17 = findViewById(R.id.textview17);
        TextView textView18 = findViewById(R.id.textview18);
        TextView textView19 = findViewById(R.id.textview19);

        textView1.setOnClickListener(v -> {
            String url = "https://naver.me/GY2GmBMr";
            openWebPage(url);
        });
        textView2.setOnClickListener(v -> {
            String url = "https://naver.me/GyeyVeBH";
            openWebPage(url);
        });
        textView3.setOnClickListener(v -> {
            String url = "https://naver.me/51Ynsofv";
            openWebPage(url);
        });
        textView4.setOnClickListener(v -> {
            String url = "https://naver.me/xGOippGl";
            openWebPage(url);
        });
        textView5.setOnClickListener(v -> {
            String url = "https://naver.me/GxRRvT0s";
            openWebPage(url);
        });
        textView6.setOnClickListener(v -> {
            String url = "https://naver.me/GROFDY4K";
            openWebPage(url);
        });
        textView7.setOnClickListener(v -> {
            String url = "https://naver.me/5WciWh7F";
            openWebPage(url);
        });
        textView8.setOnClickListener(v -> {
            String url = "https://naver.me/52hyZL1q";
            openWebPage(url);
        });
        textView9.setOnClickListener(v -> {
            String url = "https://naver.me/5wftmifv";
            openWebPage(url);
        });
        textView10.setOnClickListener(v -> {
            String url = "https://naver.me/xFpwyB9Q";
            openWebPage(url);
        });
        textView11.setOnClickListener(v -> {
            String url = "https://naver.me/5jAw9u3g";
            openWebPage(url);
        });
        textView13.setOnClickListener(v -> {
            String url = "https://naver.me/5Z0qRV88";
            openWebPage(url);
        });
        textView14.setOnClickListener(v -> {
            String url = "https://naver.me/5JJwxZq4";
            openWebPage(url);
        });
        textView15.setOnClickListener(v -> {
            String url = "https://naver.me/GJrToIl8";
            openWebPage(url);
        });
        textView16.setOnClickListener(v -> {
            String url = "https://naver.me/5kLPznmo";
            openWebPage(url);
        });
        textView17.setOnClickListener(v -> {
            String url = "https://naver.me/xC6jpVK9";
            openWebPage(url);
        });
        textView18.setOnClickListener(v -> {
            String url = "https://naver.me/xTS1dgZO";
            openWebPage(url);
        });
        textView19.setOnClickListener(v -> {
            String url = "https://naver.me/xAtK2lTK";
            openWebPage(url);
        });
    }

    ///////////////////////////////////////////////////////////////////////
    private void openWebPage(String url)//웹페이지 처리 함수
    {
        if (url != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
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
package com.mypackage.dormitory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class DormitorytoDonggukBusAcivity extends AppCompatActivity //연합 기숙사->동국대 정류장 액티비티
{
    Button busstopbutton;
    boolean busFound = false; //도착 예정 버스가 없습니다 반복문용
    Toolbar toolbar;// 툴바 변수
    NavigationView navigationView;//네비게이션 뷰 변수
    DrawerLayout drawerLayout;//드로어 레이아웃 변수
    private final Context mContext = DormitorytoDonggukBusAcivity.this;
    private NavigationView nav;//자바를 네비게이션을 통해 호출하기 위해 호출하기 위한 변수
    TextView text;
    //발급받은 인증키(인코딩)
    String key = "hJ4D%2F0pmhHozXC0XRoM5iOeccDvtvD0XdcRCaolcp5OGcxdpqyxqJj3wJuKkEQnBke%2F0NqLfl9W8CDCVvb7vOA%3D%3D";
    String data;
    String city = "37020";
    String busstopcode = "KUB352001050";
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_busstop);

        this.Busprint();//파싱 출력 함수
        text = findViewById(R.id.text); //텍스트에 데이터 출력

        Nav_class();
        NavigationViewHelper.enableNavigation(mContext,nav);
        this.drawlayouyfunction();//툴바
        this.buttonfunction();//버튼 함수
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void buttonfunction()//버튼 함수
    {
        busstopbutton = findViewById(R.id.busstop);

        //정류장 클릭시 화면 전환
        busstopbutton.setOnClickListener(v ->
        {
            Intent intent = new Intent(getApplicationContext(), WhereDormitorytoDonggukBusAcivity.class);
            startActivity(intent);
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void Busprint()
    {
        new Thread(() ->
        {
            data = getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
            runOnUiThread(() ->
            {
                text.setText(data); //TextView에 문자열  data 출력
            });
        }).start();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    String getXmlData()
    {

        StringBuffer buffer=new StringBuffer();

        String queryUrl =
                //서비스 사이트
                "https://apis.data.go.kr/1613000/ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList?"+
                        //인증키
                        "serviceKey="+ key+
                        //페이지번호
                        "&pageNo=1" +
                        //페이지 결과 수
                        "&numOfRows=100" +
                        //xml, json
                        "&_type=xml" +
                        //도시 코드
                        "&cityCode=" +city+
                        //정류소 ID
                        "&nodeId=" + busstopcode;

        try
        {
            URL url= new URL(queryUrl);//URL 받아오기
            InputStream is = url.openStream(); //url 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기, UTF-8로 변환

            String tag;
            xpp.next();
            int eventType= xpp.getEventType();

            int busArrivalTime = -1; // 초기값으로 설정
            String busNumber = null; // 초기값으로 설정

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작\n\n");
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item")) {
                            // "item" 태그가 시작되면 새로운 결과가 시작된 것으로 가정
                            busArrivalTime = -1; // 초기화
                            busNumber = null; // 초기화
                        }
                        else if (tag.equals("arrtime"))
                        {
                            xpp.next();
                            busArrivalTime = Integer.parseInt(xpp.getText()); // "arrtime" 요소의 텍스트를 정수로 파싱
                        }
                        else if (tag.equals("routeno"))
                        {
                            xpp.next();
                            busNumber = xpp.getText();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item"))
                        {
                            // "item" 태그가 끝나면 현재 결과 출력
                            if (busArrivalTime >= 0 && busNumber != null)
                            {
                                if (busNumber.equals("50") || busNumber.equals("41") || busNumber.equals("51"))
                                {
                                    buffer.append("버스 번호 : " + busNumber + "\n");
                                    busFound = true; // 적어도 하나의 버스 번호가 맞는 경우 true로 설정
                                    if (busArrivalTime >= 60)
                                    {
                                        int minutes = busArrivalTime / 60;
                                        int seconds = busArrivalTime % 60;
                                        buffer.append(minutes + "분 " + seconds + "초");
                                    }
                                    else
                                    {
                                        buffer.append(busArrivalTime + "초\n");
                                    }
                                    buffer.append("\n");
                                }
                            }
                        }
                        break;
                }
                eventType = xpp.next();
            }

            if (!busFound)
            {
                buffer.append("도착 예정 버스가 없습니다.\n");
            }

        }
        catch (Exception ignored) {}
        return buffer.toString();//StringBuffer 문자열 객체 반환
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //드로우 네비게이션을 변수로 쓰기 위한 함수
    private void Nav_class()
    {
        nav = findViewById(R.id.navigation_view);//드로우 네비게이션 호출
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

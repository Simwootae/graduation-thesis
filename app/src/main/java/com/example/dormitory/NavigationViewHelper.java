package com.example.dormitory;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.navigation.NavigationView;

//메뉴에서 자바 엑티비티를 호출하기 위한 액티비티, 메뉴전환 용
public class NavigationViewHelper
{
    //컨텐츠 = 액티비티, 네비게이션뷰 = 네비게이션
    public static void enableNavigation(final Context context, NavigationView view)
    {
        view.setNavigationItemSelectedListener(item ->
        {
            //menu_list와 id 연동, 호출
            //첫번째 버튼(공지)
            if(item.getItemId()==R.id.announcement_menu)
            {
                Intent intent = new Intent(context, AnnouncementActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }

            //두번째 버튼(입주)
            else if (item.getItemId()==R.id.slee_menu)
            {
                Intent intent = new Intent(context,SleepoverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }

            //세번째 버튼
            else if (item.getItemId()==R.id.restu_menu)
            {
                Intent intent = new Intent(context,RestaurantActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }

            //네번째 버튼(버스정류장)
            else if (item.getItemId()==R.id.Bus_memu)
            {
                Intent intent = new Intent(context,busstopActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
            //다섯번째 버튼(하자보수)
            else if (item.getItemId()==R.id.Repair_menu)
            {
                Intent intent = new Intent(context,RepairActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
            //여섯번째 버튼(생활관 수칙)
            else if (item.getItemId()==R.id.rule_menu)
            {
                Intent intent = new Intent(context, DormitoryRuleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
            return true;
        });
    }
}
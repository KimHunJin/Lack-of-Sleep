package com.withmidi.autovibration.activity.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.withmidi.autovibration.R;
import com.withmidi.autovibration.adapter.GridCalendarAdapter;
import com.withmidi.autovibration.util.GridCalendarItem;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by HunJin on 2016-02-13.
 * 기본으로 제공되는 캘린더가 있는데 쓸대없이 인터넷 예제보고 따라해봄
 * 뭔가 나름 맘에 들음
 * 무쓸모.. 뻘짓..
 * 사실 그냥 소스 가져옴 귀차나.. (솔직히 어려워서 못만들었다고 함..)
 */
public class CalendarActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static int SUNDAY = 1;

    public static String[] WEEK = {"일","월","화","수","목","금","토"};

    private TextView txtDate;
    private GridCalendarAdapter mGridCalendarAdapter;
    private ArrayList<GridCalendarItem> mDayList; // 일 저장 리스트
    private GridView mGridView;

    Calendar mThisMonthCalendar;

    int select = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        txtDate = (TextView) findViewById(R.id.txtMonth);
        mGridView = (GridView) findViewById(R.id.grvMain);

        mDayList = new ArrayList<>();

        mGridView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mThisMonthCalendar = Calendar.getInstance();
        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH,1);
        getCalendar(mThisMonthCalendar);
    }

    private void getCalendar(Calendar calendar)
    {
        int lastMonthStartDay;
        int dayOfMonth;
        int thisMonthLastDay;

        mDayList.clear();

        // 이번달 시작일의 요일을 구한다. 시작일이 일요일인 경우 인덱스를 1(일요일)에서 8(다음주 일요일)로 바꾼다.)
        dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, -1);

        // 지난달의 마지막 일자를 구한다.
        lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, 1);

        if(dayOfMonth == SUNDAY)
        {
            dayOfMonth += 7;
        }

        lastMonthStartDay -= (dayOfMonth-1)-1;


        // 캘린더 타이틀(년월 표시)을 세팅한다.
        txtDate.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

        GridCalendarItem day;

        Log.e("DayOfMOnth", dayOfMonth + "");

        for(int i=0;i<WEEK.length;i++) {
            day = new GridCalendarItem();
            day.setDay(WEEK[i]);
            day.setInMonth(false);

            mDayList.add(day);
        }

        for(int i=0; i<dayOfMonth-1; i++)
        {
            int date = lastMonthStartDay+i;
            day = new GridCalendarItem();
            day.setDay(Integer.toString(date));
            day.setInMonth(false);

            mDayList.add(day);
        }
        for(int i=1; i <= thisMonthLastDay; i++)
        {
            day = new GridCalendarItem();
            day.setDay(Integer.toString(i));
            day.setInMonth(true);

            mDayList.add(day);
        }
        for(int i=1; i<42-(thisMonthLastDay+dayOfMonth-1)+1; i++)
        {
            day = new GridCalendarItem();
            day.setDay(Integer.toString(i));
            day.setInMonth(false);
            mDayList.add(day);
        }

        initCalendarAdapter();
    }

    /**
     * 지난달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return LastMonthCalendar
     */
    private Calendar getLastMonth(Calendar calendar)
    {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, -1);
        txtDate.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
        return calendar;
    }

    /**
     * 다음달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return NextMonthCalendar
     */
    private Calendar getNextMonth(Calendar calendar)
    {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, +1);
        txtDate.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
        return calendar;
    }

    private void initCalendarAdapter()
    {
        mGridCalendarAdapter = new GridCalendarAdapter(this, R.layout.item_gridview, mDayList);
        mGridView.setAdapter(mGridCalendarAdapter);
    }

    public void onGridClick(View v) {

        switch (v.getId()) {
            case R.id.btnPast: {
                mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;
            }
            case R.id.btnNext: {
                mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;
            }
        }
    }

    /**
     * 정해진 시간부터 어느 시간까지 알람 설정을 여기서 하면 됨
     * 같은 날짜 두번 클릭 시 이벤트 생김
     * 특정 날짜의 알람 맞춰놓고 그 시간에 ChangeModeActivity에 있는 테스트 소스 가져와서 쓰면 될거임
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(select == position) {
            Toast.makeText(getApplicationContext(),"선택 날짜 : " + (mThisMonthCalendar.get(Calendar.MONTH)+1) + "월" + mDayList.get(position).getDay()+ "일",Toast.LENGTH_SHORT).show();
            Intent it = new Intent(getApplicationContext(),DetailCalendarSettingActivity.class);
            it.putExtra("month",mThisMonthCalendar.get(Calendar.MONTH)+1);
            it.putExtra("day",mDayList.get(position).getDay());
            startActivity(it);
        } else {
            select = position;
        }
    }
}

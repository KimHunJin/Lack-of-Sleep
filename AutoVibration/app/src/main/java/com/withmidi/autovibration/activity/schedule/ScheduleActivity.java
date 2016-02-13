package com.withmidi.autovibration.activity.schedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.withmidi.autovibration.R;
import com.withmidi.autovibration.util.TimeTableModel;
import com.withmidi.autovibration.view.TimeTableView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HunJin on 2016-02-13.
 * 그리드뷰 안에 동적으로 생성할거임
 * 강의 시간(교시 설정)과 컬럼(요일)을 가져와서 시간표 만들거임
 * 많이들 쓰는 시간표 어플 생각하면 됨
 * 해야하는데
 * 하기귀차나서
 * 자려고함
 * 아침 7시네.. 캘린더에서 뻘짓을 너무 했다..
 *
 */
public class ScheduleActivity extends AppCompatActivity {

    private TimeTableView mTimaTableView;
    private List<TimeTableModel> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Intent it = getIntent();
        String mColumn = it.getExtras().getString("column");
        String mLow = it.getExtras().getString("low");

        Log.e("aa",mColumn);
        mColumn = mColumn.substring(0,1);
        Log.e("aa",mColumn);
        int column = Integer.parseInt(mColumn);
        int low = Integer.parseInt(mLow);

        mList = new ArrayList<TimeTableModel>();

        mTimaTableView = (TimeTableView) findViewById(R.id.main_timetable_ly);
        mTimaTableView.setMaxnum(low);
        mTimaTableView.setWeeknum(column);
        addList();
        mTimaTableView.setTimeTable(mList);
    }

    private void addList() {
        mList.add(new TimeTableModel(0, 1, 2, 1, "8:20", "10:10", "Test1",
                "Test1", "1", "2-13"));
        mList.add(new TimeTableModel(0, 3, 4, 1, "8:20", "10:10", "Test2",
                "Test2", "2", "2-13"));
        mList.add(new TimeTableModel(0, 6, 7, 1, "8:20", "10:10", "Test3",
                "Test3","3", "2-13"));
        mList.add(new TimeTableModel(0, 6, 7, 2, "8:20", "10:10", "Test4",
                "Test4", "4", "2-13"));
        mList.add(new TimeTableModel(0, 8, 9, 2, "8:20", "10:10", "Test5",
                "Test5", "5", "2-13"));
        mList.add(new TimeTableModel(0, 1, 2, 3, "8:20", "10:10", "Test6",
                "Test6", "6", "2-13"));
        mList.add(new TimeTableModel(0, 6, 7, 3, "8:20", "10:10", "Test7",
                "Test7", "7", "2-13"));
        mList.add(new TimeTableModel(0, 3, 5, 4, "8:20", "10:10", "Test8",
                "Test8", "8", "2-13"));
        mList.add(new TimeTableModel(0, 8, 9, 4, "8:20", "10:10", "Test9",
                "Test9", "9", "2-13"));
        mList.add(new TimeTableModel(0, 3, 5, 5, "8:20", "10:10", "Test10",
                "Test10", "10", "2-13"));
        mList.add(new TimeTableModel(0, 6, 8, 5, "8:20", "10:10", "Test11",
                "Test11", "11", "2-13"));
    }

}

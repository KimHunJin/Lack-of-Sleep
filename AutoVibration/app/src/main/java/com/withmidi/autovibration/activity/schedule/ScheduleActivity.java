package com.withmidi.autovibration.activity.schedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.withmidi.autovibration.R;
import com.withmidi.autovibration.activity.calendar.CalendarActivity;
import com.withmidi.autovibration.db.SQLManager;
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
 */
public class ScheduleActivity extends AppCompatActivity {

    private TimeTableView mTimaTableView;
    private List<TimeTableModel> mList;
    public static SQLManager sqlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        sqlManager = new SQLManager(getApplicationContext(), "autovibration.db", null, 1);

        mList = new ArrayList<TimeTableModel>();

        mTimaTableView = (TimeTableView) findViewById(R.id.main_timetable_ly);

//        sqlManager.insert("INSERT INTO `schedule` (startnum,endnum,week,name,teacher,classroom) VALUES ('" + 1 + "', '" + 2 + "', '" + 1 + "', 'test', 'test', '101')");
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTimaTableView.removeAllViews();

        mList.clear();

        // schedule select query
        Cursor cursor = sqlManager.getAllSchedule();

        while (cursor.moveToNext()) {
            mList.add(new TimeTableModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
        }
        mTimaTableView.setTimeTable(mList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setWeek) {
            final String[] ArrayList = {"월요일 ~ 금요일", "월요일 ~ 토요일", "월요일 ~ 일요일"};
            AlertDialog.Builder dlg = new AlertDialog.Builder(ScheduleActivity.this);
            dlg.setTitle("요일 설정");

            dlg.setSingleChoiceItems(ArrayList, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ScheduleActivity.this, "설정 되었습니다..", Toast.LENGTH_LONG).show();
                }
            });
            dlg.setNegativeButton("취소", null);
            dlg.show();
        }
        if (id == R.id.action_reset) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(ScheduleActivity.this);//(엑티비티명.this)
            dlg.setTitle("초기화");
            dlg.setMessage("정말 초기화 하시겠습니까?");

            // 확인이나 닫기 1개만 사용랄 때, ("문자열",리스너)
            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sqlManager.initializeSchedule();
                    Toast.makeText(ScheduleActivity.this, "초기화 되었습니다.", Toast.LENGTH_LONG).show();
                    onResume();
                }
            });
            dlg.setNegativeButton("취소", null);
            //설정이 모두 끝났으면 show로 뛰우기
            dlg.show();
        }

        if (id == R.id.intent_Calendar) {
            startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}

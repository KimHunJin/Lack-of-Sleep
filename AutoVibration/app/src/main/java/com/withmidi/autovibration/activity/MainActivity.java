package com.withmidi.autovibration.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.withmidi.autovibration.activity.calendar.CalendarActivity;
import com.withmidi.autovibration.activity.schedule.AddScheduleActivity;
import com.withmidi.autovibration.R;

/**
 * Created by HunJin on 2016-02-13.
 * 실질적인 메인인데 그냥 테스트 하려고 만든거임
 * 새벽 3시 ~ 7시까지 작업 완료
 * 자야징~
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGoActivity(View v) {
        switch (v.getId()) {
            case R.id.btnSchedule : {
                startActivity(new Intent(getApplicationContext(),AddScheduleActivity.class));
                break;
            }
            case R.id.btnCalendar : {
                startActivity(new Intent(getApplicationContext(),CalendarActivity.class));
                break;
            }
            case R.id.btnChangeMode : {
                startActivity(new Intent(getApplicationContext(),ChangeModeActivity.class));
                break;
            }
        }
    }
}

package com.withmidi.autovibration.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.withmidi.autovibration.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Intent it = getIntent();
        String mColumn = it.getExtras().getString("column");
        String mLow = it.getExtras().getString("low");

        mColumn = mColumn.substring(0,1);
        int column = Integer.parseInt(mColumn);
        int low = Integer.parseInt(mLow);


    }
}

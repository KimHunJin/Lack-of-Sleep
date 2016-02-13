package com.withmidi.autovibration.activity.calendar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.withmidi.autovibration.R;
import com.withmidi.autovibration.alarm.AlarmSystemActivity2;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DetailCalendarSettingActivity extends AppCompatActivity {

    TextView txtStartTime, txtFinishTime;
    int month, day, hour, minute;
    RadioGroup rdgMode;
    EditText edtTitle;

    String title, startTime, finishTime, mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_calendar_setting);

        // sql에 저장할거임 이따가
        Intent it = getIntent();
        month = it.getExtras().getInt("month");
        day = it.getExtras().getInt("day");

        GregorianCalendar calendar = new GregorianCalendar();

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        Log.e("hour", hour + "");
        minute = calendar.get(Calendar.MINUTE);
        Log.e("minute", minute + "");

        txtStartTime = (TextView) findViewById(R.id.txtStartTime);
        txtFinishTime = (TextView) findViewById(R.id.txtFinishTime);
        rdgMode = (RadioGroup)findViewById(R.id.rdgMode);
        edtTitle = (EditText)findViewById(R.id.edtTitle);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

    }

    public void onTimeSetting(View v) {
        switch (v.getId()) {
            case R.id.liStartTIme: {
                TimePickerDialog tpd = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtStartTime.setText(hourOfDay + " : " + minute);
                            }
                        }, hour, minute, false);
                tpd.show();
                break;
            }
            case R.id.liFinishTime: {
                TimePickerDialog tpd = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtFinishTime.setText(hourOfDay + " : " + minute);
                            }
                        }, hour, minute, false);
                tpd.show();
                break;
            }
        }
    }

    public void onMakeAlarmEvent(View v) {
        switch (v.getId()) {
            case R.id.btnDetailSave : {
                RadioButton selectedRadioButton = (RadioButton)findViewById(rdgMode.getCheckedRadioButtonId());
                title = edtTitle.getText().toString().trim();
                startTime = txtStartTime.getText().toString().trim();
                finishTime = txtFinishTime.getText().toString().trim();
                mode = selectedRadioButton.getText().toString().trim();

                if(title.equals("") || startTime.equals("") || finishTime.equals("")) {
                    Toast.makeText(getApplicationContext(),"모두 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent it = new Intent(getApplicationContext(), AlarmSystemActivity2.class);
                it.putExtra("startTime",startTime);
                it.putExtra("finishTime",finishTime);
                it.putExtra("mode",mode);
                startActivity(it);
                finish();
                break;
            }
            case R.id.btnDetailCancel : {
                finish();
                break;
            }
        }
    }
}

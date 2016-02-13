package com.withmidi.autovibration.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.withmidi.autovibration.R;
import com.withmidi.autovibration.db.SQLManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmSystemActivity2 extends AppCompatActivity {

    private static final int SILENCE = 0;
    private static final int VIBRATION = 1;
    private static final int NORMAL = 2;

    private AudioManager mAudioManager;

    int getStartH;
    int getStartM;
    int getFinishH;
    int getFinishM;

    int modeNum;
    int modeNumber[];

    ArrayList<Integer> H;
    ArrayList<Integer> M;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_system);

        init();
        alarm();
    }

    void init() {
        H = new ArrayList<>();
        M = new ArrayList<>();
        Intent it = getIntent();
        String startTime = it.getExtras().getString("startTime");
        String finishTime = it.getExtras().getString("finishTime");
        String modeString = it.getExtras().getString("mode");
        Log.e("modeString", modeString);
        modeNum = -1;
        if (modeString.equals("vibration")) {
            modeNum = VIBRATION;
        } else if (modeString.equals("silence")) {
            modeNum = SILENCE;
        } else if (modeString.equals("normal")) {
            modeNum = NORMAL;
        }

        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        int modeInit = mAudioManager.getRingerMode();
        modeNumber = new int[]{modeNum, modeInit};
        Log.e("mode", modeNum + "");
        Log.e("mode2",modeInit+ "");

        String[] startTimeDivision = startTime.split(":");
        String[] finishTimeDivision = finishTime.split(":");
        getStartH = Integer.parseInt(startTimeDivision[0].toString().trim());
        getStartM = Integer.parseInt(startTimeDivision[1].toString().trim());
        getFinishH = Integer.parseInt(finishTimeDivision[0].toString().trim());
        getFinishM = Integer.parseInt(finishTimeDivision[1].toString().trim());

        H.add(getStartH);
        H.add(getFinishH);
        M.add(getStartM);
        M.add(getFinishM);

    }

    void alarm() {

        Intent intent;
        PendingIntent[] sender = new PendingIntent[H.size()];
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        for (int i = 0; i < sender.length; i++) {
            intent = new Intent(AlarmSystemActivity2.this, AlarmServiceReceiver.class).putExtra("mode", modeNumber[i]);
            sender[i] = PendingIntent.getBroadcast(getApplicationContext(), i, intent, 0);

            int hour = H.get(i);

            int minute = M.get(i);

            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            long alarmTime = cal.getTimeInMillis();

            am.set(AlarmManager.RTC_WAKEUP, alarmTime, sender[i]);
        }
        finish();
    }}

package com.withmidi.autovibration.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.withmidi.autovibration.R;
import com.withmidi.autovibration.db.SQLManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmSystemActivity extends AppCompatActivity {

    private static final int SILENCE = 0;
    private static final int VIBRATION = 1;
    private static final int NORMAL = 2;

    private AudioManager mAudioManager;

    int getStartH;
    int getStartM;
    int getFinishH;
    int getFinishM;

    String modeNum;
//    int modeNumber[];
    int modeNumber;

    ArrayList<Integer> H;
    ArrayList<Integer> M;
    ArrayList<Integer> MM;

    public static SQLManager sqlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_system);

        sqlManager = new SQLManager(getApplicationContext(), "autovibration.db", null, 1);

        init();
        alarm();
    }

    void init() {

        H = new ArrayList<>();
        M = new ArrayList<>();
        MM = new ArrayList<>();

        Cursor cursor = sqlManager.getDateInfo();
        while (cursor.moveToNext()) {
            getStartH = cursor.getInt(1);
            getStartM = 0;
            getFinishH = cursor.getInt(2);
            getFinishM = 0;
            modeNum = cursor.getString(3);

            H.add(getStartH);
            H.add(getFinishH);
            M.add(getStartM);
            M.add(getFinishM);


            mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

            if (modeNum.equals("진동모드")) {
                modeNumber = VIBRATION;
            } else if (modeNum.equals("무음모드")) {
                modeNumber = SILENCE;
            } else if (modeNum.equals("소리모드")) {
                modeNumber = NORMAL;
            } else if(modeNum.equals("없음")) {
                modeNumber = mAudioManager.getMode();
            }

            MM.add(modeNumber);
        }

//        Intent it = getIntent();
//        String startTime = it.getExtras().getString("startTime");
//        String finishTime = it.getExtras().getString("finishTime");
//        String modeString = it.getExtras().getString("mode");
//        Log.e("modeString", modeString);




//        modeNumber = new int[]{modeNum, mAudioManager.getMode()};
//        Log.e("mode", modeNum + "");
//
//        String[] startTimeDivision = startTime.split(":");
//        String[] finishTimeDivision = finishTime.split(":");
//        getStartH = Integer.parseInt(startTimeDivision[0].toString().trim());
//        getStartM = Integer.parseInt(startTimeDivision[1].toString().trim());
//        getFinishH = Integer.parseInt(finishTimeDivision[0].toString().trim());
//        getFinishM = Integer.parseInt(finishTimeDivision[1].toString().trim());
//
//        H.add(getStartH);
//        H.add(getFinishH);
//        M.add(getStartM);
//        M.add(getFinishM);

    }

    void alarm() {

        Intent intent;
        PendingIntent[] sender = new PendingIntent[M.size()];
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        for (int i = 0; i < MM.size(); i++) {
            intent = new Intent(AlarmSystemActivity.this, AlarmServiceReceiver.class).putExtra("mode", MM.get(i));


            for(int j=i*2;j<=i*2+1;j++) {
                sender[j] = PendingIntent.getBroadcast(getApplicationContext(), j, intent, 0);
                int hour = H.get(j);

                int minute = M.get(j);

                Calendar cal = new GregorianCalendar();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);

                long alarmTime = cal.getTimeInMillis();

                am.set(AlarmManager.RTC_WAKEUP, alarmTime, sender[j]);
            }
        }
        finish();
    }
}

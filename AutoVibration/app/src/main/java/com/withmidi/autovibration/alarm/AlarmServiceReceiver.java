package com.withmidi.autovibration.alarm;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by HunJin on 2016-02-13.
 */
public class AlarmServiceReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int mode = intent.getExtras().getInt("mode");
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        int mSecond =c.get(Calendar.SECOND);

        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(mode);

        Toast.makeText(context, "현재 시간"+mHour+":"+mMinute+":"+mSecond, Toast.LENGTH_SHORT).show();
    }
}

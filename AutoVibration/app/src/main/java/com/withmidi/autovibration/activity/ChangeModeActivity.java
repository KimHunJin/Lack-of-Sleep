package com.withmidi.autovibration.activity;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.withmidi.autovibration.R;

/**
 * Created by HunJin on 2016-02-13.
 * 진동 / 소리 / 무음 바뀌는지 테스트 하려고 만든 거임
 * 어플 다 만들고 지워버려
 */
public class ChangeModeActivity extends AppCompatActivity {


    AudioManager aManager;

    private static final int SILENCE = 0;
    private static final int VIBRATION = 1;
    private static final int NORMAL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mode);

        aManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        Log.e("mode", aManager.getRingerMode() + "");
    }

    public void onChangeMode(View v) {
        switch (v.getId()) {
            case R.id.btnNormalMode: {
                aManager.setRingerMode(NORMAL);
                break;
            }
            case R.id.btnSilenceMode: {
                aManager.setRingerMode(SILENCE);
                break;
            }
            case R.id.btnVibrationMode: {
                aManager.setRingerMode(VIBRATION);
                break;
            }
        }
    }
}

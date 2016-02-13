package com.withmidi.autovibration.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kim on 2016-02-13.
 */
public class AttrSave {
    // 파일에서 get
    public static String getAppPreferences(Context context, String key){
        String returnValue = null;
        SharedPreferences pref = null;
        pref = context.getSharedPreferences("photo_feed",0);
        returnValue = pref.getString(key,"");
        return returnValue;
    }

    //파일에 저장
    public static void setAppPreferences(Context context, String key, String value){
        SharedPreferences pref = null;
        pref = context.getSharedPreferences("photo_feed",0);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString(key, value);
        prefEditor.commit();
    }
}
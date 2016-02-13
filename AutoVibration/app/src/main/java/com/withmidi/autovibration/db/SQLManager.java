package com.withmidi.autovibration.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kim on 2016-02-13.
 */
public class SQLManager extends SQLiteOpenHelper {

    public SQLManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `schedule` (startnum INTEGER, endnum INTEGER, week INTEGER, name TEXT, teacher TEXT, classroom TEXT, mode TEXT);");
//        db.execSQL("CREATE TABLE `time` (day INTEGER, startHour INTEGER, startMinute INTEGER, finishHour INTEGER, finishMinute INTEGER");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String query){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public Cursor getAllSchedule(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM `schedule`", null);

        return cursor;
    }

    public Cursor getDaySchedule(int week){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM `schedule` WHERE `week`='"+week+"'", null);

        return cursor;
    }

    public Cursor getDateInfo() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT `week`,`startnum`, `endnum`, `mode` FROM `schedule` ORDER BY `week`,`startnum` ASC;",null);

        return cursor;
    }


    public void delete(String query){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void update(String query){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void initializeSchedule(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM `schedule`");
        db.close();
    }
}

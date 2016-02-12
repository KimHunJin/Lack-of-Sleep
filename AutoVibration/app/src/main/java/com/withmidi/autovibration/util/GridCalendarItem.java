package com.withmidi.autovibration.util;

/**
 * Created by HunJin on 2016-02-13.
 * 캘린더 만들때 사용한 아이템임
 */
public class GridCalendarItem {
    private String day;
    private boolean inMonth;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isInMonth() {
        return inMonth;
    }

    public void setInMonth(boolean inMonth) {
        this.inMonth = inMonth;
    }
}

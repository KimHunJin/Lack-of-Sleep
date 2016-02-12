package com.withmidi.autovibration.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.withmidi.autovibration.R;
import com.withmidi.autovibration.holder.GridCalendarHolder;
import com.withmidi.autovibration.util.GridCalendarItem;

import java.util.ArrayList;

/**
 * Created by HunJin on 2016-02-13.
 * 캘린더 만들기 위해 사용했던 어댑터임
 */
public class GridCalendarAdapter extends BaseAdapter {

    private ArrayList<GridCalendarItem> mDayList;
    private Context mContext;
    private int mResource;
    private LayoutInflater mLayoutInflater;

    public GridCalendarAdapter(Context context, int textResource, ArrayList<GridCalendarItem> dayList) {

        this.mContext = context;
        this.mDayList = dayList;
        this.mResource = textResource;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return mDayList.size();
    }


    @Override
    public Object getItem(int position) {

        return mDayList.get(position);
    }


    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GridCalendarItem day = mDayList.get(position);
        GridCalendarHolder mGridCalendarHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(mResource, null);
            if (position % 7 == 6) {
                convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP() + getRestCellWidthDP(), getCellHeightDP()));
            } else {
                convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP(), getCellHeightDP()));
            }

            mGridCalendarHolder = new GridCalendarHolder();
            mGridCalendarHolder.dayCellBackground = (LinearLayout) convertView.findViewById(R.id.dayCellBackground);
            mGridCalendarHolder.txtDay = (TextView) convertView.findViewById(R.id.txtDay);

            convertView.setTag(mGridCalendarHolder);
        } else {
            mGridCalendarHolder = (GridCalendarHolder) convertView.getTag();
        }

        if (day != null) {
            mGridCalendarHolder.txtDay.setText(day.getDay());
            if (day.isInMonth()) {
                if (position % 7 == 0) {
                    mGridCalendarHolder.txtDay.setTextColor(Color.RED);
                } else if (position % 7 == 6) {
                    mGridCalendarHolder.txtDay.setTextColor(Color.BLUE);
                } else {
                    mGridCalendarHolder.txtDay.setTextColor(Color.BLACK);
                }
            } else {
                mGridCalendarHolder.txtDay.setTextColor(Color.GRAY);
            }

        }

        return convertView;
    }


    private int getCellWidthDP() {
        int cellWidth = 480 / 7;
        return cellWidth;
    }

    private int getRestCellWidthDP() {
        int cellWidth = 480 % 7;
        return cellWidth;
    }

    private int getCellHeightDP() {
        int cellHeight = 480 / 6;
        return cellHeight;
    }
}

package com.withmidi.autovibration.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.withmidi.autovibration.R;
import com.withmidi.autovibration.activity.schedule.AddScheduleActivity;
import com.withmidi.autovibration.util.AttrSave;
import com.withmidi.autovibration.util.TimeTableModel;

import java.util.ArrayList;
import java.util.List;

public class TimeTableView extends LinearLayout {
    public static int colors[] = {R.drawable.select_label_san,
            R.drawable.select_label_er, R.drawable.select_label_si,
            R.drawable.select_label_wu, R.drawable.select_label_liu,
            R.drawable.select_label_qi, R.drawable.select_label_ba,
            R.drawable.select_label_jiu, R.drawable.select_label_sss,
            R.drawable.select_label_se, R.drawable.select_label_yiw,
            R.drawable.select_label_sy, R.drawable.select_label_yiwu,
            R.drawable.select_label_yi, R.drawable.select_label_wuw};

    int maxnum = 9;
    int weeknum = 6;

    private final static int TimeTableHeight = 50;

    private final static int TimeTableLineHeight = 2;
    private final static int TimeTableNumWidth = 20;
    private final static int TimeTableWeekNameHeight = 30;
    private LinearLayout mHorizontalWeekLayout;
    private LinearLayout mVerticalWeekLaout;
    private String[] weekname = {"월", "화", "수", "목", "금", "토", "일"};
    public static String[] colorStr = new String[20];
    int colornum = 0;

    private List<TimeTableModel> mListTimeTable = new ArrayList<TimeTableModel>();

    public TimeTableView(Context context) {
        super(context);
    }

    public TimeTableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private View getWeekTransverseLine() {
        TextView mWeekline = new TextView(getContext());
        mWeekline.setBackgroundColor(getResources().getColor(R.color.view_line));
        mWeekline.setHeight(TimeTableLineHeight);
        mWeekline.setWidth(LayoutParams.FILL_PARENT);
        return mWeekline;
    }

    private View getWeekVerticalLine() {
        TextView mWeekline = new TextView(getContext());
        mWeekline.setBackgroundColor(getResources().getColor(R.color.view_line));
        mWeekline.setHeight(dip2px(TimeTableWeekNameHeight));
        mWeekline.setWidth((TimeTableLineHeight));
        return mWeekline;
    }


    private void initView() {
        if(!AttrSave.getAppPreferences(getContext(),"maxnum").equals("")){
            maxnum = Integer.valueOf(AttrSave.getAppPreferences(getContext(),"maxnum"));
        }

        if(!AttrSave.getAppPreferences(getContext(),"weeknum").equals("")){
            weeknum = Integer.valueOf(AttrSave.getAppPreferences(getContext(),"weeknum"));
        }

        mHorizontalWeekLayout = new LinearLayout(getContext());
        mHorizontalWeekLayout.setOrientation(HORIZONTAL);

        mVerticalWeekLaout = new LinearLayout(getContext());
        mVerticalWeekLaout.setOrientation(HORIZONTAL);
        for (int i = 0; i <= weeknum; i++) {
            switch (i) {
                case 0:
                    TextView mTime = new TextView(getContext());
                    mTime.setHeight(dip2px(TimeTableWeekNameHeight));
                    mTime.setWidth((dip2px(TimeTableNumWidth)));
                    mHorizontalWeekLayout.addView(mTime);

                    LinearLayout mMonday = new LinearLayout(getContext());
                    ViewGroup.LayoutParams mm = new ViewGroup.LayoutParams(dip2px(TimeTableNumWidth), dip2px(maxnum * TimeTableHeight) + maxnum * 2);
                    mMonday.setLayoutParams(mm);
                    mMonday.setOrientation(VERTICAL);
                    for (int j = 1; j <= maxnum; j++) {
                        TextView mNum = new TextView(getContext());
                        mNum.setGravity(Gravity.CENTER);
                        mNum.setTextColor(getResources().getColor(R.color.text_color));
                        mNum.setHeight(dip2px(TimeTableHeight));
                        mNum.setWidth(dip2px(TimeTableNumWidth));
                        mNum.setTextSize(14);
                        mNum.setText(j + "");
                        mMonday.addView(mNum);
                        mMonday.addView(getWeekTransverseLine());
                    }
                    mVerticalWeekLaout.addView(mMonday);
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    LinearLayout mHoriView = new LinearLayout(getContext());
                    mHoriView.setOrientation(VERTICAL);
                    TextView mWeekName = new TextView(getContext());
                    mWeekName.setTextColor(getResources().getColor(R.color.text_color));
                    mWeekName.setWidth(((getViewWidth() - dip2px(TimeTableNumWidth))) / weeknum);
                    mWeekName.setHeight(dip2px(TimeTableWeekNameHeight));
                    mWeekName.setGravity(Gravity.CENTER);
                    mWeekName.setTextSize(16);
                    mWeekName.setText(weekname[i - 1]);
                    mHoriView.addView(mWeekName);
                    mHorizontalWeekLayout.addView(mHoriView);

                    List<TimeTableModel> mListMon = new ArrayList<>();
                    for (TimeTableModel timeTableModel : mListTimeTable) {
                        if (timeTableModel.getWeek() == i) {
                            mListMon.add(timeTableModel);
                        }
                    }
                    LinearLayout mLayout = getTimeTableView(mListMon, i);
                    mLayout.setOrientation(VERTICAL);
                    ViewGroup.LayoutParams linearParams = new ViewGroup.LayoutParams((getViewWidth() - dip2px(20)) / weeknum, LayoutParams.FILL_PARENT);
                    mLayout.setLayoutParams(linearParams);
                    mLayout.setWeightSum(1);
                    mVerticalWeekLaout.addView(mLayout);
                    break;

                default:
                    break;
            }
            TextView l = new TextView(getContext());
            l.setHeight(dip2px(TimeTableHeight * maxnum) + maxnum * 2);
            l.setWidth(2);
            l.setBackgroundColor(getResources().getColor(R.color.view_line));
            mVerticalWeekLaout.addView(l);
            mHorizontalWeekLayout.addView(getWeekVerticalLine());
        }
        addView(mHorizontalWeekLayout);
        addView(getWeekTransverseLine());
        addView(mVerticalWeekLaout);
        addView(getWeekTransverseLine());
    }

    private int getViewWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private View addStartView(int startnum, final int week, final int start) {
        LinearLayout mStartView = new LinearLayout(getContext());
        mStartView.setOrientation(VERTICAL);
        for (int i = 1; i < startnum; i++) {
            TextView mTime = new TextView(getContext());
            mTime.setGravity(Gravity.CENTER);
            mTime.setHeight(dip2px(TimeTableHeight));
            mTime.setWidth(dip2px(TimeTableHeight));
            mStartView.addView(mTime);
            mStartView.addView(getWeekTransverseLine());
            final int num = i;
//            mTime.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getContext(), week + "요일" + (start + num) + "교시", Toast.LENGTH_SHORT).show();
//                }
//            });
            mTime.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent itAddSechedule = new Intent(getContext(),AddScheduleActivity.class);
                    itAddSechedule.putExtra("type","new");
                    itAddSechedule.putExtra("week",week);
                    itAddSechedule.putExtra("startnum",start+num);
                    getContext().startActivity(itAddSechedule);
                    return false;
                }
            });

        }
        return mStartView;
    }

    private LinearLayout getTimeTableView(List<TimeTableModel> model, int week) {
        LinearLayout mTimeTableView = new LinearLayout(getContext());
        mTimeTableView.setOrientation(VERTICAL);
        int modesize = model.size();
        if (modesize <= 0) {
            mTimeTableView.addView(addStartView(maxnum + 1, week, 0));
        } else {
            for (int i = 0; i < modesize; i++) {
                if (i == 0) {
                    mTimeTableView.addView(addStartView(model.get(0).getStartnum(), week, 0));
                    mTimeTableView.addView(getMode(model.get(0)));
                } else if (model.get(i).getStartnum() - model.get(i - 1).getStartnum() > 0) {
                    mTimeTableView.addView(addStartView(model.get(i).getStartnum() - model.get(i - 1).getEndnum(), week, model.get(i - 1).getEndnum()));
                    mTimeTableView.addView(getMode(model.get(i)));
                }
                if (i + 1 == modesize) {
                    mTimeTableView.addView(addStartView(maxnum - model.get(i).getEndnum(), week, model.get(i).getEndnum()));
                }
            }
        }
        return mTimeTableView;
    }

    @SuppressWarnings("deprecation")
    private View getMode(final TimeTableModel model) {
        LinearLayout mTimeTableView = new LinearLayout(getContext());
        mTimeTableView.setOrientation(VERTICAL);
        TextView mTimeTableNameView = new TextView(getContext());
        int num = model.getEndnum() - model.getStartnum();
        mTimeTableNameView.setHeight(dip2px((num + 1) * TimeTableHeight) + num * 2);
        mTimeTableNameView.setTextColor(getContext().getResources().getColor(
                android.R.color.white));
        mTimeTableNameView.setWidth(dip2px(50));
        mTimeTableNameView.setTextSize(16);
        mTimeTableNameView.setGravity(Gravity.CENTER);
        mTimeTableNameView.setText(model.getName() + "\n" + model.getClassroom());
        mTimeTableView.addView(mTimeTableNameView);
        mTimeTableView.addView(getWeekTransverseLine());
        mTimeTableView.setBackgroundDrawable(getContext().getResources()
                .getDrawable(colors[getColorNum(model.getName())]));
//        mTimeTableView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), model.getName() + "@" + model.getClassroom(), Toast.LENGTH_SHORT).show();
//            }
//        });
        mTimeTableView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent itAddSechedule = new Intent(getContext(),AddScheduleActivity.class);
                itAddSechedule.putExtra("type","old");
                itAddSechedule.putExtra("startnum",model.getStartnum());
                itAddSechedule.putExtra("endnum",model.getEndnum());
                itAddSechedule.putExtra("week",model.getWeek());
                itAddSechedule.putExtra("name",model.getName());
                itAddSechedule.putExtra("teacher",model.getTeacher());
                itAddSechedule.putExtra("classroom",model.getClassroom());
                getContext().startActivity(itAddSechedule);
                return false;
            }
        });
        return mTimeTableView;
    }

    public int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setTimeTable(List<TimeTableModel> mlist) {
        this.mListTimeTable = mlist;
        for (TimeTableModel timeTableModel : mlist) {
            addTimeName(timeTableModel.getName());
        }
        initView();
        invalidate();
    }

    private void addTimeName(String name) {
        boolean isRepeat = true;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                isRepeat = true;
                break;
            } else {
                isRepeat = false;
            }
        }
        if (!isRepeat) {
            colorStr[colornum] = name;
            colornum++;
        }
    }

    public static int getColorNum(String name) {
        int num = 0;
        for (int i = 0; i < 15; i++) {
            if (name.equals(colorStr[i])) {
                num = i;
            }
        }
        return num;
    }
}

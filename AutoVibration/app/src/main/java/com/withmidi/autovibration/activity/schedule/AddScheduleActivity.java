package com.withmidi.autovibration.activity.schedule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.withmidi.autovibration.R;
import com.withmidi.autovibration.db.SQLManager;
import com.withmidi.autovibration.util.TimeTableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HunJin on 2016-02-13.
 * 컬럼 개수, 로우 개수 만큼의 시간표 만들거임
 * 귀찮아서 체크박스 안함
 */
public class AddScheduleActivity extends AppCompatActivity implements View.OnClickListener {
    SQLManager sqlManager;
    private List<TimeTableModel> mList = new ArrayList<TimeTableModel>();
    String type = "";

    int startnum = 0;
    int endnum = 0;
    int week = 0;
    String name = "";
    String teacher = "";
    String classroom = "";

    EditText edtClassName, edtClassPro, edtClassRoom;
    RadioGroup rgDay;
    RadioButton rbMon, rbTue, rbWed, rbThu, rbFri, rbSat, rbSun;
    Button btnStart, btnEnd;
    ImageButton btnOk, btnCancel;
    RadioGroup rgMode;
    RadioButton rbDef, rbBell, rbVib, rbNoBell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        //idea_id 받기
        getIntentInfo();

        // DB에서 schedule 가져오기
        getDaySchedule();

        initializeLayout();
        
        // 임의값 넣기
        btnStart.setText(startnum+"");
        if(startnum+3>=9){
            btnEnd.setText(9 + "");
        }else{
            btnEnd.setText(startnum+3 + "");
        }


        initializeSetting();

        setListener();
    }


    private void getDaySchedule() {
//        sqlManager = new SQLManager(getApplicationContext(), "autovibration.db", null, 1);
        sqlManager = ScheduleActivity.sqlManager;

        // schedule select query
        Cursor cursor = sqlManager.getDaySchedule(week);

        while (cursor.moveToNext()) {
            mList.add(new TimeTableModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
        }
    }

    private void getIntentInfo() {
        Intent itReceive = getIntent();
        type = itReceive.getExtras().getString("type");
        if (type.equals("old")) {
            endnum = itReceive.getExtras().getInt("endnum");
            name = itReceive.getExtras().getString("name");
            teacher = itReceive.getExtras().getString("teacher");
            classroom = itReceive.getExtras().getString("classroom");
        }
        week = itReceive.getExtras().getInt("week");
        startnum = itReceive.getExtras().getInt("startnum");
    }


    private void setListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBlankInput()) {
                    Toast.makeText(AddScheduleActivity.this, "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(type.equals("old")) {

                } else {
                    // 중복 체크
                    if (!checkScheduleOverlap()) {
                        Toast.makeText(AddScheduleActivity.this, "올바른 시간표가 아닙니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 저장
                checkSaveType();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    /**
     * 선택한 week, startnum, endnum 이 중복되는지 체크하는 메소드
     *
     * @return
     */
    public boolean checkScheduleOverlap() {

        //week, startnum, endnum 넘겼을 때 해당 요일의 과목과 겹치는지 체크
        for (TimeTableModel ttm : mList) {
            int size = ttm.getEndnum() - ttm.getStartnum();
            for (int i = 0; i < size + 1; i++) {
                if (ttm.getStartnum() + i >= Integer.valueOf(btnStart.getText().toString()) && ttm.getStartnum() + i <= Integer.valueOf(btnEnd.getText().toString())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 신규,수정을 구분해 저장하는 메소드
     */
    private void checkSaveType() {
        int startnum1 = Integer.valueOf(btnStart.getText().toString());
        int endnum1 = Integer.valueOf(btnEnd.getText().toString());

        String day = ((RadioButton) findViewById(rgDay.getCheckedRadioButtonId())).getText().toString();
        int week1 = 0;
        if (day.equals("월")) {
            week1 = 1;
        } else if (day.equals("화")) {
            week1 = 2;
        } else if (day.equals("수")) {
            week1 = 3;
        } else if (day.equals("목")) {
            week1 = 4;
        } else if (day.equals("금")) {
            week1 = 5;
        } else if (day.equals("토")) {
            week1 = 6;
        } else if (day.equals("일")) {
            week1 = 7;
        }

        String name1 = edtClassName.getText().toString();
        String teacher1 = edtClassPro.getText().toString();
        String classroom1 = edtClassRoom.getText().toString();

        if (type.equals("old")) {
            // schedule update query
            sqlManager.update("UPDATE `schedule` SET `startnum`='" + startnum1 + "', `endnum`='" + endnum1 + "', `week`='" + week1 + "', `name`='" + name1 + "', `teacher`='" + teacher1 + "', `classroom`='" + classroom1 + "' WHERE `week`='" + week1 + "' AND `startnum`='" + startnum1 + "'");
        } else {
            // schedule insert query
            Log.e("insert", startnum + "d" + endnum + "s" + week + name + teacher + classroom);
            sqlManager.insert("INSERT INTO `schedule` (startnum,endnum,week,name,teacher,classroom) VALUES ('" + startnum1 + "', '" + endnum1 + "', '" + week1 + "', '" + name1 + "', '" + teacher1 + "', '" + classroom1 + "')");
        }
        finish();
    }

    /**
     * 입력하지 않은 input 체크하는 메소드
     *
     * @return
     */
    private boolean checkBlankInput() {
        if (edtClassName.length() == 0 || edtClassPro.length() == 0 || edtClassRoom.length() == 0) {
            return false;
        }

        return true;
    }


    private void initializeLayout() {
        edtClassName = (EditText) findViewById(R.id.edtClassName);
        edtClassPro = (EditText) findViewById(R.id.edtClassPro);
        edtClassRoom = (EditText) findViewById(R.id.edtClassRoom);

        rgDay = (RadioGroup) findViewById(R.id.rgDay);

        rbMon = (RadioButton) findViewById(R.id.rbMon);
        rbTue = (RadioButton) findViewById(R.id.rbTue);
        rbWed = (RadioButton) findViewById(R.id.rbWed);
        rbThu = (RadioButton) findViewById(R.id.rbThu);
        rbFri = (RadioButton) findViewById(R.id.rbFri);
        rbSat = (RadioButton) findViewById(R.id.rbSat);
        rbSun = (RadioButton) findViewById(R.id.rbSun);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnEnd = (Button) findViewById(R.id.btnEnd);

        btnOk = (ImageButton) findViewById(R.id.btnOk);
        btnCancel = (ImageButton) findViewById(R.id.btnCancel);

        rgMode = (RadioGroup) findViewById(R.id.rgMode);

        rbDef = (RadioButton) findViewById(R.id.rbDef);
        rbBell = (RadioButton) findViewById(R.id.rbBell);
        rbVib = (RadioButton) findViewById(R.id.rbVib);
        rbNoBell = (RadioButton) findViewById(R.id.rbNoBell);

        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
    }

    private void initializeSetting() {
        if (type.equals("old")) {
            edtClassName.setText(name);
            edtClassPro.setText(teacher);
            edtClassRoom.setText(classroom);

            btnStart.setText(startnum + "");
            btnEnd.setText(endnum + "");
        }

        switch (week) {
            case 1:
                rbMon.setChecked(true);
                break;
            case 2:
                rbTue.setChecked(true);
                break;
            case 3:
                rbWed.setChecked(true);
                break;
            case 4:
                rbThu.setChecked(true);
                break;
            case 5:
                rbFri.setChecked(true);
                break;
            case 6:
                rbSat.setChecked(true);
                break;
            case 7:
                rbSun.setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart: {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                final String item[] = getResources().getStringArray(R.array.row);
                alertDialogBuilder.setTitle("선택 목록 대화 상자");
                alertDialogBuilder.setItems(R.array.row, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // 프로그램을 종료한다
                        Toast.makeText(getApplicationContext(), item[id] + " 선택했습니다.", Toast.LENGTH_SHORT).show();
                        btnStart.setText(item[id]);
                        dialog.dismiss();
                    }
                });
                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();
                break;
            }
            case R.id.btnEnd: {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                final String item[] = getResources().getStringArray(R.array.row);
                alertDialogBuilder.setTitle("선택 목록 대화 상자");
                alertDialogBuilder.setItems(R.array.row, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // 프로그램을 종료한다
                        Toast.makeText(getApplicationContext(), item[id] + " 선택했습니다.", Toast.LENGTH_SHORT).show();
                        btnEnd.setText(item[id]);
                        dialog.dismiss();
                    }
                });
                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();
                break;
            }
        }
    }

}

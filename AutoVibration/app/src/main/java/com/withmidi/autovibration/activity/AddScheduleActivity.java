package com.withmidi.autovibration.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.withmidi.autovibration.R;

/**
 * Created by HunJin on 2016-02-13.
 * 컬럼 개수, 로우 개수 만큼의 시간표 만들거임
 * 귀찮아서 체크박스 안함
 */
public class AddScheduleActivity extends AppCompatActivity {

    EditText edtName, edtLow, edtColumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        edtName = (EditText) findViewById(R.id.edtScheduleName);
        edtLow = (EditText) findViewById(R.id.edtScheduleLow);
        edtColumn = (EditText) findViewById(R.id.edtScheduleColumn);

    }

    public void onDialogClick(View v) {
        switch (v.getId()) {
            case R.id.btnMakeColumn: {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);

                final String item[] = getResources().getStringArray(R.array.column);
                alertDialogBuilder.setTitle("선택 목록 대화 상자");
                alertDialogBuilder.setItems(R.array.column,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                // 프로그램을 종료한다
                                Toast.makeText(getApplicationContext(),
                                        item[id] + " 선택했습니다.",
                                        Toast.LENGTH_SHORT).show();
                                edtColumn.setText(item[id]);
                                dialog.dismiss();
                            }
                        });
                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();
                break;
            }
            case R.id.btnMakeLow: {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);

                final String item2[] = getResources().getStringArray(R.array.row);
                alertDialogBuilder.setTitle("선택 목록 대화 상자");
                alertDialogBuilder.setItems(R.array.row,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                // 프로그램을 종료한다
                                Toast.makeText(getApplicationContext(),
                                        item2[id] + " 선택했습니다.",
                                        Toast.LENGTH_SHORT).show();
                                edtLow.setText(item2[id]+"");
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

    public void onScheduleClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave : {
                Intent it = new Intent(getApplicationContext(),ScheduleActivity.class);
                it.putExtra("column",edtColumn.getText().toString().trim());
                it.putExtra("low",edtLow.getText().toString().trim());
                startActivity(it);
                break;
            }
            case R.id.btnCancel : {
                finish();
                break;
            }
        }
    }
}

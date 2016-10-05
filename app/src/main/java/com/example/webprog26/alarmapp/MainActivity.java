package com.example.webprog26.alarmapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.webprog26.alarmapp.models.Alarm;
import com.example.webprog26.alarmapp.pickers.TimeDialog;
import com.example.webprog26.alarmapp.providers.AlarmProvider;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TimeDialog.TimeCommunicator{

    private static final String TAG = "AlarmAppMain";

    private RecyclerView mAlarmsRecyclerView;
    private Button mBtnAddAlarm;

    private AlarmProvider mAlarmProvider;
    private List<Alarm> mAlarmList;

    private static final String TIME_DIALOG_TAG = "timeDialog";
    public static final int TIME_PICKER_REQUEST_CODE = 1;

    private static final String ALARM_STATE_ON = "true";
    private static final String ALARM_STATE_OFF = "false";
    private static final String ALARM_REPEAT_ON = "true";
    private static final String ALARM_REPEAT_OFF = "false";
    private static final String ALARM_VIBRATION_ON = "true";
    private static final String ALARM_VIBRATION_OFF = "false";

    //for test
    private static final String ALARM_MELODY_FILEPATH = "filepath";
    private static final String ALARM_MELODY_TITLE = "Funny melody";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlarmProvider = new AlarmProvider(this);
        mAlarmList = new LinkedList<>();

        mAlarmsRecyclerView = (RecyclerView) findViewById(R.id.alarmsRecyclerView);

        mBtnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
        mBtnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog timeDialog = new TimeDialog();
                timeDialog.show(getSupportFragmentManager(), TIME_DIALOG_TAG);
            }
        });
    }

    @Override
    public void sendTime(int requestCode, int hour, int minute) {
        if(requestCode == TIME_PICKER_REQUEST_CODE)
        {

        }
    }

    private boolean stringToBool(String s)
    {
        return Boolean.valueOf(s);
    }
}

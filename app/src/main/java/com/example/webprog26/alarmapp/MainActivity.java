package com.example.webprog26.alarmapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.webprog26.alarmapp.adapters.AlarmsListAdapter;
import com.example.webprog26.alarmapp.db.SQLiteHelper;
import com.example.webprog26.alarmapp.interfaces.OnAlarmsListItemClickListener;
import com.example.webprog26.alarmapp.models.Alarm;
import com.example.webprog26.alarmapp.pickers.TimeDialog;
import com.example.webprog26.alarmapp.providers.AlarmProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TimeDialog.TimeCommunicator{

    private static final String TAG = "AlarmAppMain";

    private RecyclerView mAlarmsRecyclerView;
    private Button mBtnAddAlarm;

    private SQLiteHelper mSqLiteHelper;
    private AlarmProvider mAlarmProvider;
    private List<Alarm> mAlarmList;
    private AlarmsListAdapter mAlarmsListAdapter;

    //TimePickerDialog constants
    private static final String TIME_DIALOG_TAG = "timeDialog";
    public static final int TIME_PICKER_REQUEST_CODE = 1;

    //Newly-created Alarm initialization constants
    private static final String ALARM_STATE_ACTIVE = "true";
    private static final String ALARM_STATE_NOT_ACTIVE = "false";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSqLiteHelper = new SQLiteHelper(this);
        mAlarmProvider = new AlarmProvider(this, mSqLiteHelper);

        mAlarmList = mAlarmProvider.getAlarmList();

        Log.i(TAG, "Alarms received from DB with AlarmProvider:\n");
        for(Alarm alarm: mAlarmList)
        {
            Log.i(TAG, alarm.toString());
        }

        mAlarmsRecyclerView = (RecyclerView) findViewById(R.id.alarmsRecyclerView);
        mAlarmsListAdapter = new AlarmsListAdapter(this, mAlarmList, new OnAlarmsListItemClickListener() {
            @Override
            public void onAlarmsListItemClick(Alarm alarm) {
                Log.i(TAG, "Clicked alarm: " + alarm.toString());
            }
        }, mAlarmProvider);
        initAlarmsRecyclerView(mAlarmsRecyclerView, mAlarmsListAdapter);

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
            Log.i(TAG, "time: " + hour + ":" + minute);
            Alarm alarm = new Alarm(hour,
                                    minute,
                                    ALARM_STATE_ACTIVE);
            alarm.setAlarmId(mAlarmProvider.insertAlarmToDB(alarm));
            mAlarmsListAdapter.addAlarmToAdapterList(alarm);
            Log.i(TAG, alarm.toString());
        }
    }

    /**
     * Initializes RecyclerView with LayoutManager, ItemAnimator and adapter
     * @param recyclerView
     * @param adapter
     */
    private void initAlarmsRecyclerView(RecyclerView recyclerView, AlarmsListAdapter adapter)
    {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}

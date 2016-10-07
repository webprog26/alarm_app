package com.example.webprog26.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.webprog26.alarmapp.adapters.AlarmAdapter;
import com.example.webprog26.alarmapp.db.SQLiteHelper;
import com.example.webprog26.alarmapp.models.Alarm;
import com.example.webprog26.alarmapp.pickers.TimeDialog;
import com.example.webprog26.alarmapp.providers.AlarmProvider;

public class MainActivity extends AppCompatActivity implements TimeDialog.TimeCommunicator, LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "AlarmAppMain";

    private RecyclerView mAlarmsRecyclerView;
    private Button mBtnAddAlarm;

    private SQLiteHelper mSqLiteHelper;
    private AlarmProvider mAlarmProvider;


    public static final String DB_CHANGED_ACTION = "alarms_db_changed";

    //TimePickerDialog constants
    private static final String TIME_DIALOG_TAG = "timeDialog";
    public static final int TIME_PICKER_REQUEST_CODE = 1;

    //Newly-created Alarm initialization constants
    private static final String ALARM_STATE_ACTIVE = "true";
    private static final String ALARM_STATE_NOT_ACTIVE = "false";
    public static final String ALARM_REPEAT_STATE_ACTIVE = "true";
    public static final String ALARM_REPEAT_STATE_NOT_ACTIVE = "false";
    public static final String ALARM_VIBRATION_STATE_ACTIVE = "true";
    public static final String ALARM_VIBRATION_STATE_NOT_ACTIVE = "false";
    public static final String DAY_ACTIVE = "true";
    public static final String DAY_NOT_ACTIVE = "false";


    public static final int ALARMS_LOADER_ID = 101;

    private AlarmAdapter mAlarmAdapter;
    private LoadReceiver mLoadReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadReceiver = new LoadReceiver();
        registerReceiver(mLoadReceiver, new IntentFilter(DB_CHANGED_ACTION));

        mSqLiteHelper = new SQLiteHelper(this);
        mAlarmProvider = new AlarmProvider(this, mSqLiteHelper);
        Cursor cursor = mAlarmProvider.getAllAlarmsInDB();
        mAlarmAdapter = new AlarmAdapter(cursor, this, mAlarmProvider);


        mAlarmsRecyclerView = (RecyclerView) findViewById(R.id.alarmsRecyclerView);
        mAlarmsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAlarmsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAlarmsRecyclerView.setAdapter(mAlarmAdapter);


        mBtnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
        mBtnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog timeDialog = new TimeDialog();
                timeDialog.show(getSupportFragmentManager(), TIME_DIALOG_TAG);
            }
        });

        getSupportLoaderManager().initLoader(ALARMS_LOADER_ID, null, this);
    }

    @Override
    public void sendTime(int requestCode, int hour, int minute) {
        if(requestCode == TIME_PICKER_REQUEST_CODE)
        {
            Log.i(TAG, "time: " + hour + ":" + minute);
            Alarm.Builder builder = Alarm.newBuilder();
                builder.setAlarmHour(hour)
                        .setAlarmMinutes(minute)
                        .setAlarmState(ALARM_STATE_ACTIVE)
                        .setAlarmRepeatOn(ALARM_REPEAT_STATE_ACTIVE)
                        .setVibrationStateOn(ALARM_VIBRATION_STATE_ACTIVE)
                        .setMelodyFilePath("test_melody_path")
                        .setMelodyFileTitle("test_melody_title")
                        .setMOActiveState(DAY_ACTIVE)
                        .setTUActiveState(DAY_ACTIVE)
                        .setWEActiveState(DAY_ACTIVE)
                        .setTHActiveState(DAY_ACTIVE)
                        .setFRActiveState(DAY_ACTIVE)
                        .setSAActiveState(DAY_ACTIVE)
                        .setSUActiveState(DAY_ACTIVE);
            Alarm alarm = builder.build();
            mAlarmProvider.insertAlarmToDB(alarm);

            getSupportLoaderManager().getLoader(ALARMS_LOADER_ID).forceLoad();
            Log.i(TAG, alarm.toString());
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AlarmLoader(this, mAlarmProvider);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(TAG, "Loading finished...");
        mAlarmAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public class LoadReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() == DB_CHANGED_ACTION)
            {
                getSupportLoaderManager().getLoader(ALARMS_LOADER_ID).forceLoad();
                Log.i(TAG, "reseived broadcast " + intent.getAction());
            }
        }
    }

    public static class AlarmLoader extends CursorLoader {

        private AlarmProvider mAlarmProvider;

        public AlarmLoader(Context context, AlarmProvider alarmProvider) {
            super(context);
            this.mAlarmProvider = alarmProvider;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            Log.i(TAG, "startLoading...");
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = mAlarmProvider.getAllAlarmsInDB();
            return cursor;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoadReceiver != null)
        {
            unregisterReceiver(mLoadReceiver);
            mLoadReceiver = null;
        }
    }
}

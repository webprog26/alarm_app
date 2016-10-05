package com.example.webprog26.alarmapp.providers;

import android.app.Activity;
import android.content.ContentValues;

import com.example.webprog26.alarmapp.db.SQLiteHelper;
import com.example.webprog26.alarmapp.models.Alarm;

/**
 * Created by webprog26 on 05.10.2016.
 */
public class AlarmProvider {
    private Activity mActivity;
    private SQLiteHelper mSqLiteHelper;

    public AlarmProvider(Activity mActivity) {
        this.mActivity = mActivity;
        this.mSqLiteHelper = new SQLiteHelper(mActivity);
    }

    public long insertAlarmToDB(Alarm alarm)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.ALARM_HOUR, alarm.getAlarmHour());
        contentValues.put(SQLiteHelper.ALARM_MINUTE, alarm.getAlarmMinute());
        contentValues.put(SQLiteHelper.IS_ALARM_ACTIVE, alarm.isAlarmStateActive());
        contentValues.put(SQLiteHelper.IS_REPEAT_ON, alarm.isRepeatStateActive());
        contentValues.put(SQLiteHelper.IS_VIBRATION_ON, alarm.isVibrationStateActive());
        contentValues.put(SQLiteHelper.MELODY_FILE_PATH, alarm.getFileMelodyPath());
        contentValues.put(SQLiteHelper.MELODY_FILE_TITLE, alarm.getFileMelodyTitle());

        return mSqLiteHelper.getWritableDatabase().insert(SQLiteHelper.ALARMS_TABLE_TITLE, null, contentValues);
    }
}

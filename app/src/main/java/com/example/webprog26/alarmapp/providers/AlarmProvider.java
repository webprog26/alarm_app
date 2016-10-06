package com.example.webprog26.alarmapp.providers;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.webprog26.alarmapp.MainActivity;
import com.example.webprog26.alarmapp.db.SQLiteHelper;
import com.example.webprog26.alarmapp.models.Alarm;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by webprog26 on 06.10.2016.
 */
public class AlarmProvider {

    private Activity mActivity;
    private SQLiteHelper mSqLiteHelper;

    public AlarmProvider(Activity mActivity, SQLiteHelper mSqLiteHelper) {
        this.mActivity = mActivity;
        this.mSqLiteHelper = mSqLiteHelper;
    }

    /**
     * Inserts newly-created Alarm to DB
     * @param alarm
     * @return long alarmID
     */
    public long insertAlarmToDB(Alarm alarm)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.ALARM_HOUR, alarm.getHour());
        contentValues.put(SQLiteHelper.ALARM_MINUTE, alarm.getMinutes());
        contentValues.put(SQLiteHelper.IS_ALARM_ACTIVE, alarm.getAlarmState());

        return mSqLiteHelper.getWritableDatabase().insert(SQLiteHelper.ALARMS_TABLE_TITLE, null, contentValues);
    }

    public Cursor getAllAlarmsInDB()
    {
        return mSqLiteHelper.getReadableDatabase().query(SQLiteHelper.ALARMS_TABLE_TITLE, null, null, null, null, null, SQLiteHelper.ALARM_ID);
    }

//    /**
//     * Gets all the Alarms from DB
//     * @return List<Alarm> alarmList
//     */
//    public List<Alarm> getAlarmList()
//    {
//        List<Alarm> alarmsList = new LinkedList<>();
//
//        Cursor cursor = mSqLiteHelper.getReadableDatabase().query(SQLiteHelper.ALARMS_TABLE_TITLE, null, null, null, null, null, SQLiteHelper.ALARM_ID);
//
//        while(cursor.moveToNext())
//        {
//            Alarm alarm = new Alarm(
//                            cursor.getLong(cursor.getColumnIndex(SQLiteHelper.ALARM_ID)),
//                            cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_HOUR)),
//                            cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_MINUTE)),
//                            cursor.getString(cursor.getColumnIndex(SQLiteHelper.IS_ALARM_ACTIVE))
//            );
//
//            alarmsList.add(alarm);
//        }
//
//        return alarmsList;
//    }

    /**
     * Deletes alarm with received alarmId from DB
     * @param alarmId
     * @return int itemsDeleted
     */
    public int deleteAlarm(long alarmId)
    {

        return mSqLiteHelper.getWritableDatabase().delete(SQLiteHelper.ALARMS_TABLE_TITLE, SQLiteHelper.ALARM_ID + " = " + alarmId, null);
    }
}

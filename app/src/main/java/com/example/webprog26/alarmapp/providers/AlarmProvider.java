package com.example.webprog26.alarmapp.providers;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.webprog26.alarmapp.MainActivity;
import com.example.webprog26.alarmapp.db.SQLiteHelper;
import com.example.webprog26.alarmapp.models.Alarm;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by webprog26 on 06.10.2016.
 */
public class AlarmProvider {

    private static final String TAG = "AlarmProvider";

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
        contentValues.put(SQLiteHelper.IS_REPEAT_ON, alarm.getAlarmRepeatState());
        contentValues.put(SQLiteHelper.IS_VIBRATION_ON, alarm.getAlarmVibrationOn());
        contentValues.put(SQLiteHelper.MO, alarm.getMO());
        contentValues.put(SQLiteHelper.TU, alarm.getTU());
        contentValues.put(SQLiteHelper.WE, alarm.getWE());
        contentValues.put(SQLiteHelper.TH, alarm.getTH());
        contentValues.put(SQLiteHelper.FR, alarm.getFR());
        contentValues.put(SQLiteHelper.SA, alarm.getSA());
        contentValues.put(SQLiteHelper.SU, alarm.getSU());

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

    public int updateAlarmActiveState(long alarmId, String alarmActiveState)
    {
        String strFilter = SQLiteHelper.ALARM_ID + " = " + alarmId;
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.IS_ALARM_ACTIVE, alarmActiveState);

        return mSqLiteHelper.getWritableDatabase().update(SQLiteHelper.ALARMS_TABLE_TITLE, contentValues, strFilter, null);
    }

    public int updateAlarmChbState(long alarmId, String columnName, String alarmRepeatState)
    {
        String strFilter = SQLiteHelper.ALARM_ID + " = " + alarmId;

        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName, alarmRepeatState);

        return mSqLiteHelper.getWritableDatabase().update(SQLiteHelper.ALARMS_TABLE_TITLE, contentValues, strFilter, null);
    }

    public int updateDayActiveState(long alarmId, String dayColumnTitle, String dayActiveState)
    {
        Log.i(TAG, "in AlarmProvider alarm id is " + alarmId);

        String strFilter = SQLiteHelper.ALARM_ID + " = " + alarmId;

        ContentValues contentValues = new ContentValues();
        contentValues.put(dayColumnTitle, dayActiveState);

        return mSqLiteHelper.getWritableDatabase().update(SQLiteHelper.ALARMS_TABLE_TITLE, contentValues, strFilter, null);

    }

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

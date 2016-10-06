package com.example.webprog26.alarmapp.providers;

import android.app.Activity;
import android.content.ContentValues;
<<<<<<< HEAD
=======
import android.database.Cursor;
>>>>>>> Alarms can be now created & deleted

import com.example.webprog26.alarmapp.db.SQLiteHelper;
import com.example.webprog26.alarmapp.models.Alarm;

<<<<<<< HEAD
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
=======
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

    /**
     * Gets all the Alarms from DB
     * @return List<Alarm> alarmList
     */
    public List<Alarm> getAlarmList()
    {
        List<Alarm> alarmsList = new LinkedList<>();

        Cursor cursor = mSqLiteHelper.getReadableDatabase().query(SQLiteHelper.ALARMS_TABLE_TITLE, null, null, null, null, null, SQLiteHelper.ALARM_ID);

        while(cursor.moveToNext())
        {
            Alarm alarm = new Alarm(
                            cursor.getLong(cursor.getColumnIndex(SQLiteHelper.ALARM_ID)),
                            cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_HOUR)),
                            cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_MINUTE)),
                            cursor.getString(cursor.getColumnIndex(SQLiteHelper.IS_ALARM_ACTIVE))
            );

            alarmsList.add(alarm);
        }

        return alarmsList;
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
>>>>>>> Alarms can be now created & deleted
}

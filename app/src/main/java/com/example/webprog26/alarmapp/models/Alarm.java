package com.example.webprog26.alarmapp.models;

/**
 * Created by webprog26 on 05.10.2016.
 */
public class Alarm {

    private long mAlarmId;
    private int mHour;
    private int mMinute;
    private boolean mAlarmState;

    public Alarm(int mHour, int mMinute, boolean mAlarmState) {
        this.mHour = mHour;
        this.mMinute = mMinute;
        this.mAlarmState = mAlarmState;
    }

    public Alarm(long mAlarmId, int mHour, int mMinute, boolean mAlarmState) {
        this.mAlarmId = mAlarmId;
        this.mHour = mHour;
        this.mMinute = mMinute;
        this.mAlarmState = mAlarmState;
    }

    public long getmAlarmId() {
        return mAlarmId;
    }

    public int getmHour() {
        return mHour;
    }

    public int getmMinute() {
        return mMinute;
    }

    public boolean ismAlarmState() {
        return mAlarmState;
    }
}

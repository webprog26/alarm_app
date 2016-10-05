package com.example.webprog26.alarmapp.models;

/**
 * Created by webprog26 on 05.10.2016.
 */
public class Alarm {

    private long mAlarmId;
    private int mHour;
    private int mMinute;
    private boolean mAlarmState;
    private boolean mRepeatState;
    private boolean mVibrationState;
    private String mFileMelodyPath;
    private String mFileMelodyTitle;

    public Alarm(int mHour, int mMinute, boolean mAlarmState, boolean mRepeatState, boolean mVibrationState, String mFileMelodyPath, String mFileMelodyTitle) {
        this.mHour = mHour;
        this.mMinute = mMinute;
        this.mAlarmState = mAlarmState;
        this.mRepeatState = mRepeatState;
        this.mVibrationState = mVibrationState;
        this.mFileMelodyPath = mFileMelodyPath;
        this.mFileMelodyTitle = mFileMelodyTitle;
    }

    public Alarm(long mAlarmId, int mHour, int mMinute, boolean mAlarmState, boolean mRepeatState, boolean mVibrationState, String mFileMelodyPath, String mFileMelodyTitle) {
        this.mAlarmId = mAlarmId;
        this.mHour = mHour;
        this.mMinute = mMinute;
        this.mAlarmState = mAlarmState;
        this.mRepeatState = mRepeatState;
        this.mVibrationState = mVibrationState;
        this.mFileMelodyPath = mFileMelodyPath;
        this.mFileMelodyTitle = mFileMelodyTitle;
    }

    public long getmAlarId() {
        return mAlarmId;
    }

    public int getAlarmHour() {
        return mHour;
    }

    public int getAlarmMinute() {
        return mMinute;
    }

    public boolean isAlarmStateActive() {
        return mAlarmState;
    }

    public boolean isRepeatStateActive() {
        return mRepeatState;
    }

    public boolean isVibrationStateActive() {
        return mVibrationState;
    }

    public String getFileMelodyPath() {
        return mFileMelodyPath;
    }

    public String getFileMelodyTitle() {
        return mFileMelodyTitle;
    }
}

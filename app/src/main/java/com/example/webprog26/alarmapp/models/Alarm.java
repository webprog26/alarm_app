package com.example.webprog26.alarmapp.models;

/**
 * Created by webprog26 on 05.10.2016.
 */
public class Alarm {

    private long mAlarmId;
    private int mHour;
    private int mMinutes;
    private String mAlarmState;

    public Alarm(int mHour, int mMinutes, String mAlarmState) {
        this.mHour = mHour;
        this.mMinutes = mMinutes;
        this.mAlarmState = mAlarmState;
    }

    public Alarm(long mAlarmId, int mHour, int mMinutes, String mAlarmState) {
        this.mAlarmId = mAlarmId;
        this.mHour = mHour;
        this.mMinutes = mMinutes;
        this.mAlarmState = mAlarmState;
    }

    public void setAlarmId(long mAlarmId) {
        this.mAlarmId = mAlarmId;
    }

    public long getAlarmId() {
        return mAlarmId;
    }

    public int getHour() {
        return mHour;
    }

    public int getMinutes() {
        return mMinutes;
    }

    public String getAlarmState() {
        return mAlarmState;
    }

    //Test method to ensure that Alarm is filled with data properly

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("alarm id is " + getAlarmId() + "\n"
                                      + "alarm hour is " + getHour() + "\n"
                                      + "alarm minutes are " + getMinutes() + "\n"
                                      + "current alarm state is " + getAlarmState());
        return builder.toString();
    }
}

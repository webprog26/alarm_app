package com.example.webprog26.alarmapp.models;

/**
 * Created by webprog26 on 05.10.2016.
 */
public class Alarm {

    private long mAlarmId;
    private int mHour;
    private int mMinutes;
    private String mAlarmState;
    private String mAlarmRepeatState;
    private String mAlarmVibrationOn;
    private String mMelodyFilePath;
    private String mMelodyFileTitle;
    private String MO;
    private String TU;
    private String WE;
    private String TH;
    private String FR;
    private String SA;
    private String SU;

//    public Alarm(int mHour, int mMinutes, String mAlarmState) {
//        this.mHour = mHour;
//        this.mMinutes = mMinutes;
//        this.mAlarmState = mAlarmState;
//    }

//    public Alarm(long mAlarmId, int mHour, int mMinutes, String mAlarmState) {
//        this.mAlarmId = mAlarmId;
//        this.mHour = mHour;
//        this.mMinutes = mMinutes;
//        this.mAlarmState = mAlarmState;
//    }

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

    public String getAlarmRepeatState() {
        return mAlarmRepeatState;
    }

    public String getAlarmVibrationOn() {
        return mAlarmVibrationOn;
    }

    public String getMelodyFilePath() {
        return mMelodyFilePath;
    }

    public String getMelodyFileTitle() {
        return mMelodyFileTitle;
    }

    public String getMO() {
        return MO;
    }

    public String getTU() {
        return TU;
    }

    public String getWE() {
        return WE;
    }

    public String getTH() {
        return TH;
    }

    public String getFR() {
        return FR;
    }

    public String getSA() {
        return SA;
    }

    public String getSU() {
        return SU;
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

    public static Alarm.Builder newBuilder()
    {
        return new Alarm().new Builder();
    }

    public class Builder{

        public Alarm.Builder setAlarmHour(int hour)
        {
            Alarm.this.mHour = hour;
            return this;
        }

        public Alarm.Builder setAlarmMinutes(int minutes)
        {
            Alarm.this.mMinutes = minutes;
            return this;
        }

        public Alarm.Builder setAlarmState(String alarmState) {
            Alarm.this.mAlarmState = alarmState;
            return this;
        }

        public Alarm.Builder setAlarmRepeatOn(String alarmRepeatState)
        {
            Alarm.this.mAlarmRepeatState = alarmRepeatState;
            return this;
        }

        public Alarm.Builder setVibrationStateOn(String alarmVibrationState)
        {
            Alarm.this.mAlarmVibrationOn = alarmVibrationState;
            return this;
        }

        public Alarm.Builder setMelodyFilePath(String melodyFilePath)
        {
            Alarm.this.mMelodyFilePath = melodyFilePath;
            return this;
        }

        public Alarm.Builder setMelodyFileTitle (String melodyFileTitle)
        {
            Alarm.this.mMelodyFileTitle = melodyFileTitle;
            return this;
        }

        public Alarm.Builder setMOActiveState(String moActiveState)
        {
            Alarm.this.MO = moActiveState;
            return this;
        }

        public Alarm.Builder setTUActiveState(String tuActiveState)
        {
            Alarm.this.TU = tuActiveState;
            return this;
        }

        public Alarm.Builder setWEActiveState(String weActiveState)
        {
            Alarm.this.WE = weActiveState;
            return this;
        }

        public Alarm.Builder setTHActiveState(String thActiveState)
        {
            Alarm.this.TH = thActiveState;
            return this;
        }

        public Alarm.Builder setFRActiveState(String frActiveState)
        {
            Alarm.this.FR = frActiveState;
            return this;
        }

        public Alarm.Builder setSAActiveState(String saActiveState)
        {
            Alarm.this.SA = saActiveState;
            return this;
        }

        public Alarm.Builder setSUActiveState(String suActiveState)
        {
            Alarm.this.SU = suActiveState;
            return this;
        }

        public Alarm build()
        {
            return Alarm.this;
        }
    }
}

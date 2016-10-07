package com.example.webprog26.alarmapp.interfaces;

import android.database.Cursor;


/**
 * Created by webprog26 on 05.10.2016.
 */
public interface OnAlarmsListItemClickListener {
    /**
     * Handles click on Alarm item in list
     * @param cursor
     */
    void onAlarmsListItemClick(Cursor cursor);
}

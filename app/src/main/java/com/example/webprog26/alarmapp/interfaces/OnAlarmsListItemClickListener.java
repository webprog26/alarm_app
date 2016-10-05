package com.example.webprog26.alarmapp.interfaces;

import com.example.webprog26.alarmapp.models.Alarm;

/**
 * Created by webprog26 on 05.10.2016.
 */
public interface OnAlarmsListItemClickListener {
    /**
     * Handles click on Alarm item in list
     * @param alarm
     */
    void onAlarmsListItemClick(Alarm alarm);
}

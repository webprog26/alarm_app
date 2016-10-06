package com.example.webprog26.alarmapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webprog26.alarmapp.R;
import com.example.webprog26.alarmapp.interfaces.OnAlarmsListItemClickListener;
import com.example.webprog26.alarmapp.models.Alarm;
import com.example.webprog26.alarmapp.providers.AlarmProvider;

import java.util.List;

/**
 * Created by webprog26 on 05.10.2016.
 */
public class AlarmsListAdapter extends RecyclerView.Adapter<AlarmsListAdapter.AlarmsViewHolder> implements OnAlarmsListItemClickListener{

    private static final String TAG = "AlarmsListAdapter";

    private static final int ALARM_DELETE_SUCCESS = 1;

    public class AlarmsViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout mAlarmContainer;
        private TextView mTvAlarmTime;
        private Switch mAlarmState;
        private ImageButton mBtnDeleteAlarm;

        public AlarmsViewHolder(View itemView) {
            super(itemView);

            mAlarmContainer = (RelativeLayout) itemView.findViewById(R.id.alarmContainer);
            mTvAlarmTime = (TextView) itemView.findViewById(R.id.tvAlarmTime);
            mAlarmState = (Switch) itemView.findViewById(R.id.swAlarmState);
            mBtnDeleteAlarm = (ImageButton) itemView.findViewById(R.id.btnDeleteAlarm);
        }

        /**
         * Binds data received from DB with views to show them on the user's screen
         * @param alarm
         * @param listener
         */
        public void bind(final Alarm alarm, final OnAlarmsListItemClickListener listener)
        {
            mTvAlarmTime.setText(getStringTime(alarm));
            mAlarmState.setChecked(Boolean.valueOf(alarm.getAlarmState()));

            mBtnDeleteAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemsRemoved = mAlarmProvider.deleteAlarm(alarm.getAlarmId());
                    mAlarmList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    if(itemsRemoved == ALARM_DELETE_SUCCESS)
                    {
                        Toast.makeText(mActivity, mActivity.getResources().getString(R.string.alard_deleted_success), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mActivity, mActivity.getResources().getString(R.string.alarm_delete_failed), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAlarmsListItemClick(alarm);
                }
            });
        }

        /**
         * Formats input hour & minutes with 0 if any of them < 10
         * @param alarm
         * @return String formattedTime
         */
        private String getStringTime(Alarm alarm) {
            StringBuilder builder = new StringBuilder();

            if(alarm.getHour() < 10) builder.append("0");

            builder.append(String.valueOf(alarm.getHour()));
            builder.append(":");

            if(alarm.getMinutes() < 10) builder.append("0");
            builder.append(String.valueOf(alarm.getMinutes()));

            return builder.toString();

        }
    }

    private Activity mActivity;
    private List<Alarm> mAlarmList;
    private OnAlarmsListItemClickListener mItemClickListener;
    private AlarmProvider mAlarmProvider;

    public AlarmsListAdapter(Activity mActivity, List<Alarm> mAlarmList, OnAlarmsListItemClickListener mItemClickListener, AlarmProvider alarmProvider) {
        this.mActivity = mActivity;
        this.mAlarmList = mAlarmList;
        this.mItemClickListener = mItemClickListener;
        this.mAlarmProvider = alarmProvider;

        Log.i(TAG, "Received from MainActivity List<Alarm> size is " + mAlarmList.size());
    }

    @Override
    public AlarmsListAdapter.AlarmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.alarms_list_item, parent, false);
        return new AlarmsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlarmsListAdapter.AlarmsViewHolder holder, int position) {
        holder.bind(mAlarmList.get(position), mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mAlarmList.size();
    }

    /**
     * Add newly-created Alarm to list and show it on user's screen
     * @param alarm
     */
    public void addAlarmToAdapterList(Alarm alarm)
    {
        mAlarmList.add(alarm);
        notifyDataSetChanged();
    }

    @Override
    public void onAlarmsListItemClick(Alarm alarm) {
        //Todo
    }
}

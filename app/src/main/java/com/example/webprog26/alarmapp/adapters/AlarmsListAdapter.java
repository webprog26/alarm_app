package com.example.webprog26.alarmapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.webprog26.alarmapp.R;
import com.example.webprog26.alarmapp.interfaces.OnAlarmsListItemClickListener;
import com.example.webprog26.alarmapp.models.Alarm;

import java.util.List;

/**
 * Created by webprog26 on 05.10.2016.
 */
public class AlarmsListAdapter extends RecyclerView.Adapter<AlarmsListAdapter.AlarmsViewHolder> implements OnAlarmsListItemClickListener{

    private static final String TAG = "AlarmsListAdapter";

    public class AlarmsViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout mAlarmContainer;
        private TextView mTvAlarmTime;
        private Switch mAlarmState;

        public AlarmsViewHolder(View itemView) {
            super(itemView);

            mAlarmContainer = (RelativeLayout) itemView.findViewById(R.id.alarmContainer);
            mTvAlarmTime = (TextView) itemView.findViewById(R.id.tvAlarmTime);
            mAlarmState = (Switch) itemView.findViewById(R.id.swAlarmState);
        }

        public void bind(final Alarm alarm, final OnAlarmsListItemClickListener listener)
        {
            //Todo
        }
    }

    private Activity mActivity;
    private List<Alarm> mAlarmList;
    private OnAlarmsListItemClickListener mItemClickListener;

    public AlarmsListAdapter(Activity mActivity, List<Alarm> mAlarmList, OnAlarmsListItemClickListener mItemClickListener) {
        this.mActivity = mActivity;
        this.mAlarmList = mAlarmList;
        this.mItemClickListener = mItemClickListener;
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

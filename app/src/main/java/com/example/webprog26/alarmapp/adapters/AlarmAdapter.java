package com.example.webprog26.alarmapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webprog26.alarmapp.MainActivity;
import com.example.webprog26.alarmapp.R;
import com.example.webprog26.alarmapp.db.SQLiteHelper;
import com.example.webprog26.alarmapp.providers.AlarmProvider;

/**
 * Created by webprog26 on 06.10.2016.
 */
public class AlarmAdapter extends CursorRecyclerAdapter<AlarmAdapter.AlarmViewHolder>{

    private static final int ALARM_REMOVED_SUCCESS = 1;

    private static final String TAG = "AlarmAdapter";

    private Activity mActivity;
    private AlarmProvider mAlarmProvider;

    public AlarmAdapter(Cursor cursor, Activity activity, AlarmProvider alarmProvider) {
        super(cursor);
        this.mActivity = activity;
        this.mAlarmProvider = alarmProvider;
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.alarms_list_item, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolderCursor(final AlarmViewHolder holder, final Cursor cursor) {
        StringBuilder builder = new StringBuilder();
        builder.append(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_HOUR)));
        builder.append(":");
        builder.append(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_MINUTE)));
        holder.mTvAlarmTime.setText(builder.toString());

        holder.mSwAlarmState.setChecked(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.IS_ALARM_ACTIVE))));

        holder.mBtnDeleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "alarm is = " + getItemId(holder.getAdapterPosition()));
                int itemRemoved = mAlarmProvider.deleteAlarm(getItemId(holder.getAdapterPosition()));
                Log.i(TAG, "After removing " + holder.getAdapterPosition());
                Intent intent = new Intent(MainActivity.DB_CHANGED_ACTION);
                mActivity.sendBroadcast(intent);

                if(itemRemoved == ALARM_REMOVED_SUCCESS)
                {
                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.alarm_removed_success), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.alarm_remove_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public void onBindViewHolder(AlarmViewHolder holder, int i) {
//        super.onBindViewHolder(holder, i);
//        mCursor.moveToPosition(i);
//
//        StringBuilder builder = new StringBuilder();
//        builder.append(mCursor.getInt(mCursor.getColumnIndex(SQLiteHelper.ALARM_HOUR)));
//        builder.append(":");
//        builder.append(mCursor.getInt(mCursor.getColumnIndex(SQLiteHelper.ALARM_MINUTE)));
//        holder.mTvAlarmTime.setText(builder.toString());
//
//        holder.mSwAlarmState.setChecked(Boolean.valueOf(mCursor.getString(mCursor.getColumnIndex(SQLiteHelper.IS_ALARM_ACTIVE))));
//
//    }

    class AlarmViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvAlarmTime;
        private Switch mSwAlarmState;
        private ImageButton mBtnDeleteAlarm;

        public AlarmViewHolder(View itemView) {
            super(itemView);

            mTvAlarmTime = (TextView) itemView.findViewById(R.id.tvAlarmTime);
            mSwAlarmState = (Switch) itemView.findViewById(R.id.swAlarmState);
            mBtnDeleteAlarm = (ImageButton) itemView.findViewById(R.id.btnDeleteAlarm);
        }
    }
}

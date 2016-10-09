package com.example.webprog26.alarmapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webprog26.alarmapp.MainActivity;
import com.example.webprog26.alarmapp.R;
import com.example.webprog26.alarmapp.db.SQLiteHelper;
import com.example.webprog26.alarmapp.interfaces.OnAlarmsListItemClickListener;
import com.example.webprog26.alarmapp.providers.AlarmProvider;

/**
 * Created by webprog26 on 06.10.2016.
 */
public class AlarmAdapter extends CursorRecyclerAdapter<AlarmAdapter.AlarmViewHolder> implements OnAlarmsListItemClickListener {

    //days titles constants
    private static final String MO = "MO";
    private static final String TU = "TU";
    private static final String WE = "WE";
    private static final String TH = "TH";
    private static final String FR = "FR";
    private static final String SA = "SA";
    private static final String SU = "SU";

    @Override
    public void onAlarmsListItemClick(Cursor cursor) {
        //Todo
    }


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
        final Intent intent = new Intent(MainActivity.DB_CHANGED_ACTION);

        holder.mTvAlarmTime.setText(getStringTime(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_HOUR)), cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_MINUTE))));

        holder.mSwAlarmState.setChecked(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.IS_ALARM_ACTIVE))));

        RelativeLayout alarmItemContainer = (RelativeLayout) holder.itemView.findViewById(R.id.alarmContainer);

        disableEnableControls(holder.mSwAlarmState.isChecked(), alarmItemContainer);

        holder.mSwAlarmState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAlarmProvider.updateAlarmActiveState(getItemId(holder.getAdapterPosition()), String.valueOf(isChecked));
                mActivity.sendBroadcast(intent);
            }
        });

        holder.mBtnDeleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "alarm is = " + getItemId(holder.getAdapterPosition()));
                int itemRemoved = mAlarmProvider.deleteAlarm(getItemId(holder.getAdapterPosition()));
                Log.i(TAG, "After removing " + holder.getAdapterPosition());

                mActivity.sendBroadcast(intent);

                if(itemRemoved == ALARM_REMOVED_SUCCESS)
                {
                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.alarm_removed_success), Toast.LENGTH_SHORT).show();
                } else {
                    //This should never happen, but for the Murphy's Law next string of code is necessary
                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.alarm_remove_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.mChbAlarmRepeat.setChecked(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.IS_REPEAT_ON))));
        holder.mChbAlarmVibration.setChecked(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.IS_VIBRATION_ON))));

        final LinearLayout daysView = (LinearLayout) holder.itemView.findViewById(R.id.daysView);

        LinearLayout smallDaysActiveListContainer = (LinearLayout) holder.itemView.findViewById(R.id.smallActiveDaysList);

        if(!holder.mChbAlarmRepeat.isChecked())
        {
            daysView.setVisibility(View.GONE);
            smallDaysActiveListContainer.setVisibility(View.GONE);
        } else {
            daysView.setVisibility(View.VISIBLE);
            smallDaysActiveListContainer.setVisibility(View.VISIBLE);
        }

        holder.mChbAlarmRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAlarmProvider.updateAlarmChbState(getItemId(holder.getAdapterPosition()), SQLiteHelper.IS_REPEAT_ON, String.valueOf(isChecked));
                mActivity.sendBroadcast(intent);
            }
        });



        holder.mChbAlarmVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAlarmProvider.updateAlarmChbState(getItemId(holder.getAdapterPosition()), SQLiteHelper.IS_VIBRATION_ON, String.valueOf(isChecked));
                mActivity.sendBroadcast(intent);
            }
        });

        holder.mTvMo.setTag(cursor.getString(cursor.getColumnIndex(MO)));
        holder.mTvTu.setTag(cursor.getString(cursor.getColumnIndex(TU)));
        holder.mTvWe.setTag(cursor.getString(cursor.getColumnIndex(WE)));
        holder.mTvTh.setTag(cursor.getString(cursor.getColumnIndex(TH)));
        holder.mTvFr.setTag(cursor.getString(cursor.getColumnIndex(FR)));
        holder.mTvSa.setTag(cursor.getString(cursor.getColumnIndex(SA)));
        holder.mTvSu.setTag(cursor.getString(cursor.getColumnIndex(SU)));

        setUnderlineDecorationToActiveDay(Boolean.valueOf(holder.mTvMo.getTag().toString()), holder.mTvMo);
        setUnderlineDecorationToActiveDay(Boolean.valueOf(holder.mTvTu.getTag().toString()), holder.mTvTu);
        setUnderlineDecorationToActiveDay(Boolean.valueOf(holder.mTvWe.getTag().toString()), holder.mTvWe);
        setUnderlineDecorationToActiveDay(Boolean.valueOf(holder.mTvTh.getTag().toString()), holder.mTvTh);
        setUnderlineDecorationToActiveDay(Boolean.valueOf(holder.mTvFr.getTag().toString()), holder.mTvFr);
        setUnderlineDecorationToActiveDay(Boolean.valueOf(holder.mTvSa.getTag().toString()), holder.mTvSa);
        setUnderlineDecorationToActiveDay(Boolean.valueOf(holder.mTvSu.getTag().toString()), holder.mTvSu);

        setSmallActiveDaysVisibility(Boolean.valueOf(holder.mTvMo.getTag().toString()), holder.sMmTvMo);
        setSmallActiveDaysVisibility(Boolean.valueOf(holder.mTvTu.getTag().toString()), holder.sMmTvTu);
        setSmallActiveDaysVisibility(Boolean.valueOf(holder.mTvWe.getTag().toString()), holder.sMmTvWe);
        setSmallActiveDaysVisibility(Boolean.valueOf(holder.mTvTh.getTag().toString()), holder.sMmTvTh);
        setSmallActiveDaysVisibility(Boolean.valueOf(holder.mTvFr.getTag().toString()), holder.sMmTvFr);
        setSmallActiveDaysVisibility(Boolean.valueOf(holder.mTvSa.getTag().toString()), holder.sMmTvSa);
        setSmallActiveDaysVisibility(Boolean.valueOf(holder.mTvSu.getTag().toString()), holder.sMmTvSu);

//        Log.i(TAG, "Tag " + holder.mTvMo.getTag() + "\n");
//        Log.i(TAG, "Tag " + holder.mTvTu.getTag() + "\n");
//        Log.i(TAG, "Tag " + holder.mTvWe.getTag() + "\n");
//        Log.i(TAG, "Tag " + holder.mTvTh.getTag() + "\n");
//        Log.i(TAG, "Tag " + holder.mTvFr.getTag() + "\n");
//        Log.i(TAG, "Tag " + holder.mTvSa.getTag() + "\n");
//        Log.i(TAG, "Tag " + holder.mTvSu.getTag() + "\n");

        holder.mTvMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmProvider.updateDayActiveState(getItemId(holder.getAdapterPosition()), MO, String.valueOf(!Boolean.valueOf((String) v.getTag())));
                mActivity.sendBroadcast(intent);
            }
        });

        holder.mTvTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmProvider.updateDayActiveState(getItemId(holder.getAdapterPosition()), TU, String.valueOf(!Boolean.valueOf((String) v.getTag())));
                mActivity.sendBroadcast(intent);
            }
        });

        holder.mTvWe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmProvider.updateDayActiveState(getItemId(holder.getAdapterPosition()), WE, String.valueOf(!Boolean.valueOf((String) v.getTag())));
                mActivity.sendBroadcast(intent);
            }
        });

        holder.mTvTh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmProvider.updateDayActiveState(getItemId(holder.getAdapterPosition()), TH, String.valueOf(!Boolean.valueOf((String) v.getTag())));
                mActivity.sendBroadcast(intent);
            }
        });

        holder.mTvFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmProvider.updateDayActiveState(getItemId(holder.getAdapterPosition()), FR, String.valueOf(!Boolean.valueOf((String) v.getTag())));
                mActivity.sendBroadcast(intent);
            }
        });

        holder.mTvSa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmProvider.updateDayActiveState(getItemId(holder.getAdapterPosition()), SA, String.valueOf(!Boolean.valueOf((String) v.getTag())));
                mActivity.sendBroadcast(intent);
            }
        });

        holder.mTvSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarmProvider.updateDayActiveState(getItemId(holder.getAdapterPosition()), SU, String.valueOf(!Boolean.valueOf((String) v.getTag())));
                mActivity.sendBroadcast(intent);
            }
        });

        final RelativeLayout bottomAlarmView = (RelativeLayout) holder.itemView.findViewById(R.id.bottomAlarmView);

        holder.mBtnShowAlarmOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Show Alarm Options button clicked!");
                if(bottomAlarmView.getVisibility() == View.GONE)
                    bottomAlarmView.setVisibility(View.VISIBLE);
            }
        });

        holder.mBtnHideAlarmOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Hide Alarm Options button clicked!");
                if(bottomAlarmView.getVisibility() == View.VISIBLE)
                    bottomAlarmView.setVisibility(View.GONE);
            }
        });
    }

    private void disableEnableControls(boolean enable, ViewGroup vg){
        for (int i = 0; i < vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            if(child.getId() != R.id.swAlarmState && child.getId() != R.id.btnDeleteAlarm)
            {
                child.setEnabled(enable);
                if(child instanceof TextView)
                {
                    if(!enable)
                    {
                        ((TextView) child).setTextColor(mActivity.getResources().getColor(R.color.alarmDisbled));
                    } else {
                        ((TextView) child).setTextColor(mActivity.getResources().getColor(R.color.colorPrimaryDark));
                    }
                }
            }
            if (child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup)child);
            }
        }
    }

    private String getStringTime(int hour, int minutes) {
        StringBuilder builder = new StringBuilder();

        if(hour < 10) builder.append("0");

        builder.append(String.valueOf(hour));
        builder.append(":");

        if(minutes < 10) builder.append("0");
        builder.append(String.valueOf(minutes));

        return builder.toString();

    }

    private void setUnderlineDecorationToActiveDay(boolean isActive, TextView textView)
    {
        if(isActive)
        {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            textView.setPaintFlags(0);
        }
    }

    private void setSmallActiveDaysVisibility(boolean isActive, TextView textView)
    {
        if(isActive)
        {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvAlarmTime;
        private Switch mSwAlarmState;
        private ImageButton mBtnDeleteAlarm;
        private CheckBox mChbAlarmRepeat, mChbAlarmVibration;
        private LinearLayout mMelodyChooser;
        private ImageButton mBtnShowAlarmOptions, mBtnHideAlarmOptions;

        private TextView mTvMo, mTvTu, mTvWe, mTvTh, mTvFr, mTvSa, mTvSu;
        private TextView sMmTvMo, sMmTvTu, sMmTvWe, sMmTvTh, sMmTvFr, sMmTvSa, sMmTvSu;

        public AlarmViewHolder(View itemView) {
            super(itemView);

            mTvAlarmTime = (TextView) itemView.findViewById(R.id.tvAlarmTime);
            mSwAlarmState = (Switch) itemView.findViewById(R.id.swAlarmState);
            mBtnDeleteAlarm = (ImageButton) itemView.findViewById(R.id.btnDeleteAlarm);
            mChbAlarmRepeat = (CheckBox) itemView.findViewById(R.id.chbRepeatAlarm);
            mChbAlarmVibration = (CheckBox) itemView.findViewById(R.id.chbVibrationOn);
            mMelodyChooser = (LinearLayout) itemView.findViewById(R.id.llMelodyChooser);
            mBtnShowAlarmOptions = (ImageButton) itemView.findViewById(R.id.btnShowAlarmOptions);
            mBtnHideAlarmOptions = (ImageButton) itemView.findViewById(R.id.btnHideAlarmOptions);

            mTvMo = (TextView) itemView.findViewById(R.id.tvMo);
            mTvTu = (TextView) itemView.findViewById(R.id.tvTu);
            mTvWe = (TextView) itemView.findViewById(R.id.tvWe);
            mTvTh = (TextView) itemView.findViewById(R.id.tvTh);
            mTvFr = (TextView) itemView.findViewById(R.id.tvFr);
            mTvSa = (TextView) itemView.findViewById(R.id.tvSa);
            mTvSu = (TextView) itemView.findViewById(R.id.tvSu);

            sMmTvMo = (TextView) itemView.findViewById(R.id.sMtvMo);
            sMmTvTu = (TextView) itemView.findViewById(R.id.sMtvTu);
            sMmTvWe = (TextView) itemView.findViewById(R.id.sMtvWe);
            sMmTvTh = (TextView) itemView.findViewById(R.id.sMtvTh);
            sMmTvFr = (TextView) itemView.findViewById(R.id.sMtvFr);
            sMmTvSa = (TextView) itemView.findViewById(R.id.sMtvSa);
            sMmTvSu = (TextView) itemView.findViewById(R.id.sMtvSu);


            TextView[] daysTextViews = new TextView[]{
                mTvMo, mTvTu, mTvWe, mTvTh, mTvFr, mTvSa, mTvSu};

            TextView[] sMdaysTextViews = new TextView[]{
                    sMmTvMo, sMmTvTu, sMmTvWe, sMmTvTh, sMmTvFr, sMmTvSa, sMmTvSu};


            initDaysWithTitles(mActivity.getResources().getStringArray(R.array.daysArray), daysTextViews);
            initDaysWithTitles(mActivity.getResources().getStringArray(R.array.daysArray), sMdaysTextViews);
        }

        /**
         * Initializes days-TextViews with days titles and tags
         * @param daysTitles
         * @param daysTextViews
         */
        private void initDaysWithTitles(String[] daysTitles, TextView[] daysTextViews)
        {
            if(daysTitles.length != daysTextViews.length)
            {
                Log.i(TAG, "Arrays (daysTitles & daysTextViews)  must be equal in length to each other");
                return;
            }

            for(int i = 0; i < daysTitles.length; i++)
            {
                daysTextViews[i].setText(daysTitles[i]);
            }
        }

    }
}

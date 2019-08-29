package com.nawdroidtv.layartancep21.view;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Switch;

import com.nawdroidtv.layartancep21.R;
import com.nawdroidtv.layartancep21.pref.ReminderPref;
import com.nawdroidtv.layartancep21.reminder.ReminderDaily;
import com.nawdroidtv.layartancep21.reminder.ReminderRelease;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

import static com.nawdroidtv.layartancep21.Helper.Konstanta.DAILY_CHECKED;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.DAILY_REMINDER;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.RELEASE_CHECKED;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.RELEASE_REMINDER;

public class SettingActivity extends AppCompatActivity {

    public ReminderDaily reminderDaily;
    public ReminderRelease reminderRelease;
    public ReminderPref reminderPref;
    public SharedPreferences prefDaily, prefRelease;
    public SharedPreferences.Editor editDaily, editRelease;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swDaily)
    Switch dailyReminder;
    @BindView(R.id.swRelease)
    Switch releaseReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        reminderDaily = new ReminderDaily();
        reminderRelease = new ReminderRelease();
        reminderPref = new ReminderPref(this);
        setPreference();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @OnCheckedChanged(R.id.swDaily)
    public void setDailyReminder(boolean isChecked) {
        editDaily = prefDaily.edit();
        if (isChecked) {
            editDaily.putBoolean(DAILY_CHECKED, true);
            editDaily.apply();
            dailyReminderOn();
        } else {
            editDaily.putBoolean(DAILY_CHECKED, false);
            editDaily.apply();
            dailyReminderOff();
        }
    }

    @OnCheckedChanged(R.id.swRelease)
    public void setReleaseReminder(boolean isChecked) {
        editRelease = prefRelease.edit();
        if (isChecked) {
            editRelease.putBoolean(RELEASE_CHECKED, true);
            editRelease.apply();
            releaseReminderOn();
        } else {
            editRelease.putBoolean(RELEASE_CHECKED, false);
            editRelease.apply();
            releaseReminderOff();
        }
    }

    private void setPreference() {
        prefDaily = getSharedPreferences(DAILY_REMINDER, MODE_PRIVATE);
        boolean checkedDaily = prefDaily.getBoolean(DAILY_CHECKED, false);
        prefRelease = getSharedPreferences(RELEASE_REMINDER, MODE_PRIVATE);
        boolean checkedRelease = prefRelease.getBoolean(RELEASE_CHECKED, false);
        dailyReminder.setChecked(checkedDaily);
        releaseReminder.setChecked(checkedRelease);
    }

    private void dailyReminderOn() {
        String time = "07:00";
        String message = "Layar Tancep 21 missing you";
        reminderPref.setReminderDaily(time);
        reminderPref.setReminderDailyMessage(message);
        reminderDaily.setReminder(SettingActivity.this, DAILY_REMINDER, time, message);
    }

    private void dailyReminderOff() {
        reminderDaily.cancelReminder(SettingActivity.this);
    }

    private void releaseReminderOn() {
        String time = "08:00";
        String message = "Layar Tancep release movie";
        reminderPref.setReminderRelease(time);
        reminderPref.setReminderReleaseMessage(message);
        reminderRelease.setReminder(SettingActivity.this, RELEASE_REMINDER, time, message);
    }

    private void releaseReminderOff() {
        reminderRelease.cancelReminder(SettingActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.nawdroidtv.layartancep21.pref;

import android.content.Context;
import android.content.SharedPreferences;

import static com.nawdroidtv.layartancep21.Helper.Konstanta.KEY_DAILY;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.KEY_DAILY_MSG;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.KEY_RELEASE;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.KEY_RELEASE_MSG;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.PREF_NAME;

/**
 * Created by Irfan Nawawi on 09/02/2019.
 */

public class ReminderPref {
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor editor;

    public ReminderPref(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public void setReminderDaily(String time) {
        editor.putString(KEY_DAILY, time);
        editor.apply();
    }

    public void setReminderDailyMessage(String message) {
        editor.putString(KEY_DAILY_MSG, message);
    }

    public void setReminderRelease(String time) {
        editor.putString(KEY_RELEASE, time);
        editor.apply();
    }

    public void setReminderReleaseMessage(String message) {
        editor.putString(KEY_RELEASE_MSG, message);
    }
}

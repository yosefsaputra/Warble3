package edu.utexas.mpc.warble3.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHandler {
    public static String SHARED_PREFS_CURRENT_USER_SETTINGS = "edu.utexas.mpc.warble3.CURRENT_USER_SETTINGS";

    public static SharedPreferences getSharedPrefsCurrentUserSettings(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_CURRENT_USER_SETTINGS, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPrefsEditorCurrentUserSettings(Context context) {
        return getSharedPrefsCurrentUserSettings(context).edit();
    }
}
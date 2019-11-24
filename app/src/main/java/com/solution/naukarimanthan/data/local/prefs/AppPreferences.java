package com.solution.naukarimanthan.data.local.prefs;


import android.content.Context;
import android.content.SharedPreferences;

import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.di.annotation.PreferenceInfo;
import com.solution.naukarimanthan.utils.General;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import static com.solution.naukarimanthan.utils.AppConstants.GENERAL_DATE_FORMAT;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 */
public class AppPreferences implements PreferencesService {

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
    private static final String PREF_KEY_MENU_COUNT = "PREF_KEY_MENU_COUNT";
    private static final String PREF_KEY_STATE_CODE= "PREF_KEY_STATE_CODE";
    private static final String PREF_KEY_App_DATE = "PREF_KEY_App_DATE";
    private static final String PREF_KEY_STATE_NAME = "PREF_KEY_STATE_NAME";
    private static final String PREF_KEY_FIREBASE_ID = "PREF_KEY_FIREBASE_ID";
    private static final String PREF_KEY_IP_ADDRESS = "PREF_KEY_IP_ADDRESS";
    private static final String PREF_KEY_UNIQUE_ID = "PREF_KEY_UNIQUE_ID";


    private static final String PREF_KEY_U_NAME  = "PREF_KEY_U_NAME";
    private static final String PREF_KEY_KEY  = "PREF_KEY_KEY";
    private static final String PREF_KEY_ICON  = "PREF_KEY_ICON";
    private static final String PREF_KEY_EMAIL  = "PREF_KEY_EMAIL";
    private static final String PREF_ACCOUNT_NAME  = "PREF_ACCOUNT_NAME";
    private static final String PREF_COUNTRY_CODE  = "PREF_COUNTRY_CODE";
    private static final String PREF_LOCATION_CAPTURE  = "PREF_LOCATION_CAPTURE";

    private final SharedPreferences pref;

    @Inject
    public AppPreferences(Context context, @PreferenceInfo String prefFileName) {
        pref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return pref.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_FIRST_TIME.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        pref.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public void setMenuCount(int menuCount) {
        pref.edit().putInt(PREF_KEY_MENU_COUNT, menuCount).apply();
    }

    @Override
    public int getMenuCount() {
        return pref.getInt(PREF_KEY_MENU_COUNT, 16);
    }

    @Override
    public void setStateCode(int code) {
        pref.edit().putInt(PREF_KEY_STATE_CODE, code).apply();
    }

    @Override
    public int getStateCode() {
        return pref.getInt(PREF_KEY_STATE_CODE, 0);
    }

    @Override
    public void setAppDate(String date) {
        pref.edit().putString(PREF_KEY_App_DATE, date).apply();
    }

    @Override
    public String getAppDate() {
        return pref.getString(PREF_KEY_App_DATE, General.defaultDate());
    }

    @Override
    public String getStateName() {
        return pref.getString(PREF_KEY_STATE_NAME, "");
    }

    @Override
    public void setStateName(String stateName) {
        pref.edit().putString(PREF_KEY_STATE_NAME, stateName).apply();
    }

    @Override
    public void setFirebaseAppId(String key) {
        pref.edit().putString(PREF_KEY_FIREBASE_ID, key).apply();
    }

    @Override
    public String getFirebaseAppId() {
        return pref.getString(PREF_KEY_FIREBASE_ID, "");
    }

    @Override
    public String getIpAddress() {
        return pref.getString(PREF_KEY_IP_ADDRESS, "");
    }

    @Override
    public void setIpAddress(String ipAddress) {
        pref.edit().putString(PREF_KEY_IP_ADDRESS, ipAddress).apply();
    }

    @Override
    public String getUniqueId() {
        return pref.getString(PREF_KEY_UNIQUE_ID, "");
    }

    @Override
    public void setUniqueId(String uniqueId) {
        pref.edit().putString(PREF_KEY_UNIQUE_ID, uniqueId).apply();
    }
}

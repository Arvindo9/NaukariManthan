package com.solution.naukarimanthan.data.local.prefs;

import com.solution.naukarimanthan.data.DataManager;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 */
public interface PreferencesService {
    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    void setMenuCount(int menuCount);

    int getMenuCount();

    void setStateCode(int code);

    int getStateCode();

    void setAppDate(String date);

    String getAppDate();

    String getStateName();

    void setStateName(String stateName);

    void setFirebaseAppId(String key);

    String getFirebaseAppId();

    String getIpAddress();

    void setIpAddress(String ipAddress);

    String getUniqueId();

    void setUniqueId(String uniqueId);

}

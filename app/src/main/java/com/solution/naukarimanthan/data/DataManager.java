package com.solution.naukarimanthan.data;


import com.solution.naukarimanthan.data.local.db.DatabaseService;
import com.solution.naukarimanthan.data.local.prefs.PreferencesService;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.data.remote.APIService;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
public interface DataManager extends DatabaseService, PreferencesService, APIService {


    Flowable<List<JobsData>> getSpecialJobsFromDb(int jobsId, int pageNo, int menuId);

    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath);

    enum LoggedInMode {
        LOGGED_IN_MODE_LOGGED_FIRST_TIME(0),
        LOGGED_IN_MODE_LOGGED_OUT(1),
        LOGGED_IN_MODE_LOGGED_IN(2),
        LOGGED_IN_MODE_LOGGED_GOOGLE(3),
        LOGGED_IN_MODE_LOGGED_LINKED_IN(4);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}

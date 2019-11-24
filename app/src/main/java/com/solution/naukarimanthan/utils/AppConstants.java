/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.solution.naukarimanthan.utils;

import com.solution.naukarimanthan.BuildConfig;

import static com.solution.naukarimanthan.BuildConfig.BASE_URL;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */

public enum  AppConstants {

    INSTANCE;


    public static final String FIREBASE_NOTIFICATION_TOPIC = "NOTIFICATION_TOPIC_GENERAL";

    public static final int START_AS_JOB_ITEM_SHORT = 4;
    public static final int START_AS_JOB_ITEM_LONG = 5;
    public static final int START_AS_DEFAULT = 1;
    public static final int START_AS_SPECIAL = 2;
    public static final String KEY_TAB_ID = "KEY_TAB_ID";   //menuID
    public static final String KEY_TITLE = "KEY_TITLE";     //title or menu
    public static final String KEY_TYPE = "KEY_TYPE";       //type is for jobs type like recent or local
    public static final String KEY_TYPE_WHERE = "KEY_TYPE_WHERE";   //from where it start, like default/special
    public static final String KEY_JOB_ID = "KEY_JOB_ID";   //job id is row id
    public static final String KEY_DESCRIPTION_ID = "KEY_DESCRIPTION_ID";   //job id is row id

    public static final int RECENT_JOBS = 1;
    public static final int RECOMMENDED_JOBS = 2;
    public static final int POPULAR_JOBS = 3;
    public static final int LOCAL_JOBS = 4;
    public static final int JOBS_BY_MENU_ID = 5;

    AppConstants() {
        // This utility class is not publicly instantiable
    }


    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "aiprog.db";

    public static final long NULL_INDEX = -1L;

    public static final String PREF_NAME = "aiprog_pref";

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";

    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String STATUS_CODE_FAILED = "failed";

    public static final String STATUS_CODE_SUCCESS = "success";


    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    public static final String BASE_URL_UP_50 = "https://up50.in";

    public static final String APP_PACKAGE_NAME = "com.solution.naukarimanthan";

    public static final String UP50_PACKAGE_NAME = "com.solution.up50";

    public static final int PICK_CONTACT = 1000;

    public static final int REQUEST_CHECK_SETTINGS = 1001;

    public static final String ACCESS_TOKEN = "ba30bfcb690cfc5ce3bc0ffe8bb554d1";

    public static boolean TASK_COMPLETION = false;

    public final String WEB_URL = "catterpillars";


    public static final String GENERAL_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_INTEGER = "yyyyMMdd";

    public final String ADMOB_APP_ID = BuildConfig.DEBUG?"ca-app-pub-3940256099942544~3347511713":"ca-app-pub-8423837804323248~9919328647";
    public final String FB_ADS_PLACEMENT_ID=BuildConfig.DEBUG?"YOUR_PLACEMENT_ID":"278055979531585_278056352864881";
    public final String FB_ADS_300_250_BANNER_ID=BuildConfig.DEBUG?"YOUR_PLACEMENT_ID":"2189559427977049_2236049363328055";
    public final String FB_ADS_NORMAL_BANNER_ID=BuildConfig.DEBUG?"YOUR_PLACEMENT_ID":"2189559427977049_2236050106661314";

    public static final int GOOGLE_ADS = 1;
    public static final int FACEBOOK_ADS = 2;

}

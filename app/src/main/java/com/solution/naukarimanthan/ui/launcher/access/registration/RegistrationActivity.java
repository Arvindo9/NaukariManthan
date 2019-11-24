package com.solution.naukarimanthan.ui.launcher.access.registration;


import android.content.Context;
import android.content.Intent;

/**
 * Author       : Arvindo Mondal
 * Created on   : 28-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class RegistrationActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }
}

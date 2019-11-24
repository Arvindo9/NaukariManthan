package com.solution.naukarimanthan.base;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.di.component.DaggerAppComponent;
import com.solution.naukarimanthan.utils.Logger;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;

import static com.solution.naukarimanthan.utils.AppConstants.FIREBASE_NOTIFICATION_TOPIC;


/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class BaseApplication extends Application  implements HasActivityInjector, HasServiceInjector {

    private static final String TAG = BaseApplication.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;


    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;



    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    /**
     * Returns an {@link AndroidInjector} of {@link Service}s.
     */
    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        Logger.init();

        subscribToTopic();
    }



    private void subscribToTopic(){
        Log.d(TAG, "Subscribing to weather topic");
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic(FIREBASE_NOTIFICATION_TOPIC)
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d(TAG, msg);
                });
        // [END subscribe_topics]
    }
}

package com.solution.naukarimanthan.ui.launcher.splash;


/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
public interface SplashNavigator {

    void openWelcomeScreen();

    void openLoginActivity();

    void openHomeActivity();

    void openPlayStoreUpdate();

    void handleError(Throwable throwable);

    void openLoginWithGoogle();

    void openLoginWithLinkedIn();

    void onNetworkAvailability(boolean networkState);
}

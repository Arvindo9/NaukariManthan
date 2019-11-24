package com.solution.naukarimanthan.ui.launcher.welcome;


/**
 * Author       : Arvindo Mondal
 * Created on   : 25-12-2018
 */
public interface WelcomeNavigator {

    void onNextClick();

    void onSkipClick();

    void handleError(Throwable throwable);
}

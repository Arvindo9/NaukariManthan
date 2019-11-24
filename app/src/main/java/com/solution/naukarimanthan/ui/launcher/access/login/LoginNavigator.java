package com.solution.naukarimanthan.ui.launcher.access.login;


/**
 * Author       : Arvindo Mondal
 * Created on   : 28-12-2018
 * Email        : arvindomondal@gmail.com
 */
public interface LoginNavigator {

    void onSignUp();

    void onSignIn();

    void onSignInWithGoogle();

    void onSignInWithLinkedIn();

    void email();

    void password();
}

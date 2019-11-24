package com.solution.naukarimanthan.ui.launcher.access.login;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.di.annotation.PreferenceInfo;
import com.solution.naukarimanthan.utils.Logger;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

/**
 * Author       : Arvindo Mondal
 * Created on   : 28-12-2018
 */
public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void isValidPassword(){

    }

    public void onForgetPassword(){

    }

    public void onLogin(){
    }

    public void email(){
    }

    public void password(){
    }

    public void onSignIn(){
    }

    public void signInWithGoogle(){
    }

    public void signInWithLinkedIn(){
    }

    public void onSignUp(){
    }

}

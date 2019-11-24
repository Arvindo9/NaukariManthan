package com.solution.naukarimanthan.ui.launcher.access.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseActivity;
import com.solution.naukarimanthan.databinding.LoginActivityBinding;

import javax.inject.Inject;

/**
 * Author       : Arvindo Mondal
 * Created on   : 28-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class LoginActivity extends BaseActivity<LoginActivityBinding, LoginViewModel> implements
        LoginNavigator {

    private LoginActivityBinding binding;
    private Context context;

    @Inject
    LoginViewModel viewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    /**
     * @param binding activity class data binding
     */
    @Override
    public void getActivityBinding(LoginActivityBinding binding) {
        this.binding = binding;
    }

    /**
     * @param state Initialise any thing here before data binding
     */
    @Override
    protected void initialization(@Nullable Bundle state) {

    }

    /**
     * @param context is a Activity class context or instance
     */
    @Override
    public void getContext(Context context) {

        this.context = context;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.login_activity;
    }

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    @Override
    public int getBindingVariable() {
        return 0;
    }


    /**
     * Override for set view model
     *
     * @return view model instance
     */
    @Override
    public LoginViewModel getViewModel() {
        return viewModel;
    }

    /**
     * Do anything on onCreate after binding
     */
    @Override
    protected void init() {

    }

    @Override
    public void onSignUp() {

    }

    @Override
    public void onSignIn() {

    }

    @Override
    public void onSignInWithGoogle() {

    }

    @Override
    public void onSignInWithLinkedIn() {

    }

    @Override
    public void email() {

    }

    @Override
    public void password() {

    }

    //Validations---------------
}

package com.solution.naukarimanthan.ui.launcher.welcome;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseActivity;
import com.solution.naukarimanthan.databinding.WelcomeActivityBinding;
import com.solution.naukarimanthan.ui.dashboard.Dashboard;
import com.solution.naukarimanthan.ui.location.LocationActivity;
import com.solution.naukarimanthan.utils.Logger;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Author       : Arvindo Mondal
 * Created on   : 25-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class WelcomeActivity extends BaseActivity<WelcomeActivityBinding, WelcomeViewModel> implements
        WelcomeNavigator {

    @Inject
    WelcomeViewModel viewModel;

    @Inject
    WelcomeAdapter adapter;

    private WelcomeActivityBinding binding;
    private Context context;
    private static int mCureentPage = 0;
    private static final int NUM_PAGES = 3;
    private TextView[] mdots;
    private Handler handler = new Handler();
    private Runnable Update;
    private boolean isNextActivityStart = false;
    private final long PERIOD_MS = 1000; // time in milliseconds between successive task executions.

    public static Intent newIntent(Context context) {
        return new Intent(context, WelcomeActivity.class);
    }

    /**
     * @param binding activity class data binding
     */
    @Override
    public void getActivityBinding(WelcomeActivityBinding binding) {
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
        return R.layout.welcome_activity;
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
    public WelcomeViewModel getViewModel() {
        return viewModel;
    }

    /**
     * Do anything on onCreate after binding
     */
    @Override
    protected void init() {
        REQUEST_PERMISSION_FOR_ACTIVITY = false;
        binding.viewpager.setAdapter(adapter);
        viewModel.setNavigator(this);
        viewModel.start();

        addDots(0);
        binding.viewpager.addOnPageChangeListener(viewListener);

        handlingBackground();
    }

    private void openStartAppActivity() {
        handler.removeCallbacks(Update);
        if(!isNextActivityStart) {
            startActivity(LocationActivity.newIntent(context));
            finish();
            isNextActivityStart = true;
        }
    }

    //pager------------------------

    private void handlingBackground() {
        /*After setting the adapter use the timer */
        handler.postDelayed(Update= () -> {
            handler.postDelayed(Update,PERIOD_MS);
            if (mCureentPage == NUM_PAGES) {
                mCureentPage = 0;
                openStartAppActivity();
            }
            else {
                try {
                    binding.viewpager.setCurrentItem(mCureentPage++, true);
                }catch (NullPointerException ignore){}
            }
        },PERIOD_MS);
    }

    public void addDots(int i){

        mdots=new TextView[NUM_PAGES];
        binding.dots.removeAllViews();

        for (int x=0;x<mdots.length;x++){

            mdots[x]=new TextView(this);
            mdots[x].setText(Html.fromHtml("&#8226;"));
            mdots[x].setTextSize(35);
            mdots[x].setTextColor(getResources().getColor(R.color.gray));

            binding.dots.addView(mdots[x]);
        }
        if (mdots.length>0){

            mdots[i].setTextColor(getResources().getColor(R.color.white));

        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            mCureentPage = position;
            if (position==mdots.length-1){
                binding.nextBtn.setText(getText(R.string.start));
                binding.backBtn.setVisibility(View.GONE);
            }else{
                binding.nextBtn.setText(getText(R.string.next));
                binding.backBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onNextClick() {
        if(binding.nextBtn.getText().equals(getText(R.string.start))){
            openStartAppActivity();
        }else{
            binding.viewpager.setCurrentItem(mCureentPage+1);
        }
    }

    @Override
    public void onSkipClick() {
        openStartAppActivity();
    }

    @Override
    public void handleError(Throwable throwable) {
        Logger.e(throwable.getMessage());
        if(throwable instanceof IOException){
            runOnUiThread(() -> showToast(R.string.network_error));
        }
    }
}

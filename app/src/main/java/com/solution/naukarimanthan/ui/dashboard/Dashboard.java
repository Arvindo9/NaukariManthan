package com.solution.naukarimanthan.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.solution.naukarimanthan.BR;
import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.AdapterFilterCalls;
import com.solution.naukarimanthan.base.BaseActivity;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.databinding.DashboardBinding;
import com.solution.naukarimanthan.ui.category.CategoryActivity;
import com.solution.naukarimanthan.ui.dashboard.dialog.UpdateDialog;
import com.solution.naukarimanthan.ui.dashboard.home.HomeFragment;
import com.solution.naukarimanthan.ui.dashboard.jobs.JobsFragment;
import com.solution.naukarimanthan.ui.dashboard.notification.NotificationAdapter;
import com.solution.naukarimanthan.ui.jobs_item.JobItemActivity;
import com.solution.naukarimanthan.utils.AppConstants;
import com.solution.naukarimanthan.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.solution.naukarimanthan.utils.AppConstants.START_AS_JOB_ITEM_SHORT;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/8/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class Dashboard extends BaseActivity<DashboardBinding, DashboardViewModel> implements
    DashboardNavigator, HasSupportFragmentInjector, AdapterFilterCalls, NotificationAdapter.NotifyAdapterListener {

    private static final String TAG = Dashboard.class.getSimpleName();
    public DashboardBinding binding;
    private InterstitialAd interstitialAd;


    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    DashboardViewModel viewModel;

    @Inject
    DashboardPagerAdapter mPagerAdapter;

    @Inject
    NotificationAdapter notificationAdapter;

//    @Inject
//    LinearLayoutManager mLayoutManager;



    private int currentItem = 0;

    public static Intent newIntent(Context context) {
        return new Intent(context, Dashboard.class);
    }

    /**
     * @param binding activity class data binding
     */
    @Override
    public void getActivityBinding(DashboardBinding binding) {
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
        return R.layout.dashboard;
    }

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    @Override
    public int getBindingVariable() {
        return BR.data;
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    @Override
    public DashboardViewModel getViewModel() {
        return viewModel;
    }

    /**
     * Do anything on onCreate after binding
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        setUp();
        subscribeToLiveData();

        setUpNotificationUi();
        setUpNotification();

        setUpAds();

        viewModel.start(getCurrentVersionOfApp());
    }

    private void setUpAds() {
        interstitialAd = new InterstitialAd(this, AppConstants.INSTANCE.FB_ADS_PLACEMENT_ID);
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");

                if(!interstitialAd.isAdLoaded()){
                    interstitialAd.loadAd();
                }
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());

                if(!interstitialAd.isAdLoaded()){
                    interstitialAd.loadAd();
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
//                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        interstitialAd.loadAd();
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    private void setUp() {

        setSupportActionBar(binding.toolbarLayout.toolbar);
        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        mPagerAdapter.setCount(10);
    }

    private void setUpViewPager(){
        binding.viewPager.setAdapter(mPagerAdapter);

        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.viewPager.setOffscreenPageLimit(binding.tabLayout.getTabCount());

        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                binding.tabLayout));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
                currentItem = binding.viewPager.getCurrentItem();


                int count = (int) (Math.random() * 3) + 1;
                if (interstitialAd.isAdLoaded() && count == 2) {
                    interstitialAd.show();
                }

                if(currentItem == 0){
                    binding.toolbarLayout.searchIcon.setVisibility(View.GONE);
                    binding.toolbarLayout.notificationIcon.setVisibility(View.VISIBLE);
                }
                else {
                    binding.toolbarLayout.searchIcon.setVisibility(View.VISIBLE);
                    binding.toolbarLayout.notificationIcon.setVisibility(View.GONE);
                }

                /*
                List<JobsMenuTabs> tabList = mPagerAdapter.getTabsList();

                if(tabList != null) {
                    String title = tabList.get(currentItem).getMenu();
                    getActivity().setTitle(title);
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
        });
    }

    private void subscribeToLiveData() {
        viewModel.getAreaLiveData().observe(this, area ->
                viewModel.updateArea(area));

        viewModel.getNotificationListLiveData().observe(this, notify ->
                viewModel.addJobsItemsToList(notify));
    }

    private String getCurrentVersionOfApp(){
        String version = "0";

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    @Override
    public void tabsMenuList(List<JobsMenuTabs> jobsMenuTabs) {
        if(jobsMenuTabs == null || jobsMenuTabs.isEmpty()){
            jobsMenuTabs = new ArrayList<>();
            JobsMenuTabs homeTab = new JobsMenuTabs("Home", -1, -1);
            jobsMenuTabs.add(0, homeTab);
        }
        else if(jobsMenuTabs.size() > 1 && jobsMenuTabs.get(1).getMenuId() == -1){
            jobsMenuTabs.remove(1);
        }

        mPagerAdapter.setCount(jobsMenuTabs.size());

        mPagerAdapter.setValues(jobsMenuTabs);

        setUpViewPager();

        for(JobsMenuTabs tabs: jobsMenuTabs) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabs.getMenu()));
        }

    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();

        if(throwable instanceof IOException){
            runOnUiThread(() -> showToast(R.string.network_error));
        }
    }

    @Override
    public void onHomeButtonClick() {
        binding.viewPager.setCurrentItem(0);
    }

    @Override
    public void onNetworkAvailability(boolean networkState) {

    }

    @Override
    public void onRefreshButtonClick() {
//        if(currentItem == 0){
            //home frag
            if(mPagerAdapter.getFragment(currentItem) instanceof HomeFragment){
                ((HomeFragment)mPagerAdapter.getFragment(currentItem)).onRefreshButtonClick();
            }
//        }
//        else {
            //jobs frag
            if(mPagerAdapter.getFragment(currentItem) instanceof JobsFragment){
                ((JobsFragment)mPagerAdapter.getFragment(currentItem)).onRefreshButtonClick();
            }
//        }
    }

    @Override
    public void onCategoryClick() {
        startActivity(CategoryActivity.newIntent(context));
    }

    @Override
    public void openPlayStoreUpdate() {
        try {
            UpdateDialog updateDialog = new UpdateDialog();
            updateDialog.show(getSupportFragmentManager(), "UpdateDialog");
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    /**
     * @param data it will only be called when filter will occur in searchBar
     */
    @Override
    public void callBackToActivity(String data) {

    }

    //Notification------------------------

    private void setUpNotificationUi() {
        notificationAdapter.setListener(this);
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        binding.notificationListView.setLayoutManager(mLayoutManager);
        binding.notificationListView.setItemAnimator(new DefaultItemAnimator());
        binding.notificationListView.setAdapter(notificationAdapter);

        binding.toolbarLayout.notificationIcon.setVisibility(View.VISIBLE);


        Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.slide_in_bottom);

        Animation bottomDown = AnimationUtils.loadAnimation(this,
                R.anim.slide_out_bottom);

        binding.toolbarLayout.notificationIcon.setOnClickListener(v -> {
            if(binding.noticeLayout.getVisibility() == View.VISIBLE){
                binding.noticeLayout.startAnimation(bottomDown);
                binding.noticeLayout.setVisibility(View.GONE);
                binding.noticeLayout.setBackground(null);
            }
            else{
                binding.noticeLayout.bringToFront();
                binding.noticeLayout.startAnimation(bottomUp);
                binding.noticeLayout.setVisibility(View.VISIBLE);
            }
        });

        binding.cancelNotification.setOnClickListener(v ->{
            binding.noticeLayout.startAnimation(bottomDown);
            binding.noticeLayout.setVisibility(View.GONE);
            binding.noticeLayout.setBackground(null);
        });

        bottomUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.noticeLayout.setBackground(getResources().getDrawable(R.color.grey_transparent));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void setUpNotification() {
        // If a Notification message is tapped, any data accompanying the Notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the Notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the Notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying Notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Logger.e("Key: " + key + " Value: " + value);

                if(key.equals("Key")) {
                    try {
                        String idStr = String.valueOf(value);
                        int id = Integer.parseInt(idStr);
                        onOpenJobItem(id);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        }
        // [END handle_data_extras]
    }

    private void onOpenJobItem(int jobId){
        startActivity(JobItemActivity.newIntent(this, START_AS_JOB_ITEM_SHORT,
                jobId, 0));
    }

    @Override
    public void onRetryClick() {

    }
}
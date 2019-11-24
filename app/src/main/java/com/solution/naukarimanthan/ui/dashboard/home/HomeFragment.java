package com.solution.naukarimanthan.ui.dashboard.home;


import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;

import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.solution.naukarimanthan.BR;
import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseFragment;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.databinding.HomeFragmentBinding;
import com.solution.naukarimanthan.ui.dashboard.home.adapter.HomeAdapter;
import com.solution.naukarimanthan.ui.dashboard.home.dialog.SubscribeDialog;
import com.solution.naukarimanthan.ui.dashboard.home.recent$jobs.RecentJobsAdapter;
import com.solution.naukarimanthan.ui.special_jobs.SpecialJobsActivity;
import com.solution.naukarimanthan.utils.AppConstants;
import com.solution.naukarimanthan.utils.ads.AdgebraAds;
import com.solution.naukarimanthan.utils.ads.AdsCallBack;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static com.solution.naukarimanthan.utils.AppConstants.FACEBOOK_ADS;
import static com.solution.naukarimanthan.utils.AppConstants.LOCAL_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.POPULAR_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECENT_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECOMMENDED_JOBS;
import static com.solution.naukarimanthan.utils.ads.AdgebraAds.BIG_CARD;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-01-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 */
public class HomeFragment extends BaseFragment<HomeFragmentBinding, HomeViewModel> implements
        HomeNavigator, HomeAdapter.HomeAdapterListener, RecentJobsAdapter.RecentJobsAdapterListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    HomeAdapter recommendedJobAdapter, localJobAdapter, popularJobAdapter;
    @Inject
    RecentJobsAdapter recentJobAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    private InterstitialAd interstitialAd;

    private final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    private static int mCurrentPage = 0;
    private static int NUM_PAGES = 1;
    private Handler handler = new Handler();
    private Runnable Update;

//    @Inject
//    LinearLayoutManager mLayoutManager;

    private HomeFragmentBinding binding;
    private HomeViewModel viewModel;
    private com.facebook.ads.AdView adView1;
    private com.facebook.ads.AdView adView2;
    private com.facebook.ads.AdView adView3;
    private com.facebook.ads.AdView adView4;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(HomeFragmentBinding binding) {
        this.binding = binding;
    }

    /**
     * @param savedInstanceState save the instance of fragment before closing
     */
    @Override
    protected void onCreateFragment(Bundle savedInstanceState) {
        viewModel.setNavigator(this);
        recentJobAdapter.setListener(this);
        localJobAdapter.setListener(this);
        recommendedJobAdapter.setListener(this);
        popularJobAdapter.setListener(this);

        recommendedJobAdapter.setValue(1);
        localJobAdapter.setValue(2);
        popularJobAdapter.setValue(2);
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.home_fragment;
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    @Override
    public HomeViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        return viewModel;
    }

    /**
     * Override for set bindingF variable
     *
     * @return variable id
     */
    @Override
    public int getBindingVariable() {
        return BR.data;
    }

    /**
     * @return R.strings.text
     */
    @Override
    public int setTitle() {
        return 0;
    }

    /**
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
        setUp();
        subscribeToLiveData();

        handlingBackground();

        paceView();

        setupAds();
    }

    private void setupAds() {
        interstitialAd = new InterstitialAd(getActivityClass(),AppConstants.INSTANCE.FB_ADS_PLACEMENT_ID);
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
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
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

/*
        MobileAds.initialize(getContext(), AppConstants.INSTANCE.ADMOB_APP_ID);

        if (getContext() != null) {
            mInterstitialAd = new InterstitialAd(getContext());
            mInterstitialAd.setAdUnitId(getString(R.string.adsInterstitial));
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
*/

        //first ads
        int whichCompanyAds = (int) (Math.random() * 3) +1;
        Log.e(TAG, "Add type ============" + whichCompanyAds);

        if(whichCompanyAds == FACEBOOK_ADS) {
            if (getContext() != null) {
                adView1 = new com.facebook.ads.AdView(getContext(),
                        AppConstants.INSTANCE.FB_ADS_PLACEMENT_ID, AdSize.BANNER_HEIGHT_90);
                // Add the ad view to your activity layout
                binding.bannerAd1.addView(adView1);

                // Request an ad
                adView1.loadAd();

                adView1.setAdListener(new AdListener() {
                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        loadGoogleBannerAds(1);
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Ad loaded callback
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Ad impression logged callback
                    }
                });
            }
        }
        else {
            loadGoogleBannerAds(1);
        }

        //second ads
        whichCompanyAds = (int) (Math.random() * 3) + 1;
        Log.e(TAG, "Add type ============" + whichCompanyAds);
        if(whichCompanyAds == FACEBOOK_ADS) {
            if (getContext() != null) {
                adView2 = new com.facebook.ads.AdView(getContext(),
                        AppConstants.INSTANCE.FB_ADS_PLACEMENT_ID, AdSize.BANNER_HEIGHT_90);
                // Add the ad view to your activity layout
                binding.bannerAd2.addView(adView2);

                // Request an ad
                adView2.loadAd();

                adView2.setAdListener(new AdListener() {
                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        loadGoogleBannerAds(2);
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Ad loaded callback
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Ad impression logged callback
                    }
                });
            }
        }
        else {
            loadGoogleBannerAds(2);
        }

        //thirld ads
        whichCompanyAds = (int) (Math.random() * 3) + 1;
        Log.e(TAG, "Add type ============" + whichCompanyAds);
        if(whichCompanyAds == FACEBOOK_ADS) {
            if (getContext() != null) {
                adView3 = new com.facebook.ads.AdView(getContext(),
                        AppConstants.INSTANCE.FB_ADS_PLACEMENT_ID, AdSize.BANNER_HEIGHT_90);
                // Add the ad view to your activity layout
                binding.bannerAd3.addView(adView3);

                // Request an ad
                adView3.loadAd();

                adView3.setAdListener(new AdListener() {
                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        loadGoogleBannerAds(3);
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Ad loaded callback
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Ad impression logged callback
                    }
                });
            }
        }
        else {
            loadGoogleBannerAds(3);
        }

        //fourth ads
        whichCompanyAds = (int) (Math.random() * 3) + 1;
        Log.e(TAG, "Add type ============" + whichCompanyAds);
        if(whichCompanyAds == FACEBOOK_ADS) {
            if (getContext() != null) {
                adView4 = new com.facebook.ads.AdView(getContext(),
                        AppConstants.INSTANCE.FB_ADS_PLACEMENT_ID, AdSize.BANNER_HEIGHT_90);
                // Add the ad view to your activity layout
                binding.bannerAd4.addView(adView4);

                // Request an ad
                adView4.loadAd();

                adView4.setAdListener(new AdListener() {
                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        loadGoogleBannerAds(4);
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Ad loaded callback
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Ad impression logged callback
                    }
                });
            }
        }
        else {
            loadGoogleBannerAds(4);
        }
    }

    private void loadGoogleBannerAds(int index){
        AdsCallBack adgebraAdsCallBack1 = new AdsCallBack() {
            @Override
            public void adLoaded(View view) {
                binding.bannerAd1.addView(view);
            }

            @Override
            public void Error() {

            }
        };
        AdsCallBack adgebraAdsCallBack2 = new AdsCallBack() {
            @Override
            public void adLoaded(View view) {
                binding.bannerAd2.addView(view);
            }

            @Override
            public void Error() {

            }
        };
        AdsCallBack adgebraAdsCallBack3 = new AdsCallBack() {
            @Override
            public void adLoaded(View view) {
                binding.bannerAd3.addView(view);
            }

            @Override
            public void Error() {

            }
        };
        AdsCallBack adgebraAdsCallBack4 = new AdsCallBack() {
            @Override
            public void adLoaded(View view) {
                binding.bannerAd4.addView(view);
            }

            @Override
            public void Error() {

            }
        };

//        AdView adView1;
        switch (index){
            case 1:
                if (getContext() != null) {
/*
                    adView1 = new AdView(getContext());
                    adView1.setAdSize(com.google.android.gms.ads.AdSize.LARGE_BANNER);
                    adView1.setAdUnitId(getString(R.string.adsBannerSplash));
                    binding.bannerAd1.addView(adView1);
                    adView1.loadAd(new AdRequest.Builder().build());
*/
                    AdgebraAds.setNativeAds(viewModel, getActivity(), adgebraAdsCallBack1, BIG_CARD);
                }
                break;

            case 2:
                if (getContext() != null) {
/*
                    adView1 = new AdView(getContext());
                    adView1.setAdSize(com.google.android.gms.ads.AdSize.LARGE_BANNER);
                    adView1.setAdUnitId(getString(R.string.adsBannerSplash));
                    binding.bannerAd2.addView(adView1);
                    adView1.loadAd(new AdRequest.Builder().build());
*/

                    AdgebraAds.setNativeAds(viewModel, getActivity(), adgebraAdsCallBack2, BIG_CARD);
                }
                break;

            case 3:
                if (getContext() != null) {
/*
                    adView1 = new AdView(getContext());
                    adView1.setAdSize(com.google.android.gms.ads.AdSize.LARGE_BANNER);
                    adView1.setAdUnitId(getString(R.string.adsBannerSplash));
                    binding.bannerAd3.addView(adView1);
                    adView1.loadAd(new AdRequest.Builder().build());
*/

                    AdgebraAds.setNativeAds(viewModel, getActivity(), adgebraAdsCallBack3, BIG_CARD);
                }
                break;

            case 4:
                if (getContext() != null) {
/*
                    adView1 = new AdView(getContext());
                    adView1.setAdSize(com.google.android.gms.ads.AdSize.LARGE_BANNER);
                    adView1.setAdUnitId(getString(R.string.adsBannerSplash));
                    binding.bannerAd4.addView(adView1);
                    adView1.loadAd(new AdRequest.Builder().build());
*/

                    AdgebraAds.setNativeAds(viewModel, getActivity(), adgebraAdsCallBack4, BIG_CARD);
                }
                break;
        }
    }

    private void paceView(){
        // Gets the layout params that will allow you to resize the layout
/*
        ViewGroup.LayoutParams paramsClose = binding.slidingPaneLayout.getLayoutParams();
        // Changes the height and width to the specified *pixels*
//        paramsClose.height = 100;
//        paramsClose.width = 25;

        ViewGroup.LayoutParams paramsOpen = binding.slidingPaneLayout.getLayoutParams();
        // Changes the height and width to the specified *pixels*
//        paramsOpen.height = 100;
//        paramsOpen.width = 130;


        binding.slidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
        binding.openPaneBtn.setOnClickListener(view -> {
            if(!binding.slidingPaneLayout.isOpen()) {
                binding.slidingPaneLayout.openPane();
//
//                paramsOpen.height = 100;
//                paramsOpen.width = 130;
//                binding.slidingPaneLayout.setLayoutParams(paramsOpen);
            }
            else{
                binding.slidingPaneLayout.closePane();
//
//                paramsClose.height = 100;
//                paramsClose.width = 25;
//                binding.slidingPaneLayout.setLayoutParams(paramsClose);
            }
        });
*/

        binding.openPaneBtn.setOnClickListener(v -> {
            if (binding.actionLayoutAlternate.getVisibility() == View.VISIBLE) {
                binding.actionLayoutAlternate.setVisibility(View.GONE);
                binding.actionLayoutAlternate.setBackground(null);
                binding.openPaneBtn.setVisibility(View.VISIBLE);
            }
            else {
                binding.actionLayoutAlternate.bringToFront();
                binding.actionLayoutAlternate.setVisibility(View.VISIBLE);
                binding.openPaneBtn.setVisibility(View.GONE);
            }
        });


        binding.actionLayoutAlternate.setOnClickListener(v -> {
            binding.actionLayoutAlternate.setVisibility(View.GONE);
            binding.actionLayoutAlternate.setBackground(null);
            binding.openPaneBtn.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void updateJobs(List<JobsData> model) {
        recentJobAdapter.addItems(model);
        recommendedJobAdapter.addItems(model);
        localJobAdapter.addItems(model);
        popularJobAdapter.addItems(model);

    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
        if(throwable instanceof IOException){
            getActivityClass().runOnUiThread(() -> getActivityClass().showToast(R.string.network_error));
        }
    }

    @Override
    public void getRecentJobsPageNo(int pageNumber) {
        NUM_PAGES = pageNumber;
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onDetach() {
        super.onDetach();

        handler.removeCallbacks(Update);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(Update);
        if (adView1 != null) {
            adView1.destroy();
        }
        if (adView2 != null) {
            adView2.destroy();
        }
        if (adView3 != null) {
            adView3.destroy();
        }
        if (adView4 != null) {
            adView4.destroy();
        }
    }

    private void setUp() {
        //Recent jobs
//        binding.recentJobsListView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        binding.recentJobsListView.setItemAnimator(new DefaultItemAnimator());
//        binding.recentJobsListView.setAdapter(recentJobAdapter);


        binding.recentJobsListView.setAdapter(recentJobAdapter);
        binding.recentJobsListView.addOnPageChangeListener(recentJobsListener);


//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        binding.recommendedJobsListView.setLayoutManager(mLayoutManager);
//        binding.recommendedJobsListView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recommendedJobsListView.setItemAnimator(new DefaultItemAnimator());
        binding.recommendedJobsListView.setAdapter(recommendedJobAdapter);

        binding.popularJobsListView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.popularJobsListView.setItemAnimator(new DefaultItemAnimator());
        binding.popularJobsListView.setAdapter(popularJobAdapter);

        binding.localJobsListView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.localJobsListView.setItemAnimator(new DefaultItemAnimator());
        binding.localJobsListView.setAdapter(localJobAdapter);


    }

    private void subscribeToLiveData() {
        viewModel.getRecentListLiveData().observe(this, jobs ->
                viewModel.addRecentJobsItemsToList(jobs));

        viewModel.getRecommendedListLiveData().observe(this, jobs ->
                viewModel.addRecommendedJobsItemsToList(jobs));

        viewModel.getLocalListLiveData().observe(this, jobs ->
                viewModel.addLocalJobsItemsToList(jobs));

        viewModel.getPopularListLiveData().observe(this, jobs ->
                viewModel.addPopularJobsItemsToList(jobs));
    }

    ViewPager.OnPageChangeListener recentJobsListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //pager------------------------

    private void handlingBackground() {
        /*After setting the adapter use the timer */
//        handler.postDelayed(Update,PERIOD_MS);
        handler.postDelayed(Update= () -> {
            handler.postDelayed(Update,PERIOD_MS);
            if (mCurrentPage == NUM_PAGES) {
                mCurrentPage = 0;
            }
            else {
                try {
                    binding.recentJobsListView.setCurrentItem(mCurrentPage++, true);
                }catch (NullPointerException ignore){}
            }
        },PERIOD_MS);

    }

    public void onRefreshButtonClick() {
        mCurrentPage = 1;
        viewModel.onRefreshButtonClick();
    }

    //view more task---------------

    @Override
    public void onRecentMoreClick() {
        if (interstitialAd != null && interstitialAd.isAdLoaded()) {
            interstitialAd.show();
        }
        startActivity(SpecialJobsActivity.newIntent(getContext(), RECENT_JOBS,
                getResources().getString(R.string.recent_jobs)));
    }

    @Override
    public void onRecommendedMoreClick() {
        if (interstitialAd != null && interstitialAd.isAdLoaded()) {
            interstitialAd.show();
        }
        startActivity(SpecialJobsActivity.newIntent(getContext(), RECOMMENDED_JOBS,
                getResources().getString(R.string.recommended_jobs)));
    }

    @Override
    public void onLocalMoreClick() {
        if (interstitialAd != null && interstitialAd.isAdLoaded()) {
            interstitialAd.show();
        }
        startActivity(SpecialJobsActivity.newIntent(getContext(), LOCAL_JOBS,
                getResources().getString(R.string.local_jobs)));
    }

    @Override
    public void onPopularMoreClick() {
        if (interstitialAd != null && interstitialAd.isAdLoaded()) {
            interstitialAd.show();
        }
        startActivity(SpecialJobsActivity.newIntent(getContext(), POPULAR_JOBS,
                getResources().getString(R.string.popular_jobs)));
    }

    @Override
    public void onSubscriptionClick() {
        try {
            SubscribeDialog flagDialog = new SubscribeDialog(viewModel);
//        flagDialog.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Dialog);
            flagDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "SubscribeDialog");
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onFeedbackClick() {
        Uri uri;
        if (getContext() != null) {
            uri = Uri.parse("market://details?id=" + getContext().getPackageName());
        }
        else{
            uri = Uri.parse("market://details?id=" + "com.solution.naukarimanthan");
        }
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            if (getContext() != null) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" +
                                getContext().getPackageName())));
            }
            else{
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" +
                                "com.solution.naukarimanthan")));
            }
        }
    }

    @Override
    public void onSubscriptionDone(String message) {
        getBaseActivity().showToast(message);
    }
}
package com.solution.naukarimanthan.ui.jobs_item;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.solution.naukarimanthan.BR;
import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseActivity;
import com.solution.naukarimanthan.databinding.JobItemActivityBinding;
import com.solution.naukarimanthan.utils.ads.AdgebraAds;
import com.solution.naukarimanthan.utils.ads.AdsCallBack;

import java.io.IOException;

import javax.inject.Inject;

import static com.solution.naukarimanthan.utils.AppConstants.KEY_DESCRIPTION_ID;
import static com.solution.naukarimanthan.utils.AppConstants.KEY_JOB_ID;
import static com.solution.naukarimanthan.utils.AppConstants.KEY_TYPE_WHERE;
import static com.solution.naukarimanthan.utils.AppConstants.START_AS_JOB_ITEM_LONG;
import static com.solution.naukarimanthan.utils.AppConstants.START_AS_JOB_ITEM_SHORT;
import static com.solution.naukarimanthan.utils.ads.AdgebraAds.BIG_CARD;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/21/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class JobItemActivity extends BaseActivity<JobItemActivityBinding, JobItemViewModel>
        implements JobItemNavigator{

    @Inject
    JobItemViewModel viewModel;
    private JobItemActivityBinding binding;
    private int jobId;
    private int typeWhere;
    private int descriptionId;

    public static Intent newIntent(Context context, int typeWhere, int jobId, int descriptionId) {
        Intent i = new Intent(context, JobItemActivity.class);
        i.putExtra(KEY_JOB_ID, jobId);
        i.putExtra(KEY_DESCRIPTION_ID, descriptionId);
        i.putExtra(KEY_TYPE_WHERE, typeWhere);
        return i;
    }

    /**
     * @param binding activity class data binding
     */
    @Override
    public void getActivityBinding(JobItemActivityBinding binding) {
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

    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.job_item_activity;
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
    public JobItemViewModel getViewModel() {
        return viewModel;
    }

    /**
     * Do anything on onCreate after binding
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        initialise();
        open();
        setUp();
        subscribeToLiveData();

//        loadData("");
    }

    private void initialise() {
        jobId = getIntent().getIntExtra(KEY_JOB_ID, 0);
        descriptionId = getIntent().getIntExtra(KEY_DESCRIPTION_ID, 0);
        typeWhere = getIntent().getIntExtra(KEY_TYPE_WHERE, 0);
    }

    private void open() {
        if(typeWhere == START_AS_JOB_ITEM_SHORT){
            viewModel.start(jobId);
        }
        else{
            viewModel.start(jobId, descriptionId);
            binding.viewDetails.setVisibility(View.GONE);
        }
    }


    private void setUp() {
        setSupportActionBar(binding.toolbarLayout.toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        binding.toolbarLayout.backHome.setOnClickListener(v -> onBackPressed());

        binding.toolbarLayout.searchIcon.setVisibility(View.GONE);
        binding.toolbarLayout.title.setVisibility(View.VISIBLE);
//        binding.toolbarLayout.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        binding.toolbarLayout.toolbar.setTitle("Filter option");
        binding.toolbarLayout.toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        binding.toolbarLayout.refreshIcon.setOnClickListener(v -> open());


        AdsCallBack adgebraAdsCallBack1 = new AdsCallBack() {
            @Override
            public void adLoaded(View view) {
                binding.bannerAd1.addView(view);
            }

            @Override
            public void Error() {

            }
        };


        AdgebraAds.setNativeAds(viewModel, this, adgebraAdsCallBack1, BIG_CARD);

        /*
        AdView adView1 = new AdView(this);
        adView1.setAdSize(com.google.android.gms.ads.AdSize.LARGE_BANNER);
        adView1.setAdUnitId(getString(R.string.adsBannerSplash));
        binding.bannerAd1.addView(adView1);
        adView1.loadAd(new AdRequest.Builder().build());*/

    }

    private void subscribeToLiveData() {
        viewModel.getJobsItemLiveData().observe(this, data ->{
                viewModel.addJobsItems(data);
                if (data != null) {
                    binding.toolbarLayout.title.setText(data.getMenu());
                }

                if(typeWhere == START_AS_JOB_ITEM_SHORT) {
                    if (data != null) {
                        descriptionId = data.getShordescId();
                        loadData(data.getShortdesc());
                    }
                }
                else{
                    if (data != null) {
                        loadData(data.getLongdesc());
                    }
                }
            }
        );
    }

    private void loadData(String data) {
        binding.webView.loadData(data,  "text/html", null);
    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();

        if(throwable instanceof IOException){
            runOnUiThread(() -> showToast(R.string.network_error));
        }
    }

    @Override
    public void onViewDetailClick() {
        if(typeWhere == START_AS_JOB_ITEM_SHORT){
            startActivity(JobItemActivity.newIntent(this, START_AS_JOB_ITEM_LONG, jobId, descriptionId));
        }
    }

}

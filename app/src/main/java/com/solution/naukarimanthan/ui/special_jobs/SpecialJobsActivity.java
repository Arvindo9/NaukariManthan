package com.solution.naukarimanthan.ui.special_jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.solution.naukarimanthan.BR;
import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseActivity;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.databinding.SpecialJobsActivityBinding;
import com.solution.naukarimanthan.ui.dashboard.jobs.JobsFragment;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.solution.naukarimanthan.utils.AppConstants.KEY_TAB_ID;
import static com.solution.naukarimanthan.utils.AppConstants.KEY_TITLE;
import static com.solution.naukarimanthan.utils.AppConstants.KEY_TYPE;
import static com.solution.naukarimanthan.utils.AppConstants.START_AS_SPECIAL;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/19/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class SpecialJobsActivity extends BaseActivity<SpecialJobsActivityBinding, SpecialJobsViewMode>
    implements SpecialJobsNavigator, HasSupportFragmentInjector {

    @Inject
    SpecialJobsViewMode viewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public SpecialJobsActivityBinding binding;
    private Context context;
    private int typeStart;
    private String title = "";
    private int menuId = 0;

    public static Intent newIntent(Context context, int startType, String title) {
        Intent i = new Intent(context, SpecialJobsActivity.class);
        i.putExtra(KEY_TYPE, startType);
        i.putExtra(KEY_TITLE, title);
        return i;
    }

    public static Intent newIntent(Context context, int startType, String title, int menuId) {
        Intent i = new Intent(context, SpecialJobsActivity.class);
        i.putExtra(KEY_TYPE, startType);
        i.putExtra(KEY_TITLE, title);
        i.putExtra(KEY_TAB_ID, menuId);
        return i;
    }

    /**
     * @param binding activity class data binding
     */
    @Override
    public void getActivityBinding(SpecialJobsActivityBinding binding) {
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
        return R.layout.special_jobs_activity;
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
    public SpecialJobsViewMode getViewModel() {
        return viewModel;
    }

    /**
     * Do anything on onCreate after binding
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        getValues();
        setUp();
        openNewFragment();
    }

    private void getValues(){
        this.typeStart = getIntent().getIntExtra(KEY_TYPE, 0);
        this.title = getIntent().getStringExtra(KEY_TITLE);
        this.menuId = getIntent().getIntExtra(KEY_TAB_ID, 0);
    }

    private void setUp() {
        binding.toolbarLayout.homeButton.setVisibility(View.GONE);
        binding.toolbarLayout.subtitleText.setVisibility(View.GONE);
        binding.toolbarLayout.categoryText.setVisibility(View.GONE);
        binding.toolbarLayout.searchIcon.setVisibility(View.VISIBLE);
        binding.toolbarLayout.title.setVisibility(View.VISIBLE);

        setSupportActionBar(binding.toolbarLayout.toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }


        binding.toolbarLayout.backHome.setVisibility(View.VISIBLE);
        binding.toolbarLayout.backHome.setOnClickListener(v -> finish());

//        binding.toolbarLayout.toolbar.setNavigationOnClickListener(v -> finish());
//        binding.toolbarLayout.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        binding.toolbarLayout.title.setText(title);
    }

    private void openNewFragment(){
        Fragment fragment = JobsFragment.newInstance(START_AS_SPECIAL, typeStart, title, menuId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack("JobsFragment")
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void updateJobs(List<JobsMenuTabs> model) {

    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();

        if(throwable instanceof IOException){
            runOnUiThread(() -> showToast(R.string.network_error));
        }
    }

    @Override
    public void onCheckInternetConnection() {
        if(!isNetworkAvailable()){
            showToast(R.string.network_not_found);
        }
    }

    /**
     * Returns an {@link AndroidInjector} of {@link Fragment}s.
     */
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}

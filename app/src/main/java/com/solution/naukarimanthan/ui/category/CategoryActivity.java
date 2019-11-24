package com.solution.naukarimanthan.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.solution.naukarimanthan.BR;
import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseActivity;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.databinding.CategoryActivityBinding;
import com.solution.naukarimanthan.ui.category.adapter.CategoryAdapter;
import com.solution.naukarimanthan.ui.dashboard.Dashboard;
import com.solution.naukarimanthan.ui.location.LocationActivity;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static com.solution.naukarimanthan.ui.location.LocationActivity.CALL_FROM_CATEGORY_ACTIVITY;
import static com.solution.naukarimanthan.ui.location.LocationActivity.ON_LACATION_SCREEC;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/17/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class CategoryActivity extends BaseActivity<CategoryActivityBinding, CategoryViewModel>
    implements CategoryNavigator, CategoryAdapter.CategoryAdapterListener {

    @Inject
    CategoryViewModel viewModel;
    @Inject
    CategoryAdapter adapter;

    private CategoryActivityBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, CategoryActivity.class);
    }

    /**
     * @param binding activity class data binding
     */
    @Override
    public void getActivityBinding(CategoryActivityBinding binding) {
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
        return R.layout.category_activity;
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
    public CategoryViewModel getViewModel() {
        return viewModel;
    }

    /**
     * Do anything on onCreate after binding
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        adapter.setListener(this);
        setUp();
        subscribeToLiveData();
        searchFunction();
    }

    private void setUp() {
        setSupportActionBar(binding.toolbarLayout.toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        binding.toolbarLayout.backHome.setOnClickListener(v -> onBackPressed());

        binding.toolbarLayout.title.setVisibility(View.VISIBLE);
//        binding.toolbarLayout.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        binding.toolbarLayout.title.setText(getString(R.string.filter_option));
        binding.toolbarLayout.toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        binding.listView.setItemAnimator(new DefaultItemAnimator());
        binding.listView.setAdapter(adapter);
        adapter.setValues(viewModel);
    }

    private void searchFunction1() {
        binding.toolbarLayout.clearBtn.setOnClickListener(v -> {
            if (Objects.requireNonNull(binding.toolbarLayout.search.getText()).length() > 0) {
                binding.toolbarLayout.search.setText("");
            }
            else{
                binding.toolbarLayout.searchBar.setVisibility(View.GONE);
            }
        });

        binding.toolbarLayout.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (adapter != null && !newText.equals("")) {
                    adapter.getFilter().filter(newText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void searchFunction() {
//            activity.binding.toolbarLayout.searchIcon.setVisibility(View.VISIBLE);
            binding.toolbarLayout.searchIcon.setOnClickListener(v ->{
                binding.toolbarLayout.searchBar.setVisibility(View.VISIBLE);
                binding.toolbarLayout.toolbar.setVisibility(View.GONE);
            });

            binding.toolbarLayout.clearBtn.setOnClickListener(v -> {

                if (Objects.requireNonNull(binding.toolbarLayout.search.getText()).length() > 0) {
                    binding.toolbarLayout.search.setText("");
                } else {
                    binding.toolbarLayout.toolbar.setVisibility(View.VISIBLE);
                    binding.toolbarLayout.searchBar.setVisibility(View.GONE);
                }
            });

            binding.toolbarLayout.search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence newText, int start, int before, int count) {
                    if (adapter != null && !newText.equals("")) {
                        adapter.getFilter().filter(newText);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
    }

    private void subscribeToLiveData() {
        viewModel.getMenuListLiveData().observe(this, jobs ->
                viewModel.addMenuItemsToList(jobs));

        viewModel.getAreaLiveData().observe(this, area ->
                viewModel.updateArea(area));
    }

    @Override
    public void updateJobs(List<JobsMenuTabs> model) {
        adapter.addItems(model);
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();

        if(throwable instanceof IOException){
            runOnUiThread(() -> showToast(R.string.network_error));
        }
    }

    @Override
    public void onLocationButtonClick() {
        Intent i = LocationActivity.newIntent(context);
        i.putExtra(ON_LACATION_SCREEC, CALL_FROM_CATEGORY_ACTIVITY);
        startActivity(i);
    }

    @Override
    public void onCheckInternetConnection() {
        if(!isNetworkAvailable()){
            showToast(R.string.network_not_found);
        }
    }
}
package com.solution.naukarimanthan.ui.dashboard.jobs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;

import com.solution.naukarimanthan.BR;
import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseFragment;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.databinding.JobsFragmentBinding;
import com.solution.naukarimanthan.ui.dashboard.Dashboard;
import com.solution.naukarimanthan.ui.dashboard.jobs.adapter.JobsAdapter;
import com.solution.naukarimanthan.ui.special_jobs.SpecialJobsActivity;
import com.solution.naukarimanthan.utils.AppConstants;
import com.solution.naukarimanthan.utils.EndlessRecyclerViewScrollListener;
import com.solution.naukarimanthan.utils.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static com.solution.naukarimanthan.utils.AppConstants.JOBS_BY_MENU_ID;
import static com.solution.naukarimanthan.utils.AppConstants.START_AS_DEFAULT;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/9/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class JobsFragment extends BaseFragment<JobsFragmentBinding, JobsViewModel> implements
        JobsNavigator, JobsAdapter.JobsAdapterListener{
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    JobsAdapter adapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    private int currentPage = 1;


    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    private int typeStart = 1;
    private JobsViewModel viewModel;
    private JobsFragmentBinding binding;
    private int tabsId;
    private String title = "";
    private int typeWhere = 0;

    public static JobsFragment newInstance(int typeWhere, int type, String title, int tabId) {
        Bundle args = new Bundle();
        JobsFragment fragment = new JobsFragment();
        args.putInt(AppConstants.KEY_TYPE_WHERE, typeWhere);
        args.putInt(AppConstants.KEY_TYPE, type);
        args.putString(AppConstants.KEY_TITLE, title);
        args.putInt(AppConstants.KEY_TAB_ID, tabId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(JobsFragmentBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.jobs_fragment;
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    @Override
    public JobsViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(JobsViewModel.class);
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
     * @param savedInstanceState save the instance of fragment before closing
     */
    @Override
    protected void onCreateFragment(Bundle savedInstanceState) {
        viewModel.setNavigator(this);
        adapter.setListener(this);
        typeWhere = getArguments() != null ? getArguments().getInt(AppConstants.KEY_TYPE_WHERE, 1) : 1;
        typeStart = getArguments() != null ? getArguments().getInt(AppConstants.KEY_TYPE, 1) : 1;
        tabsId = getArguments() != null ? getArguments().getInt(AppConstants.KEY_TAB_ID, 0) : 0;
        title = getArguments() != null ? getArguments().getString(AppConstants.KEY_TITLE) : "Jobs";

        if(typeWhere == START_AS_DEFAULT) {
            viewModel.start(tabsId);
        }
        else{
            viewModel.startSpecial(typeStart, tabsId);
        }
    }

    /**
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
        setUp();
        subscribeToLiveData();

        if(typeWhere == START_AS_DEFAULT) {
            searchFunctionDashboard();
        }
        else{
            searchFunctionSpecial();
        }

        recyclerViewScrollListener();
    }

    private void setUp() {
//        String title = getArguments().getString(KEY_TITLE);
//        if(title != null){
//            getActivityClass().setTitle(title);
//        }

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.listView.setLayoutManager(mLayoutManager);
        binding.listView.setItemAnimator(new DefaultItemAnimator());
        binding.listView.setAdapter(adapter);
    }

    @Override
    public void updateJobs(List<JobsData> model) {
        adapter.addItems(model);
    }

    private void subscribeToLiveData() {
        viewModel.getJobsListLiveData().observe(this, jobs ->
                viewModel.addJobsItemsToList(jobs));
    }

    private void recyclerViewScrollListener() {
        binding.listView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            private int pastVisiblesItems;
            private int totalItemCount;
            private int visibleItemCount;
            private int count = -1;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                if(dy > 0){ //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                }
                if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount &&
                        count < totalItemCount){
                    Logger.e("Listview", "Last Item Wow !");

                    if(typeWhere == START_AS_DEFAULT) {
                        viewModel.hitApiForNextLoadData(currentPage);
                    }
                    else{
                        viewModel.fetchSpecialJobs(typeStart, currentPage, tabsId);
                    }
                    currentPage++;
                    count = totalItemCount;
                    //Do pagination.. i.e. fetch new data
                }
            }
        });
    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();

        if(throwable instanceof IOException){
            getActivityClass().runOnUiThread(() -> getActivityClass().showToast(R.string.network_error));
        }
    }

    @Override
    public void setCurrentPage(int pageNo) {
        currentPage = pageNo;
    }

    @Override
    public void onRetryClick() {
        viewModel.start(tabsId);
    }

    private void searchFunctionDashboard() {
        Dashboard activity = ((Dashboard) getContext());
        if(activity!=null && activity.binding!=null && activity.binding.toolbarLayout!=null) {
//            activity.binding.toolbarLayout.searchIcon.setVisibility(View.VISIBLE);
            activity.binding.toolbarLayout.searchIcon.setOnClickListener(v ->{
                    activity.binding.searchBar.setVisibility(View.VISIBLE);
                    activity.binding.toolbarLayout.toolbar.setVisibility(View.GONE);
            });

            activity.binding.clearBtn.setOnClickListener(v -> {

                if (Objects.requireNonNull(activity.binding.search.getText()).length() > 0) {
                    activity.binding.search.setText("");
                } else {
                    activity.binding.toolbarLayout.toolbar.setVisibility(View.VISIBLE);
                    activity.binding.searchBar.setVisibility(View.GONE);
                }
            });

            activity.binding.search.addTextChangedListener(new TextWatcher() {
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
    }

    private void searchFunctionSpecial() {
        SpecialJobsActivity activity = ((SpecialJobsActivity) getContext());
        if(activity!=null && activity.binding!=null && activity.binding.toolbarLayout!=null) {
//            activity.binding.toolbarLayout.searchIcon.setVisibility(View.VISIBLE);
            activity.binding.toolbarLayout.searchIcon.setOnClickListener(v ->{
                activity.binding.searchBar.setVisibility(View.VISIBLE);
                activity.binding.toolbarLayout.toolbar.setVisibility(View.GONE);
            });

            activity.binding.clearBtn.setOnClickListener(v -> {

                if (Objects.requireNonNull(activity.binding.search.getText()).length() > 0) {
                    activity.binding.search.setText("");
                } else {
                    activity.binding.toolbarLayout.toolbar.setVisibility(View.VISIBLE);
                    activity.binding.searchBar.setVisibility(View.GONE);
                }
            });

            activity.binding.search.addTextChangedListener(new TextWatcher() {
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
    }

    public void onRefreshButtonClick() {
        currentPage = 1;
        viewModel.onRefreshButtonClick();
    }
}

package com.solution.naukarimanthan.ui.category.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseAdapter;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.databinding.CategoryAdapterBinding;
import com.solution.naukarimanthan.ui.category.CategoryViewModel;
import com.solution.naukarimanthan.ui.special_jobs.SpecialJobsActivity;
import com.solution.naukarimanthan.utils.BindingMethods;
import com.solution.naukarimanthan.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.solution.naukarimanthan.utils.AppConstants.JOBS_BY_MENU_ID;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/17/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class CategoryAdapter extends BaseAdapter<CategoryAdapterBinding, JobsMenuTabs> {
    private CategoryAdapterListener mListener;

    private CategoryViewModel viewModel;

    public CategoryAdapter(ArrayList<JobsMenuTabs> list) {
        super(list);
    }

    public void setValues(CategoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public interface CategoryAdapterListener {
        void onRetryClick();
    }

    /**
     * @param position current index of ArrayList
     * @return return 0 if single layout xml else override this method for multiple xml or elements
     */
    @Override
    protected int getItemViewTypeAdapter(int position) {
        /*if(position == 0){
            return VIEW_TYPE_LOCATION;
        }
        else if(!list.isEmpty()){
            return VIEW_TYPE_NORMAL;
        }
        return VIEW_TYPE_EMPTY;*/
        return 0;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.category_adapter;
    }

    public void addItems(List<JobsMenuTabs> model) {
        list.addAll(model);
//        mainList.addAll(model);
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
        mainList.clear();
    }

    public void setListener(CategoryAdapterListener listener) {
        this.mListener = listener;
    }

/*
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_NORMAL) {
            return super.onCreateViewHolder(parent, viewType);
        }
        else if(viewType == VIEW_TYPE_LOCATION){
            CategoryAdapterLocationBinding bindingLocation = (CategoryAdapterLocationBinding)
                    createBinding(parent, viewType, R.layout.category_adapter_location);

            return new ViewHolderLocation(bindingLocation);
        }
        return null;
    }
*/

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder(binding);
     */
    @Override
    public ViewHolder getViewHolder(CategoryAdapterBinding binding) {
        return new ViewHolder<CategoryAdapterBinding, JobsMenuTabs>(binding){
            /**
             * If there is anything to do then do here otherwise leave it blank
             *
             * @param binding  layout reference for single view
             * @param data     for single view
             * @param position position of ArrayList
             */
            @Override
            protected void doSomeWorkHere(CategoryAdapterBinding binding, JobsMenuTabs data, int position) {

            }

            /**
             * @param data binding.setData(data);
             */
            @Override
            protected void bindData(JobsMenuTabs data) {
                binding.setData(new CategoryAdapterViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        data
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, CategoryAdapterBinding binding,
                                          JobsMenuTabs data) {
                binding.titleButton.setOnClickListener(thisContext);
            }

            /**
             * Initialised holder by new operator
             *
             * @param binding  DataBinding
             * @param data     dataList
             * @param position of adapter
             * @return new ViewHolderClickListener(binding, data, position)
             */
            @Override
            protected ViewHolderClickListener viewHolderReference(CategoryAdapterBinding binding,
                                                                  JobsMenuTabs data, int position) {
                return new ViewHolderClickListener(binding, position, data) {
                    /**
                     * Called when a view has been clicked.
                     *
                     * @param v The view that was clicked.
                     */
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.titleButton:
                                Logger.e(data.getMenu(), " " + data.getMenuId(), data);
                                if (binding.titleButton.isChecked()) {
                                    binding.titleButton.setChecked(true);
                                } else {
                                    binding.titleButton.setChecked(false);
                                }
                                Intent intent = SpecialJobsActivity.newIntent(itemView
                                        .getContext(), JOBS_BY_MENU_ID, data.getMenu(), data.getMenuId());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                itemView.getContext().startActivity(intent);

                                BindingMethods.INSTANCE.saveRecentMenuIdDb(viewModel, data.getMenuId());
                            break;
                        }
                    }
                };
            }
        };
    }

    /**
     * @return new FilterClass();
     */
    @Override
    protected FilterClass initialisedFilterClass() {
        return new FilterClass() {
            @Override
            public Context getContext() {
                return null;
            }

            @Override
            public ArrayList<JobsMenuTabs> filterData(CharSequence constraint, ArrayList<JobsMenuTabs> list,
                                                      ArrayList<JobsMenuTabs> filteredArrayList) {
                boolean ok = false;
                for (JobsMenuTabs data : list) {
                    if (data.getMenu().toLowerCase().contains(constraint)) {
                        filteredArrayList.add(data);
                        ok = true;
                    }
                }

                if(ok){
                    CategoryAdapterViewModel.textFilter.set(constraint + "");
                }
                else{
                    CategoryAdapterViewModel.textFilter.set("");
                }
                return filteredArrayList;
            }
        };
    }
/*
    private class ViewHolderLocation extends ViewHolder<CategoryAdapterLocationBinding,
            JobsMenuTabs> {

        public ViewHolderLocation(CategoryAdapterLocationBinding bindingLocation) {
            super(bindingLocation);
        }

        *//**
         * If there is anything to do then do here otherwise leave it blank
         *
         * @param binding  layout reference for single view
         * @param data     for single view
         * @param position position of ArrayList
         *//*
        @Override
        protected void doSomeWorkHere(CategoryAdapterLocationBinding binding,
                                      JobsMenuTabs data, int position) {

        }

        *//**
         * @param data binding.setData(data);
         *//*
        @Override
        protected void bindData(JobsMenuTabs data) {
            binding.setData(new CategoryAdapterViewModel(data));
        }

        *//**
         * Method to set click listeners on view holder or groups
         *
         * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
         * @param binding     DataBinding
         * @param data        data
         *//*
        @Override
        public void setClickListeners(ViewHolderClickListener thisContext, CategoryAdapterLocationBinding binding,
                                      JobsMenuTabs data) {

        }

        *//**
         * Initialised holder by new operator
         *
         * @param binding  DataBinding
         * @param data     dataList
         * @param position of adapter
         * @return new ViewHolderClickListener(binding, data, position)
         *//*
        @Override
        protected ViewHolderClickListener viewHolderReference(CategoryAdapterLocationBinding binding,
                                  JobsMenuTabs data, int position) {
            return null;
        }
    }
    */
}

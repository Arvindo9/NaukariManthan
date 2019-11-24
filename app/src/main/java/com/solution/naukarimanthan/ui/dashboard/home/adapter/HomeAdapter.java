package com.solution.naukarimanthan.ui.dashboard.home.adapter;

import android.view.View;

import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseAdapter;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.databinding.HomeAdapterBinding;
import com.solution.naukarimanthan.ui.jobs_item.JobItemActivity;

import java.util.ArrayList;
import java.util.List;

import static com.solution.naukarimanthan.utils.AppConstants.START_AS_JOB_ITEM_SHORT;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/14/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class HomeAdapter extends BaseAdapter<HomeAdapterBinding, JobsData> {
    private HomeAdapterListener mListener;
    private int type = 0;

    public void setValue(int i) {
        type = i;
    }

    public interface HomeAdapterListener {
        void onRetryClick();
    }

    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public HomeAdapter(ArrayList<JobsData> adapterList) {
        super(adapterList);
    }

    public void setListener(HomeAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        if(type == 1){
            return list.size();
        }
        return Math.min(list.size(), 4);
    }

    public void addItems(List<JobsData> model) {
        list.addAll(model);
//        mainList.addAll(model);
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
        mainList.clear();
    }

    /**
     * @param position current index of ArrayList
     * @return return 0 if single layout xml else override this method for multiple xml or elements
     */
    @Override
    protected int getItemViewTypeAdapter(int position) {
        return 0;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.home_adapter;
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder(binding);
     */
    @Override
    public ViewHolder getViewHolder(HomeAdapterBinding binding) {
        return new ViewHolder<HomeAdapterBinding, JobsData>(binding){
            /**
             * If there is anything to do then do here otherwise leave it blank
             *
             * @param binding  layout reference for single view
             * @param data     for single view
             * @param position position of ArrayList
             */
            @Override
            protected void doSomeWorkHere(HomeAdapterBinding binding, JobsData data, int position) {

            }

            /**
             * @param data binding.setData(data);
             */
            @Override
            protected void bindData(JobsData data) {
                binding.setData(new HomeAdapterViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        data
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, HomeAdapterBinding binding, JobsData data) {
                binding.jobsCard.setOnClickListener(thisContext);
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
            protected ViewHolderClickListener viewHolderReference(HomeAdapterBinding binding, JobsData data, int position) {
                return new ViewHolderClickListener(binding, position, data){
                    /**
                     * Called when a view has been clicked.
                     *
                     * @param v The view that was clicked.
                     */
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.jobsCard:
                                itemView.getContext().startActivity(
                                        JobItemActivity.newIntent(itemView.getContext(),
                                                START_AS_JOB_ITEM_SHORT, data.getId(), 0));
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
        return null;
    }
}

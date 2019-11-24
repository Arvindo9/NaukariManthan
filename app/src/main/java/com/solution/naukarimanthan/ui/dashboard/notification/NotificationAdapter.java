package com.solution.naukarimanthan.ui.dashboard.notification;

import android.view.View;

import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseAdapter;
import com.solution.naukarimanthan.data.model.db.notification.Notification;
import com.solution.naukarimanthan.databinding.JobsAdapterBinding;
import com.solution.naukarimanthan.ui.dashboard.jobs.adapter.JobsAdapter;
import com.solution.naukarimanthan.ui.dashboard.jobs.adapter.JobsAdapterViewModel;
import com.solution.naukarimanthan.ui.jobs_item.JobItemActivity;

import java.util.ArrayList;
import java.util.List;

import static com.solution.naukarimanthan.utils.AppConstants.START_AS_JOB_ITEM_SHORT;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/23/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class NotificationAdapter extends BaseAdapter<JobsAdapterBinding, Notification> {

    private NotifyAdapterListener mListener;

    public NotificationAdapter(ArrayList<Notification> list) {
        super(list);
    }

    public interface NotifyAdapterListener {
        void onRetryClick();
    }

    public void addItems(List<Notification> model) {
        list.addAll(model);
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
        mainList.clear();
    }
    public void setListener(NotifyAdapterListener listener) {
        this.mListener = listener;
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
        return R.layout.jobs_adapter;
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder(binding);
     */
    @Override
    public ViewHolder getViewHolder(JobsAdapterBinding binding) {
        return new ViewHolder<JobsAdapterBinding, Notification>(binding){
            /**
             * If there is anything to do then do here otherwise leave it blank
             *
             * @param binding  layout reference for single view
             * @param data     for single view
             * @param position position of ArrayList
             */
            @Override
            protected void doSomeWorkHere(JobsAdapterBinding binding, Notification data, int position) {

            }

            /**
             * @param data binding.setData(data);
             */
            @Override
            protected void bindData(Notification data) {
                binding.setData(new JobsAdapterViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        data
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext,
                                          JobsAdapterBinding binding, Notification data) {
                binding.jobCard.setOnClickListener(thisContext);
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
            protected ViewHolderClickListener viewHolderReference(JobsAdapterBinding binding, Notification data,
                                                                  int position) {
                return new ViewHolderClickListener(binding, position, data){
                    /**
                     * Called when a view has been clicked.
                     *
                     * @param v The view that was clicked.
                     */
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.jobCard:
                                itemView.getContext().startActivity(
                                        JobItemActivity.newIntent(itemView.getContext(),
                                                START_AS_JOB_ITEM_SHORT, Integer.parseInt(data.getKey()), 0));
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

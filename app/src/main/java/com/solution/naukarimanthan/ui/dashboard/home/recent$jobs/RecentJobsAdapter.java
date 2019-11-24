package com.solution.naukarimanthan.ui.dashboard.home.recent$jobs;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.databinding.HomePagerAdapterBinding;
import com.solution.naukarimanthan.ui.dashboard.home.adapter.HomeAdapterViewModel;
import com.solution.naukarimanthan.ui.jobs_item.JobItemActivity;
import com.solution.naukarimanthan.utils.Logger;

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
public class RecentJobsAdapter extends PagerAdapter {


    private final Context context;
    private final ArrayList<JobsData> list;
    private RecentJobsAdapterListener mListener;

    public interface RecentJobsAdapterListener {
        void onRetryClick();
    }

    public RecentJobsAdapter(Context context, ArrayList<JobsData> list){
        this.context = context;
        this.list = list;
    }

    public void setListener(RecentJobsAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void addItems(List<JobsData> model) {
        list.addAll(model);
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
//        if(view == o){
//            Logger.e("sdvsvs");
//        }
//
//        if(o instanceof HomePagerAdapterBinding){
//
//            Logger.e("hgdjhgd===============");
//        }
        return view== o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        HomePagerAdapterBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.home_pager_adapter, container, false);

        binding.setData(new HomeAdapterViewModel(list.get(position)));

        binding.jobsCard.setOnClickListener(v ->
                context.startActivity(JobItemActivity.newIntent(context, START_AS_JOB_ITEM_SHORT,
                list.get(position).getId(), 0)));

        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}

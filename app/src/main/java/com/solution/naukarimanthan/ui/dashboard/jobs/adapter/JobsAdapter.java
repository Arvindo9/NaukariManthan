package com.solution.naukarimanthan.ui.dashboard.jobs.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseAdapter;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.databinding.JobsAdapterBinding;
import com.solution.naukarimanthan.ui.dashboard.jobs.JobsFragment;
import com.solution.naukarimanthan.ui.dashboard.jobs.JobsViewModel;
import com.solution.naukarimanthan.ui.jobs_item.JobItemActivity;
import com.solution.naukarimanthan.utils.AppConstants;
import com.solution.naukarimanthan.utils.ads.AdgebraAds;
import com.solution.naukarimanthan.utils.ads.AdsCallBack;

import java.util.ArrayList;
import java.util.List;

import static com.solution.naukarimanthan.utils.AppConstants.FACEBOOK_ADS;
import static com.solution.naukarimanthan.utils.AppConstants.START_AS_JOB_ITEM_SHORT;
import static com.solution.naukarimanthan.utils.ads.AdgebraAds.SMALL_CARD;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/9/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class JobsAdapter extends BaseAdapter<JobsAdapterBinding, JobsData>{
    private static final int EMPTY_VIEW = 1;
    private static final int JOBS_VIEW = 2;
    private final Context context;
    private final JobsViewModel viewModel;
    private final JobsFragment jobfragment;
    //    private final ArrayList<JobsData> list;
    private JobsAdapterListener mListener;

/*    public JobsAdapter(Context context, ArrayList<JobsData> list, JobsViewModel viewModel) {
        super(list);
        this.context = context;
        this.viewModel = viewModel;
    }*/

    public JobsAdapter(Context context, JobsFragment jobsFragment, ArrayList<JobsData> arrayList,
                       JobsViewModel jobsViewModel) {
        super(arrayList);
        this.context = context;
        this.jobfragment = jobsFragment;
        this.viewModel = jobsViewModel;
    }

    public interface JobsAdapterListener {
        void onRetryClick();
    }

    public void addItems(List<JobsData> model) {
        list.addAll(model);
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
        mainList.clear();
    }

    public void setListener(JobsAdapterListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == JOBS_VIEW) {
            return super.onCreateViewHolder(parent, viewType);
        }
        else{
//        else if(viewType == EMPTY_VIEW){

            int whichCompanyAds = (int) (Math.random() * 3) + 1;

            if(whichCompanyAds == FACEBOOK_ADS) {
                return new AdviewHolder(facebookAdsBanner());
            }
            else{
//                return new AdviewHolder(googleAdsBanner());
/*                View view = new View();
                return new AdviewHolder(googleAdsBanner());
                */

                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View view = inflater.inflate(R.layout.empty, null);
                return new AdviewHolder(view, adgebraAdsBanner(view));
            }
        }
    }

    private com.facebook.ads.AdView facebookAdsBanner(){
        com.facebook.ads.AdView
                adView = new com.facebook.ads.AdView(context,
                AppConstants.INSTANCE.FB_ADS_PLACEMENT_ID, com.facebook.ads.AdSize.BANNER_HEIGHT_90);

//        float density = context.getResources().getDisplayMetrics().density;
//        int height = Math.round(com.facebook.ads.AdSize.BANNER_HEIGHT_90 * density);
//        AbsListView.LayoutParams params = new
//                AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, height);
//        adView.setLayoutParams(params);

        adView.loadAd();
        return adView;
    }

/*    private void adgebraAdsBanner(){
        AdsCallBack adgebraAdsCallBack = new AdsCallBack() {
            @Override
            public void adLoaded(View view) {
                adgebraAdsView(view);
            }

            @Override
            public void Error() {

            }
        };
        AdgebraAds.setNativeAds(viewModel, (Activity) context, adgebraAdsCallBack, SMALL_CARD);
    }*/


    private AdsCallBack adgebraAdsBanner(View view) {
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.emptyView);
        AdsCallBack adsCallBack = new AdsCallBack() {
            public void Error() {
            }

            public void adLoaded(View view) {
                relativeLayout.addView(view);
            }
        };
        AdgebraAds.setNativeAds(this.viewModel, this.jobfragment.getActivity(), adsCallBack, SMALL_CARD);
        return adsCallBack;
    }

/*
    private AdView googleAdsBanner(){
        AdView adview = new AdView(context);
        adview.setAdSize(AdSize.LARGE_BANNER);

        // this is the good adview
        adview.setAdUnitId(context.getString(R.string.adsBannerSplash));

        float density = context.getResources().getDisplayMetrics().density;
        int height = Math.round(AdSize.LARGE_BANNER.getHeight() * density);
        AbsListView.LayoutParams params = new
                AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, height);
        adview.setLayoutParams(params);

        // dont use below if testing on a device
        // follow https://developers.google.com/admob/android/quick-start?hl=en to setup testing device
        AdRequest request = new AdRequest.Builder().build();
        adview.loadAd(request);
        return adview;
    }
*/

    /**
     * @param position current index of ArrayList
     * @return return 0 if single layout xml else override this method for multiple xml or elements
     */
    @Override
    protected int getItemViewTypeAdapter(int position) {
        if(list.get(position) == null){
            return EMPTY_VIEW;
        }
        return JOBS_VIEW;
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
        return new ViewHolder<JobsAdapterBinding, JobsData>(binding){

            /**
             * If there is anything to do then do here otherwise leave it blank
             *
             * @param binding layout reference for single view
             * @param data     for single view
             * @param position position of ArrayList
             */
            @Override
            protected void doSomeWorkHere(JobsAdapterBinding binding, JobsData data, int position) {

            }

            /**
             * @param data binding.setData(data);
             */
            @Override
            protected void bindData(JobsData data) {
                binding.setData(new JobsAdapterViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding    DataBinding
             * @param data        data
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, JobsAdapterBinding binding,
                                          JobsData data) {
                binding.jobCard.setOnClickListener(thisContext);
            }

            /**
             * Initialised holder by new operator
             *
             * @param binding DataBinding
             * @param data     dataList
             * @param position of adapter
             * @return new ViewHolderClickListener(binding, data, position)
             */
            @Override
            protected ViewHolderClickListener viewHolderReference(JobsAdapterBinding binding,
                                                                  JobsData data, int position) {
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
        return new FilterClass() {
            /**
             * @return Context, to initialise and use filter class pass activity or application context
             */
            @Override
            public Context getContext() {
                return null;
            }

            /**
             * @param constraint        JobsResponse
             *                          for (L data : list) {
             *                          if (data.getRefNo().toLowerCase().contains(constraint) ||
             *                          data.getZone().toLowerCase().contains(constraint))
             *                          filteredArrayList.add(data);
             *                          }
             * @param list              only one time, use in for loop
             * @param filteredArrayList This list will return with added data
             */
            @Override
            public ArrayList<JobsData> filterData(CharSequence constraint, ArrayList<JobsData> list,
                                                  ArrayList<JobsData> filteredArrayList) {
                boolean ok = false;
                for (JobsData data : list) {
                    if (data.getAboutJob().toLowerCase().contains(constraint) ||
                            data.getTitle().toLowerCase().contains(constraint)||
                            data.getDate().toLowerCase().contains(constraint)
                            ) {
                        filteredArrayList.add(data);
                        ok = true;
                    }
                }

                if(ok){
                    JobsAdapterViewModel.textFilter.set(constraint + "");
                }
                else{
                    JobsAdapterViewModel.textFilter.set("");
                }
                return filteredArrayList;
            }
        };
    }

    private class AdviewHolder extends ViewHolder<ViewDataBinding, JobsData> {
        private View view;
/*
y
        public AdviewHolder(AdView adview) {
            super(adview);
            this.view = adview;
        }
*/

        public AdviewHolder(View view) {
            super(view);
            this.view = view;
        }

        public AdviewHolder(com.facebook.ads.AdView adview) {
            super(adview);
            this.view = adview;
        }

        public AdviewHolder(View view, AdsCallBack adgebraAdsBanner) {
            super(view);
            this.view = view;
        }

        /**
         * If there is anything to do then do here otherwise leave it blank
         *
         * @param binding  layout reference for single view
         * @param data     for single view
         * @param position position of ArrayList
         */
        @Override
        protected void doSomeWorkHere(ViewDataBinding binding, JobsData data, int position) {

        }

        /**
         * @param data binding.setData(data);
         */
        @Override
        protected void bindData(JobsData data) {
            if(view instanceof com.facebook.ads.AdView){
                ((com.facebook.ads.AdView)view).setAdListener(new AdListener() {
                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
//                        view = googleAdsBanner();/**/
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

        /**
         * Method to set click listeners on view holder or groups
         *
         * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
         * @param binding     DataBinding
         * @param data        data
         */
        @Override
        public void setClickListeners(ViewHolderClickListener thisContext, ViewDataBinding binding, JobsData data) {

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
        protected ViewHolderClickListener viewHolderReference(ViewDataBinding binding, JobsData data,
                                                              int position) {
            return null;
        }
    }
}
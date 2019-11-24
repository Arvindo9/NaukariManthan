package com.solution.naukarimanthan.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
public abstract class BaseAdapter<B extends ViewDataBinding, D> extends
        RecyclerView
        .Adapter<BaseAdapter.ViewHolder>
        implements Filterable {

    protected String filterText = "";
    protected ArrayList<D> list;
    protected ArrayList<D> mainList;
    private FilterClass filter = null;

    /**
     * 
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public BaseAdapter(ArrayList<D> adapterList) {
        this.list = adapterList;
        this.mainList = adapterList;
    }

    @NonNull
    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        B binding = DataBindingUtil.inflate(inflater,
                getLayout(), parent, false);

        showHideView();

        doJobInOnCreate(parent, viewType, binding);

        // set the view's size, margins, paddings and layout parameters
        return getViewHolder(binding);
    }

    protected <VB extends ViewDataBinding> ViewDataBinding createBinding(ViewGroup parent, int
            viewType, @LayoutRes int layoutId){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return DataBindingUtil.<VB>inflate(inflater,
                layoutId, parent, false);
    }


    /**
     *
     * @param viewGroup view group
     * @param viewType view type
     * @param binding adapter binding
     */
    protected void doJobInOnCreate(ViewGroup viewGroup, int viewType, B binding) {}

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = getItemViewTypeAdapter(position);
        if(itemType != 0){
            return itemType;
        }
        return position;
    }

    /**
     *
     * @param position current index of ArrayList
     * @return return 0 if single layout xml else override this method for multiple xml or elements
     */
    protected abstract int getItemViewTypeAdapter(int position);


    /**
     * This will hide the view on first load
     * This is use to make the hidden view on first load
     * left blank if noting is hidden
     */
    protected void showHideView(){}

    /**
     *
     * @return R.layout.layout_file
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * Initialised View Holder
     * @param binding DataBinding
     * @return new ViewHolder(binding);
     */
    public abstract ViewHolder getViewHolder(B binding);

    //-----------------filter/search in adapter---------------------
    /**
     *
     * @return new FilterClass();
     */
    protected abstract FilterClass initialisedFilterClass();

    /**
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     * <p>
     * <p>This method is usually implemented by {@link Adapter}
     * classes.</p>
     *
     * @return a filter used to constrain data
     */
    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = initialisedFilterClass();
        return filter;
    }

    /**
     * class use for the filter of adapter view
     */
    public abstract class FilterClass extends Filter {
//        private AdapterFilterCalls adapterFilterCalls;
        private ArrayList<D> filteredArrayList;

        /**
         *
         * @return Context, to initialise and use filter class pass activity or application context
         */
        public abstract Context getContext();

        protected FilterClass(){
//            this.adapterFilterCalls = (AdapterFilterCalls) getContext();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            constraint = constraint.toString().toLowerCase();

            if (constraint.length() <= 0) {
                filteredArrayList = mainList;
            } else {
                filteredArrayList = new ArrayList<>();

                filteredArrayList = filterData(constraint, mainList, filteredArrayList);

                filterResults.count = filteredArrayList.size();
                filterResults.values = filteredArrayList;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            list = (ArrayList<D>) results.values;
            filterText = constraint + "";

            if (constraint == null || constraint.length() <= 0) {
                list = mainList;
                filterText = "";
            }

//            adapterFilterCalls.callBackToActivity(String.valueOf(filteredArrayList.size()));

            notifyDataSetChanged();
        }

        /**
         *
         * @param constraint JobsResponse
        for (L data : list) {
        if (data.getRefNo().toLowerCase().contains(constraint) ||
        data.getZone().toLowerCase().contains(constraint))
        filteredArrayList.add(data);
        }
         * @param list only one time, use in for loop
         * @param filteredArrayList This list will return with added data
         */
        public abstract ArrayList<D> filterData(CharSequence constraint,
                                                                  ArrayList<D>
                                                                          list,
                                                ArrayList<D> filteredArrayList);
    }

    //------------------view Holder---------------------------------

    public abstract class ViewHolder<B extends ViewDataBinding, D> extends RecyclerView.ViewHolder{
        protected int position;
        protected B binding;

        /**
         *
         * @param binding layout reference
         */
        protected ViewHolder(B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        protected ViewHolder(View view) {
            super(view);
        }

        void bind(D data, int position){
            this.position = position;
            bindData(data);
            setClickListeners(data, position);
            doSomeWorkHere(binding, data, position);
        }

        /**
         * If there is anything to do then do here otherwise leave it blank
         * @param binding layout reference for single view
         * @param data for single view
         * @param position position of ArrayList
         */
        protected abstract void doSomeWorkHere(B binding, D data, int position);

        /**
         *
         * @param data binding.setData(data);
         */
        protected abstract void bindData(D data);

        protected void setClickListeners(D data, int position) {
            ViewHolderClickListener viewHolderClickListener = viewHolderReference(binding, data,
                    position);
            setClickListeners(viewHolderClickListener, binding, data);
        }

        /**
         * Method to set click listeners on view holder or groups
         *
         * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
         * @param binding DataBinding
         * @param data data
         */
        public abstract void setClickListeners(ViewHolderClickListener thisContext,
                                                                     B binding, D data);


        /**
         * Initialised holder by new operator
         *
         * @param binding DataBinding
         * @param data dataList
         * @return new ViewHolderClickListener(binding, data, position)
         * @param position of adapter
         */
        protected abstract ViewHolderClickListener viewHolderReference(
                B binding, D data, int position);
    }

    //----------------------------ViewHolder Click Listener---------

    public abstract class ViewHolderClickListener implements View.OnClickListener{
        protected final int position;
        protected B binding;
        private final D data;

        /**
         *
         * @param position of a adapter in current view
         */
        public ViewHolderClickListener(B binding, int position, D data) {
            this.position = position;
            this.binding = binding;
            this.data = data;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public abstract void onClick(View v);
    }

    //--------------------------------------------------------------
}

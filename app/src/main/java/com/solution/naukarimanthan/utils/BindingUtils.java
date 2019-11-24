/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.solution.naukarimanthan.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.notification.Notification;
import com.solution.naukarimanthan.ui.category.adapter.CategoryAdapter;
import com.solution.naukarimanthan.ui.dashboard.home.adapter.HomeAdapter;
import com.solution.naukarimanthan.ui.dashboard.home.recent$jobs.RecentJobsAdapter;
import com.solution.naukarimanthan.ui.dashboard.jobs.adapter.JobsAdapter;
import com.solution.naukarimanthan.ui.dashboard.notification.NotificationAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */


public final class BindingUtils {

    //ObservableField must not have final
    public static ObservableField<Boolean> progressBarVisibility = new ObservableField<>();
    public static ObservableField<Boolean> searchBarActivityVisibility = new ObservableField<>();
    public static ObservableField<String> areaLocation = new ObservableField<>();
    public static ObservableList<JobsMenuTabs> menuTabsList = new ObservableArrayList<>();
    public static ObservableField<Boolean> searchBarVisibility = new ObservableField<>();


    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addJobsItem(RecyclerView recyclerView, List<JobsData> jobs) {
        JobsAdapter adapter = (JobsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(jobs);
        }
    }

    @BindingAdapter({"adapterHome"})
    public static void addHomeJobsItem(RecyclerView recyclerView, List<JobsData> jobs) {
        HomeAdapter adapter = (HomeAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(jobs);
        }
    }

    @BindingAdapter({"adapterPagerHome"})
    public static void addPagerHomeJobsItem(ViewPager viewPager, List<JobsData> jobs) {
        RecentJobsAdapter adapter = (RecentJobsAdapter) viewPager.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(jobs);
        }
    }

    @BindingAdapter({"adapterCategory"})
    public static void addCategoryItem(RecyclerView recyclerView, List<JobsMenuTabs> jobs) {
        CategoryAdapter adapter = (CategoryAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(jobs);
        }
    }

    @BindingAdapter({"adapterNotification"})
    public static void addNotificationItem(RecyclerView recyclerView, List<Notification> notification) {
        NotificationAdapter adapter = (NotificationAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(notification);
        }
    }

    @BindingAdapter({"imageView"})
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.logo_small)
                .error(R.drawable.logo_small)
                .fit()
                .into(imageView);
    }

    @BindingAdapter({"textFilter", "android:text"})
    public static void setFilterText(TextView textView, String text, String textFilter) {
        String textLower = text.toLowerCase(Locale.getDefault());
        if(textLower.contains(textFilter)){
            int startPos = textLower.indexOf(textFilter);
            int endPos = startPos + textFilter.length();

            Spannable spanText = Spannable.Factory.getInstance().newSpannable(text);
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(spanText, TextView.BufferType.SPANNABLE);
        }
        else{
            textView.setText(text);
        }
    }

    public static void onSearchIconOpenClick(){
        searchBarActivityVisibility.set(true);
    }

    public static void onSearchIconCloseClick(){
        searchBarActivityVisibility.set(false);
    }
}

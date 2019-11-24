package com.solution.naukarimanthan.ui.dashboard;

import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/8/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
interface DashboardNavigator {

    void tabsMenuList(List<JobsMenuTabs> jobsMenuTabs);

    void handleError(Throwable throwable);

    void onHomeButtonClick();

    void onNetworkAvailability(boolean networkState);

    void onRefreshButtonClick();

    void onCategoryClick();

    void openPlayStoreUpdate();

}

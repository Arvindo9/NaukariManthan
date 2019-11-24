package com.solution.naukarimanthan.ui.category;

import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/17/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
interface CategoryNavigator {


    void updateJobs(List<JobsMenuTabs> model);

    void handleError(Throwable throwable);

    void onLocationButtonClick();

    void onCheckInternetConnection();
}

package com.solution.naukarimanthan.ui.special_jobs;

import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/19/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
interface SpecialJobsNavigator {

    void updateJobs(List<JobsMenuTabs> model);

    void handleError(Throwable throwable);

    void onCheckInternetConnection();
}

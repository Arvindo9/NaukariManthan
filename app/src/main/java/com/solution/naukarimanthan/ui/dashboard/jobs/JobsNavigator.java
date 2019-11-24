package com.solution.naukarimanthan.ui.dashboard.jobs;

import com.solution.naukarimanthan.data.model.db.jobs.JobsData;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/9/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
interface JobsNavigator {

    void updateJobs(List<JobsData> model);

    void handleError(Throwable throwable);

    void setCurrentPage(int pageNo);
}

package com.solution.naukarimanthan.ui.dashboard.home;


import com.solution.naukarimanthan.data.model.db.jobs.JobsData;

import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-01-2019
 * Email        : arvindomondal@gmail.com
 */
public interface HomeNavigator {

    void updateJobs(List<JobsData> model);

    void handleError(Throwable throwable);

    void getRecentJobsPageNo(int pageNumber);

    void onRecentMoreClick();

    void onRecommendedMoreClick();

    void onLocalMoreClick();

    void onPopularMoreClick();

    void onSubscriptionClick();

    void onFeedbackClick();

    void onSubscriptionDone(String message);
}

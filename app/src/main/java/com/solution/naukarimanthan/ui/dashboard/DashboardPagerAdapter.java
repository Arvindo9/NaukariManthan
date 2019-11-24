package com.solution.naukarimanthan.ui.dashboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.ui.dashboard.home.HomeFragment;
import com.solution.naukarimanthan.ui.dashboard.jobs.JobsFragment;

import java.util.List;

import static com.solution.naukarimanthan.utils.AppConstants.JOBS_BY_MENU_ID;
import static com.solution.naukarimanthan.utils.AppConstants.START_AS_DEFAULT;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/9/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class DashboardPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;
    private List<JobsMenuTabs> tabsList;
    private SparseArray<Fragment> mPageReferenceMap;

    public DashboardPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 1;
        mPageReferenceMap = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
        notifyDataSetChanged();
    }

    public void setValues(List<JobsMenuTabs> jobsMenuTabs) {
        this.tabsList = jobsMenuTabs;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if(tabsList != null && position > 0) {
            String title = "Accounting";
            title = tabsList.get(position).getMenu();
            int menuId = tabsList.get(position).getMenuId();

            Fragment fragment = JobsFragment.newInstance(START_AS_DEFAULT, JOBS_BY_MENU_ID, title, menuId);
            mPageReferenceMap.put(position, fragment);
            return fragment;
        }
        else{
            Fragment fragment = HomeFragment.newInstance();
            mPageReferenceMap.put(position, fragment);
            return fragment;
        }
//        return null;
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferenceMap.remove(position);
    }

    public Fragment getFragment(int key) {
        return mPageReferenceMap.get(key);
    }
}

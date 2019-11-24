package com.solution.naukarimanthan.ui.jobs_item;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.job$item.JobItem;
import com.solution.naukarimanthan.utils.Logger;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/21/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class JobItemViewModel extends BaseViewModel<JobItemNavigator> {


    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<String> menu = new ObservableField<>();
    public final ObservableField<String> date = new ObservableField<>();
    public final ObservableField<String> time = new ObservableField<>();

    private final MutableLiveData<JobItem> jobsItemLiveData;

    public JobItemViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        jobsItemLiveData = new MutableLiveData<>();
    }

    public void start(int jobId){
        loadJobItemDataFromDb(jobId);
    }

    public void start(int jobId, int descId){
        loadJobItemDataFromDbLong(jobId, descId);
    }

    //Item short data--------------------------

    //1) check db for availability of data, if not found the load from api
    private void loadJobItemDataFromDb(int jobId) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .loadJobItmShort(jobId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response != null && !response.getTitle().isEmpty()){
                        jobsItemLiveData.setValue(response);
                    }
                    else{
                        loadJobItemDataFromApi(jobId);
                    }
                    setIsLoading(false);
                },throwable ->{
                    setIsLoading(false);
                    loadJobItemDataFromApi(jobId);
                    Logger.d("Failed to Load menus");
                    getNavigator().handleError(throwable);
                }));
    }

    //2) check api for availability of data, and save to db
    private void loadJobItemDataFromApi(int jobId) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getJobsItemShort(jobId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response != null && !response.getData().isEmpty()){
                        jobsItemLiveData.setValue(response.getData().get(0));
                        saveJobItemDataToDb(response.getData().get(0));
                    }
                    setIsLoading(false);
                },throwable ->{
                    setIsLoading(false);
                    Logger.d("Failed to Load menus");
                    getNavigator().handleError(throwable);
                }));
    }

    //3) save job item to db
    private void saveJobItemDataToDb(JobItem jobItem) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .saveJobItm(jobItem)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> Logger.d("save JobItem short to db"), throwable ->{
                    Logger.e("Failed JobItem short to db");
                    getNavigator().handleError(throwable);
                }));
    }

    //Item long data--------------------------

    //1) check db for availability of data, if not found the load from api
    private void loadJobItemDataFromDbLong(int jobId, int shortDescriptionId) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .loadJobItmLong(jobId, shortDescriptionId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response != null && !response.getTitle().isEmpty() &&
                            response.getLongdesc() != null && response.getLongdesc().equals("")){
                        jobsItemLiveData.setValue(response);
                    }
                    else{
                        loadJobItemDataFromApiLong(jobId, shortDescriptionId);
                    }
                    setIsLoading(false);
                },throwable ->{
                    setIsLoading(false);
                    loadJobItemDataFromApiLong(jobId, shortDescriptionId);
                    Logger.d("Failed to Load menus");
                    getNavigator().handleError(throwable);
                }));
    }

    //2) check api for availability of data, and save to db
    private void loadJobItemDataFromApiLong(int jobId, int shortDescriptionId) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getJobsItemLong(jobId, shortDescriptionId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response != null && !response.getData().isEmpty()){
                        jobsItemLiveData.setValue(response.getData().get(0));
                        saveJobItemDataToDb(response.getData().get(0));
                    }
                    setIsLoading(false);
                },throwable ->{
                    setIsLoading(false);
                    Logger.d("Failed to Load menus");
                    getNavigator().handleError(throwable);
                }));
    }

    //End-------------------------------------

    public MutableLiveData<JobItem> getJobsItemLiveData() {
        return jobsItemLiveData;
    }

    public void addJobsItems(JobItem jobs) {
        title.set(jobs.getTitle());
        image.set(jobs.getImageUrl());
        description.set(jobs.getAboutJob());
        menu.set(jobs.getMenu());
        date.set(jobs.getDate());
        time.set(jobs.getTime());
    }

    public void onViewDetailClick(){
        getNavigator().onViewDetailClick();
    }
}

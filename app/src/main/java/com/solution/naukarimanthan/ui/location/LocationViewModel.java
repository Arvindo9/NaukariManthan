package com.solution.naukarimanthan.ui.location;

import android.app.Activity;
import android.databinding.ObservableField;
import android.location.Address;
import android.location.Geocoder;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.states$city.StatesAndCity;
import com.solution.naukarimanthan.ui.category.CategoryViewModel;
import com.solution.naukarimanthan.ui.dashboard.DashboardViewModel;
import com.solution.naukarimanthan.utils.Logger;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/16/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class LocationViewModel extends BaseViewModel<LocationNavigator> {


    private String code;
    private String city;
    private int stage = 1;

    public LocationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAddress(Activity activity, double lat, double lng) {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if(addresses != null && !addresses.isEmpty()) {
                Address obj = addresses.get(0);
                String add = obj.getAddressLine(0);
                String city = null;

                if(obj.getAdminArea() != null){
                    add = obj.getAdminArea();
                    city = obj.getLocality();
                }else if (obj.getLocality() != null) {
                    add = obj.getLocality();
                } else if (obj.getPostalCode() != null) {
                    add = obj.getPostalCode();
                } else if (obj.getSubAdminArea() != null) {
                    add = obj.getSubAdminArea();
                }

                this.code = add;
                this.city = city;
                saveState();

                Logger.d("IGA", "Address" + add);
            }
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            getNavigator().openNextActivity();
        }
    }

    private void saveState(){
        if(code != null && !code.equals("")) {
            getCompositeDisposable().add(getDataManager()
                    .getAllStateCity()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if(response != null && !response.isEmpty()) {
                            for (StatesAndCity data1 : response) {
                                if (code.contains(data1.getStateName())) {
                                    String name = "";
                                    if (city != null) {
                                        name = city + ", " + data1.getStateName();
                                    }

                                    DashboardViewModel.areaLiveData.setValue(name);
                                    CategoryViewModel.areaLiveData.setValue(name);
                                    getDataManager().setStateName(name);
                                    getDataManager().setStateCode(data1.getStateId());

                                    break;
                                }
                            }

                            getNavigator().openNextActivity();
                        }
                        else{
                            if(stage == 1) {
                                stage++;
                                loadStateCity();
                            }
                            else{
                                getNavigator().openNextActivity();
                            }
                        }

                        Logger.d( "Jobs data saved to Db");
                        //do noting
                    }, throwable -> {
                        getNavigator().openNextActivity();
                        Logger.d( "Jobs data failed to Db");
                        //do nothing
                    }));
        }
    }

    private void loadStateCity() {
        getCompositeDisposable().add(getDataManager()
                .getAreaCodeApi()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        saveStateCity(response.getData());
                    }
                }, throwable ->
                        getNavigator().handleError(throwable)));
    }

    private void saveStateCity(List<StatesAndCity> data) {
        getCompositeDisposable().add(getDataManager()
                .saveStateCityList(data)
                .doOnComplete(() -> Logger.d("Success save jobs tabs"))
                .doOnError(throwable -> getNavigator().handleError(throwable))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    saveState();
                    //Inserted
                },throwable -> getNavigator().handleError(throwable)));
    }
}

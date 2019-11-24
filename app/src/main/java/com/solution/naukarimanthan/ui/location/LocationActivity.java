package com.solution.naukarimanthan.ui.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseActivity;
import com.solution.naukarimanthan.databinding.LocationActivityBinding;
import com.solution.naukarimanthan.ui.dashboard.Dashboard;
import com.solution.naukarimanthan.utils.Logger;

import javax.inject.Inject;

import static com.solution.naukarimanthan.utils.AppConstants.REQUEST_CHECK_SETTINGS;


public class LocationActivity extends BaseActivity<LocationActivityBinding, LocationViewModel>
        implements LocationNavigator {

    private static final String TAG = LocationActivity.class.getSimpleName();
    public static final String ON_LACATION_SCREEC = "ON_LACATION_SCREEC";
    public static final int CALL_FROM_CATEGORY_ACTIVITY = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private double latitudeLast = 0.0;
    private double longitudeLast = 0.0;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private boolean ENABLE_LOCATION_REQUEST = false;
    private static boolean IS_CAPTURED_CURRENT_LOCATION = false;
    private ResolvableApiException resolvable;
    private LocationNavigator locationNavigator;
    private String mobile;
    private LocationActivityBinding binding;
    private int callType;

    public static Intent newIntent(Context context) {
        return new Intent(context, LocationActivity.class);
    }

    @Inject
    LocationViewModel viewModel;

    @Override
    protected void initialization(@Nullable Bundle state) {

    }

    /**
     * @param context is a Activity class context or instance
     */
    @Override
    public void getContext(Context context) {

    }

    @Override
    protected void init() {
        viewModel.setNavigator(this);
        binding.content.startRippleAnimation();
        locationNavigator = this;

        callType = getIntent().getIntExtra(ON_LACATION_SCREEC, 0);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        createLocationRequest();

        currentUpdatedLocationListner();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        createLocationRequest();
    }

    /**
     * @param binding activity class data binding
     */
    @Override
    public void getActivityBinding(LocationActivityBinding binding) {
        this.binding = binding;
    }

    @Override
    protected void onStart() {
        super.onStart();
        createLocationRequest();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected int getLayout() {
        return R.layout.location_activity;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }


    @Override
    public LocationViewModel getViewModel() {
        return viewModel;
    }

    //----------------Permission---------------

    @Override
    public void onPermissionGranted() {
        startLocationUpdates();
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if(isPermissionGranted()) {
            if (ENABLE_LOCATION_REQUEST) {
                mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                        mLocationCallback,
                        null /* Looper */);

                getLastLocation();
            }
            else {
                createLocationRequest();
            }
        }
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    //---------------Checking for GPS-----------------------------

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        builder.setNeedBle(true);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, locationSettingsResponse -> {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...

            Log.e(TAG, "onSuccess");
            ENABLE_LOCATION_REQUEST = true;
            startLocationUpdates();

        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        Log.e(TAG, "onFailure");
                        resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(activity,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    //---------------Currently updated location--------------------

    private void currentUpdatedLocationListner(){
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    locationNavigator.onUpdateCurrentLocation();

                    stopLocationUpdates();
                }
            };
        };
    }

    //---------------Last known location---------------------------

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location mLastLocation = task.getResult();

                            latitudeLast = mLastLocation.getLatitude();
                            longitudeLast = mLastLocation.getLongitude();

                            Log.e(TAG + " Latitude last", "onComplete:" + String.valueOf
                                    (latitudeLast));
                            Log.e(TAG + " longitude last", "onComplete:" + String.valueOf
                                    (longitudeLast));

                            locationNavigator.onUpdateLastLocation();

                        } else {
                            Log.e(TAG, " getLastLocation:exception", task.getException());
                        }
                    }
                });

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            latitudeLast = location.getLatitude();
                            longitudeLast = location.getLongitude();

                            locationNavigator.onUpdateLastLocation();

                            Log.e(TAG + " Latitude last", "onSuccess:" + String.valueOf
                                    (latitudeLast));
                            Log.e(TAG + " longitude last", "onSuccess:" + String.valueOf
                                    (longitudeLast));
                        }
                    }
                });
    }

    //----------------On GPS device on-----------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made

                        ENABLE_LOCATION_REQUEST = true;
                        startLocationUpdates();

                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        showSnackbarOnCancel();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private void showSnackbarOnCancel(){
        if(isPermissionGranted()) {
            Snackbar mSnackBar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.enable_gps),
                    Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.enable),
                    v -> {
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            Log.e(TAG, " showSnackbarOnCancel");
                            resolvable.startResolutionForResult(activity,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    });
            mSnackBar.setActionTextColor(getResources().getColor(R.color.snackBarActionColor));
            mSnackBar.show();
        }
    }

    //----------------When location catch--------------------------

    @Override
    public void onUpdateLastLocation() {
        Logger.e(TAG + " Latitude", "last:" + String.valueOf(latitudeLast));
        Logger.e(TAG + " longitude", "last:" + String.valueOf(longitudeLast));

        new Handler().postDelayed(() -> {
            // This method will be executed once the timer is over
            // Start your app main activity
            if(!IS_CAPTURED_CURRENT_LOCATION){
                latitude = latitudeLast;
                longitude = longitudeLast;
                //Do after
                onLocationGet();
            }
        }, 5000);
    }

    @Override
    public void onUpdateCurrentLocation() {
        Logger.e(TAG + " Latitude", "listener:" + String.valueOf(latitude));
        Logger.e(TAG + " longitude", "listener:" + String.valueOf
                (longitude));

        IS_CAPTURED_CURRENT_LOCATION = true;
        onLocationGet();
    }

    @Override
    public void openNextActivity() {
        if(callType == CALL_FROM_CATEGORY_ACTIVITY){
            finish();
        }
        else{
            startActivity(Dashboard.newIntent(context));
            finish();
        }
    }

    @Override
    public void onNetworkAvailability(boolean networkState) {

    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void onLocationGet(){
        if(isNetworkAvailable()) {
            viewModel.getAddress(this, latitude, longitude);
        }
        else if(callType == CALL_FROM_CATEGORY_ACTIVITY){
            showToast(R.string.network_not_found);
        }
        else{
            viewModel.getNavigator().openNextActivity();
        }
    }
}

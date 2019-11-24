package com.solution.naukarimanthan.ui.location;


public interface LocationNavigator {
    void onUpdateLastLocation();

    void onUpdateCurrentLocation();

    void openNextActivity();

    void onNetworkAvailability(boolean networkState);

    void handleError(Throwable throwable);
}

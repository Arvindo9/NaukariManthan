package com.solution.naukarimanthan.ui.launcher.splash;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.solution.naukarimanthan.BR;
import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseActivity;
import com.solution.naukarimanthan.databinding.SplashActivityBinding;
import com.solution.naukarimanthan.ui.dashboard.Dashboard;
import com.solution.naukarimanthan.ui.dashboard.dialog.UpdateDialog;
import com.solution.naukarimanthan.ui.launcher.welcome.WelcomeActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import static com.solution.naukarimanthan.utils.AppConstants.FIREBASE_NOTIFICATION_TOPIC;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class SplashActivity extends BaseActivity<SplashActivityBinding, SplashViewModel> implements
        SplashNavigator{

    private static final String TAG = SplashActivity.class.getSimpleName();
    private Context context;

    @Inject
    SplashViewModel splashViewModel;

    private SplashActivityBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    /**
     * @param binding activity class data binding
     */
    @Override
    public void getActivityBinding(SplashActivityBinding binding) {
        this.binding = binding;
    }

    /**
     * @param state Initialise any thing here before binding
     */
    @Override
    protected void initialization(@Nullable Bundle state) {

    }

    /**
     * @param context is a Activity class context or instance
     */
    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    /**
     * Do anything on onCreate after binding
     */
    @Override
    protected void init() {
        REQUEST_PERMISSION_FOR_ACTIVITY = false;
        splashViewModel.setNavigator(this);
//        MobileAds.initialize(this, AppConstants.INSTANCE.ADMOB_APP_ID);

        splashViewModel.isPermissionGranted = isPermissionGranted();

        loadBannerAds();
        splashViewModel.start(getCurrentVersionOfApp(), new WeakReference<>(this));

        setUpNotification();
    }

    private String getCurrentVersionOfApp(){
        String version = "0";

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.splash_activity;
    }

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    @Override
    public int getBindingVariable() {
        return BR.data;
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    @Override
    public SplashViewModel getViewModel() {
        return splashViewModel;
    }

    @Override
    public void openWelcomeScreen() {
        startActivity(WelcomeActivity.newIntent(context));
        finish();
    }

    @Override
    public void openLoginActivity() {
    }

    @Override
    public void openHomeActivity() {
        startActivity(Dashboard.newIntent(context));
        finish();
    }

    @Override
    public void openPlayStoreUpdate() {
        /*
        try {
            startActivityForResult(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + APP_PACKAGE_NAME)), 41);
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivityForResult(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" +
                            APP_PACKAGE_NAME)), 41);
        }
*/
        try {
            UpdateDialog updateDialog = new UpdateDialog();
            updateDialog.show(getSupportFragmentManager(), "UpdateDialog");
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
        if(throwable instanceof IOException){
            runOnUiThread(() -> showToast(R.string.network_error));
        }
    }

    @Override
    public void openLoginWithGoogle() {

    }

    @Override
    public void openLoginWithLinkedIn() {

    }

    @Override
    public void onNetworkAvailability(boolean networkState) {

    }

    public void loadBannerAds(){
//        binding.adView.loadAd(new AdRequest.Builder().build());
//        binding.adView2.loadAd(new AdRequest.Builder().build());
    }

    //Notification-----------------------------------------------


    private void setUpNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a Notification message is tapped, any data accompanying the Notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the Notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the Notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying Notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

//        playService(this);
//        getToken();
//        subscribToTopic();
    }

    protected void IsPlayServicesAvailable() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (resultCode == ConnectionResult.SUCCESS){
            Log.e(TAG, "isGooglePlayServicesAvailable SUCCESS");
        } else {
            GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 1).show();
        }
    }

    private void getToken(){
        // Get token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();

                    // Log and toast
                    String msg = getString(R.string.msg_token_fmt, token);
                    Log.d(TAG, msg);
                    Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                });


    }

    private void subscribToTopic(){
        Log.d(TAG, "Subscribing to weather topic");
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic(FIREBASE_NOTIFICATION_TOPIC)
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d(TAG, msg);
                    Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
        // [END subscribe_topics]
    }

    private static void playService(Activity activity) {
        if(!isGooglePlayServicesAvailable(activity)){
            Log.e(TAG, "play service is not installed");

            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(activity);
        }
        else{

            Log.e(TAG, "isGooglePlayServicesAvailable SUCCESS");
        }
    }

    public static boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }
}

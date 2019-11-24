package com.solution.naukarimanthan.core.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.solution.naukarimanthan.utils.Logger;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/17/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class NetworkBroadcast extends BroadcastReceiver {

    public static final String TAG = NetworkBroadcast.class.getSimpleName();
    public static int times = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.e(TAG, " NetworkBroadcast called :" + times++);
    }
}

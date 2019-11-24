package com.solution.naukarimanthan.ui.dashboard.dialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.solution.naukarimanthan.R;

import static com.solution.naukarimanthan.utils.AppConstants.APP_PACKAGE_NAME;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/22/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class UpdateDialog extends DialogFragment {

    public UpdateDialog(){
        super();
    }


    // this method create view for your Dialog
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout with recycler view
        View v = inflater.inflate(R.layout.update, container, false);
        Button update = v.findViewById(R.id.update);
        ImageView cancel = v.findViewById(R.id.cancel);

        cancel.setOnClickListener(v1 -> dismiss());

        update.setOnClickListener(v12 -> {
            try {
                startActivityForResult(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + APP_PACKAGE_NAME)), 41);
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivityForResult(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" +
                                APP_PACKAGE_NAME)), 41);
            }
        });

        return v;
    }
}

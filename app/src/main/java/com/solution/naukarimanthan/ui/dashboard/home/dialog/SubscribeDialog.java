package com.solution.naukarimanthan.ui.dashboard.home.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.ui.dashboard.home.HomeViewModel;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/22/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class SubscribeDialog extends DialogFragment {

    private Object viewModel;
    private EditText email;
    private EditText userName;

    public SubscribeDialog(){
        super();
    }


    @SuppressLint("ValidFragment")
    public SubscribeDialog(Object viewModel){
        this();
        this.viewModel = viewModel;
    }


    private boolean validateUserName() {
        if (userName.getText().toString().trim().isEmpty()) {
            userName.setError(getString(R.string.errorUserName));
            return false;
        } else {
            userName.setError(null);
        }

        return true;
    }


    private boolean validateEmail() {
        if (email.getText().toString().trim().isEmpty() ||
                !email.getText().toString().contains("@") ||
                !email.getText().toString().contains(".") ) {
            email.setError(getString(R.string.errorEmail));
            return false;
        } else {
            email.setError(null);
        }

        return true;
    }

    // this method create view for your Dialog
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout with recycler view
        View v = inflater.inflate(R.layout.subscription, container, false);
        userName = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        EditText fieldInterest = v.findViewById(R.id.fieldInterest);
        Button submit = v.findViewById(R.id.submit);
        ImageView cancel = v.findViewById(R.id.cancel);

        cancel.setOnClickListener(v1 -> dismiss());

        submit.setOnClickListener(v12 -> {
            String nameS = userName.getText().toString();
            String emailS = email.getText().toString();
            String field = fieldInterest.getText().toString();

            if(validateEmail()){
                callApi(nameS, emailS, field);
                dismiss();
            }

        });

        return v;
    }

    private void callApi(String nameS, String emailS, String field) {
        ((HomeViewModel) viewModel).subscribeToApi(nameS, emailS, "", field);
    }
}

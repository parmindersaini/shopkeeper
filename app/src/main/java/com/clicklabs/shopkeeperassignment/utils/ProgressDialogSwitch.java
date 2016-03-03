package com.clicklabs.shopkeeperassignment.utils;

import android.app.Activity;
import android.app.ProgressDialog;


public class ProgressDialogSwitch {

    private static ProgressDialog loading = null;

    public static void showProgressDialog(Activity context, String message) {

        dismissProgressDialog();

        if (context.isFinishing())
            return;


        loading = new ProgressDialog(context);


        if (message == null) {
            loading.setMessage("Loading...");
        } else {
            loading.setMessage(message);
        }

        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setCancelable(true);
        loading.show();
    }


    public static void dismissProgressDialog() {
        if (loading != null && loading.isShowing())
            loading.dismiss();
    }


}

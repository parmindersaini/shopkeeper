package com.clicklabs.shopkeeperassignment.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class DisplayAlert {

    private static AlertDialog alertDialog = null ;
    private static customActionInterface customActionInterface ;

    public static void showAlert(Context context,String msg) {
        if(alertDialog==null) {
            alertDialog = new AlertDialog.Builder(context).create();
        }


        alertDialog.setTitle("Alert");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }
    public static void showAlert(Context context,String msg,customActionInterface customActionInterfaceObject) {

        customActionInterface =customActionInterfaceObject;
        if(alertDialog==null) {
            alertDialog = new AlertDialog.Builder(context).create();
        }


        alertDialog.setTitle("Alert");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        customActionInterface.onButtonClick();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public  interface customActionInterface{

        void onButtonClick();
    }

}

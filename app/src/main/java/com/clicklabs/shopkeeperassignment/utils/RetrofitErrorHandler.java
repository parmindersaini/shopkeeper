package com.clicklabs.shopkeeperassignment.utils ;

import android.app.Activity;
import android.util.Log;


import org.json.JSONObject;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class RetrofitErrorHandler {


    public static void checkCode(final Activity context, RetrofitError error) {
        try {
            String errorMessage = "";
            Log.v("error bundle", error.toString() + "");

            switch (error.getKind()) {
                case NETWORK:
                    errorMessage = "Failed to connect. Please check your internet connection.";

                    break;
                case CONVERSION:
                    errorMessage = "An error was procured while parsing.";
                    break;
                case HTTP:
                    errorMessage = "Application server could not respond. Please try later.";

                    try {
                        String str = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                        JSONObject json = new JSONObject(str);
                        errorMessage = json.getString("message");

                    } catch (Exception e) {
                        errorMessage = "Something went wrong. Please try later.";
                    }

                break;

                case UNEXPECTED:
                    errorMessage = "An unexpected error occurred. Please try later.";
                    break;
            }

            if (!errorMessage.isEmpty())
                DisplayAlert.showAlert(context, errorMessage);


        } catch (Exception e) {
            DisplayAlert.showAlert(context, "Something went wrong. Please try later");

        }
    }




}

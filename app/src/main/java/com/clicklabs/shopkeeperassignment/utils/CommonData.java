package com.clicklabs.shopkeeperassignment.utils;

import android.content.Context;

import com.clicklabs.shopkeeperassignment.models.ShopkeeperData.Shopkeeper;
import com.clicklabs.shopkeeperassignment.models.customersdata.CustomersData;

import io.paperdb.Paper;


public class CommonData {

    private static Shopkeeper AppUser = null;
    private static CustomersData customersDataObject = null;


    public static Shopkeeper getAppUser(Context context) {
        if (AppUser == null) {
//            AppUser = Prefs.with(context).getObject(AppConstants.APP_USER, Shopkeeper.class);
           AppUser =  Paper.book().read(AppConstants.APP_USER);
        }
        return AppUser;
    }


    public static void saveAppUser(Shopkeeper appUser, Context context) {

//        Prefs.with(context).save(AppConstants.APP_USER,appUser);
        Paper.book().write(AppConstants.APP_USER,appUser);
        AppUser = appUser;
    }

    public static CustomersData getCustomersData(Context context) {
        if (customersDataObject == null) {
//            customersDataObject = Prefs.with(context).getObject(SharedPreferencesName.CUSTOMER_DATA, CustomersData.class);
            customersDataObject = Paper.book().read(AppConstants.CUSTOMER_DATA);
        }
        return customersDataObject;
    }

    public static void setCustomersData(CustomersData customersData, Context context) {

        //Prefs.with(context).save(SharedPreferencesName.CUSTOMER_DATA, customersData);
        Paper.book().write(AppConstants.CUSTOMER_DATA,customersData);
        customersDataObject = customersData;

    }

    public static void clearAllAppData(Context context) {

        AppUser= null ;
        customersDataObject=null;
        Paper.book().destroy();
        //Prefs.with(context).removeAll();
    }


}

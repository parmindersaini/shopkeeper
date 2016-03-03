package com.clicklabs.shopkeeperassignment.config;



public class Config {


    private static String GCM_PROJECT_NUMBER = "";
    private static String BASE_URL = "";
    private static String FLURRY_KEY = "";
    private static final AppMode appMode = AppMode.TEST;

    static public String getBaseURL() {
        init();
        return BASE_URL;
    }

    static public String getFlurryKey() {

        init();

        return FLURRY_KEY;
    }

    static public String getGCMProjectNumber() {

        init();

        return GCM_PROJECT_NUMBER;
    }



    private static void init() {

        switch (Config.appMode) {
            case DEV:

                BASE_URL = "http://api.deets.clicklabs.in:1440/";
                FLURRY_KEY = "MNZJSQ9YV376F3NM39VZ";
                GCM_PROJECT_NUMBER = "563232976573";
                break;

            case TEST:

                BASE_URL = "http://54.173.40.155:3000/";
                FLURRY_KEY = "MNZJSQ9YV376F3NM39VZ";
                GCM_PROJECT_NUMBER = "563232976573";
                break;

            case LIVE:

                BASE_URL = "base URl for live mode";
                FLURRY_KEY = "flurry key for live mode";
                break;

        }


    }

    public enum AppMode {
        DEV, TEST, LIVE
    }

}
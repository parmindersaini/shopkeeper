package com.clicklabs.shopkeeperassignment.retrofit;

import com.clicklabs.shopkeeperassignment.config.Config;

import retrofit.RestAdapter;


/**
 * Rest client
 */
public class RestClient {
    private static ApiService apiService = null;

    public static ApiService getApiService() {
        if (apiService == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Config.getBaseURL())
                    .build();



            apiService = restAdapter.create(ApiService.class);
        }
        return apiService;
    }


}

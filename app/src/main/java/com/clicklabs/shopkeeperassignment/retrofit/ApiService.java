package com.clicklabs.shopkeeperassignment.retrofit;


import com.clicklabs.shopkeeperassignment.models.ShopkeeperData.Shopkeeper;
import com.clicklabs.shopkeeperassignment.models.customersdata.CustomersData;
import com.clicklabs.shopkeeperassignment.models.registerCustomer.RegisterCustomer;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;


/**
 *Define all server calls here
 */
public interface ApiService {

  @FormUrlEncoded
  @POST("/api/admin/createSupplier")
  void createSupplier(@Field("name") String name, @Field("phoneNo") String number, @Field("email") String email, @Field("password") String password, Callback<Shopkeeper> callback);

  @FormUrlEncoded
  @POST("/api/admin/registerDriver")
  void registerDriver(@Header("authorization") String accessToken, @Field("name") String name, @Field("phoneNo") String phoneNo, @Field("email") String email, @Field("address") String address, Callback<RegisterCustomer> registerDriverCallback);

  @GET("/api/admin/getAlldriver")
  void getAllDrivers(@Header("authorization") String accessToken,Callback<CustomersData> callback);


  @DELETE("/api/admin/deleteDriver")
  void deleteDriver(@Header("authorization") String accessToken,@Field("driverId") String userId,Callback<CustomersData> callback);

  @PUT("/api/admin/editDriver")
  void editDriver(@Header("authorization") String accessToken,@Field("password") String password,Callback<CustomersData> callback);

  @FormUrlEncoded
  @POST("/api/admin/supplierLogin")
  void login(@Field("email") String email,@Field("password") String password,Callback<Shopkeeper> callback);


//  public void createSupplier(Callback<Shopkeeper> callback);
//   void getUsers(Callback<List<CustomerData>> callback);



}
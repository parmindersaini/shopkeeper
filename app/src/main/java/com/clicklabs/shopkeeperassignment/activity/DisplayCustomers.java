package com.clicklabs.shopkeeperassignment.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clicklabs.shopkeeperassignment.R;
import com.clicklabs.shopkeeperassignment.adapters.CustomersDisplayRecyclerAdapter;
import com.clicklabs.shopkeeperassignment.models.customersdata.Customer;
import com.clicklabs.shopkeeperassignment.models.customersdata.CustomersData;
import com.clicklabs.shopkeeperassignment.retrofit.RestClient;
import com.clicklabs.shopkeeperassignment.utils.CommonData;
import com.clicklabs.shopkeeperassignment.utils.ProgressDialogSwitch;
import com.clicklabs.shopkeeperassignment.utils.RetrofitErrorHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DisplayCustomers extends BaseActivity implements View.OnClickListener {
    private String accessToken;
    private List<Customer> dataList = new ArrayList<>();
    private CustomersDisplayRecyclerAdapter customersDisplayRecyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_display);
        init();
        getAccessToken();
        getAllCustomersDataAndSetAdapter();
    }

    private void getAccessToken() {
        accessToken = CommonData.getAppUser(getApplicationContext()).getData().get(0).getAccessToken();

    }


    private void getAllCustomersDataAndSetAdapter() {


        if (CommonData.getCustomersData(getApplicationContext()) == null || CommonData.getCustomersData(getApplicationContext()).getData().size() == 0)
            ProgressDialogSwitch.showProgressDialog(DisplayCustomers.this, "Loading Customers");


        RestClient.getApiService().getAllDrivers("bearer " + accessToken, new Callback<CustomersData>() {
            @Override
            public void success(CustomersData customersData, Response response) {


                CommonData.setCustomersData(customersData, getApplicationContext());

                ProgressDialogSwitch.dismissProgressDialog();
                customersDisplayRecyclerAdapter.resetData(customersData.getData());

                Log.v("TAG", accessToken);
            }

            @Override
            public void failure(RetrofitError error) {
                ProgressDialogSwitch.dismissProgressDialog();
                RetrofitErrorHandler.checkCode(DisplayCustomers.this, error);
            }
        });
    }


    private void init() {
        initToolbar();
        TextView tvToolbarActivityName = (TextView) findViewById(R.id.tv_toolBar_activity_name);
        tvToolbarActivityName.setText(getClass().getSimpleName());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_customer_data);
        initAdapter();


    }

    private void initAdapter() {
        if (CommonData.getCustomersData(getApplicationContext()) != null) {
            dataList = CommonData.getCustomersData(getApplicationContext()).getData();
        }
        customersDisplayRecyclerAdapter = new CustomersDisplayRecyclerAdapter(this, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(customersDisplayRecyclerAdapter);

    }

    private void initToolbar() {
        initFloatingActionButton();
        Button btnBackToolbar = (Button) findViewById(R.id.btn_toolBar_back);
        Button btnToolbarLogOut = (Button) findViewById(R.id.btn_toolbar_logout);
        btnBackToolbar.setVisibility(View.GONE);
        btnToolbarLogOut.setVisibility(View.VISIBLE);
        btnToolbarLogOut.setOnClickListener(this);

    }

    private void initFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                startAddCustomerActivity();
                break;
            case R.id.btn_toolbar_logout:
                displayLogOutAlert();
                break;

        }
    }

    private void startAddCustomerActivity() {
        Intent intent = new Intent(DisplayCustomers.this, AddCustomer.class);
        startActivityForResult(intent, requestCodeForAddCustomerActivity);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == requestCodeForAddCustomerActivity) {
            if (resultCode == Activity.RESULT_OK) {
                getAllCustomersDataAndSetAdapter();

            }
        }
    }

    private void displayLogOutAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(
                DisplayCustomers.this);
        alert.setTitle("Alert!!");
        alert.setMessage("Are you sure to Log Out?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommonData.clearAllAppData(getApplicationContext());
                Intent intent = new Intent(DisplayCustomers.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();
    }

}

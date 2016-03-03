package com.clicklabs.shopkeeperassignment.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.clicklabs.shopkeeperassignment.R;
import com.clicklabs.shopkeeperassignment.models.customersdata.Customer;
import com.clicklabs.shopkeeperassignment.models.customersdata.CustomersData;
import com.clicklabs.shopkeeperassignment.retrofit.RestClient;
import com.clicklabs.shopkeeperassignment.utils.CommonData;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DisplayDetailsCustomer extends BaseActivity implements View.OnClickListener {

    private EditText edtDetailsName;
    private EditText edtDetailsId;
    private EditText edtDetailsPhone;
    private EditText edtDetailsAddress;
    private EditText edtDetailsEmail;
    private Customer dataSet;
    private TextView btnDetailsDelete;
    private String accessToken;
    private Button btnBackToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details_customer);
        dataSet = (Customer) getIntent().getSerializableExtra("onClickDataDisplay");
        init();
        setData();
        getAccessToken();
        btnDetailsDelete.setOnClickListener(this);
        btnBackToolbar.setOnClickListener(this);


    }

    private void getAccessToken() {
        accessToken = CommonData.getAppUser(getApplicationContext()).getData().get(0).getAccessToken();
    }

    private void setData() {
        edtDetailsName.setText(dataSet.getName());
        edtDetailsId.setText(dataSet.getId());
        edtDetailsPhone.setText(dataSet.getPhoneNo());
        edtDetailsEmail.setText(dataSet.getEmail());
        edtDetailsAddress.setText(dataSet.getAddress());
    }

    private void init() {
        initToolBar();
        edtDetailsName = (EditText) findViewById(R.id.et_details_customer_name);
        edtDetailsId = (EditText) findViewById(R.id.et_details_customer_id);
        edtDetailsPhone = (EditText) findViewById(R.id.et_details_customer_phone);
        edtDetailsAddress = (EditText) findViewById(R.id.et_details_customer_address);
        edtDetailsEmail = (EditText) findViewById(R.id.et_details_customer_email);
        btnDetailsDelete = (TextView) findViewById(R.id.tv_btn_details_delete);
    }

    private void initToolBar() {
        btnBackToolbar = (Button) findViewById(R.id.btn_toolBar_back);
        TextView tvLabelToolbar = (TextView) findViewById(R.id.tv_toolBar_activity_name);
        tvLabelToolbar.setText(getClass().getSimpleName());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_btn_details_delete:
                displayAlert();
                break;
            case R.id.btn_toolBar_back:
                onBackPressed();
                break;

        }
    }

    private void displayAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(
                DisplayDetailsCustomer.this);
        alert.setTitle("Alert!!");
        alert.setMessage("Are you sure to delete record");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCustomer();
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

    private void deleteCustomer() {
        RestClient.getApiService().deleteDriver("bearer " + accessToken, dataSet.getId(), new Callback<CustomersData>() {
            @Override
            public void success(CustomersData customersData, Response response) {
                CommonData.setCustomersData(customersData,getApplicationContext());
                Intent intent = new Intent();
                setResult(AppCompatActivity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v("TAG", error.getLocalizedMessage());
            }
        });
    }
}

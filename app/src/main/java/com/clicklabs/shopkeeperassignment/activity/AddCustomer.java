package com.clicklabs.shopkeeperassignment.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.clicklabs.shopkeeperassignment.R;
import com.clicklabs.shopkeeperassignment.models.registerCustomer.RegisterCustomer;
import com.clicklabs.shopkeeperassignment.retrofit.RestClient;
import com.clicklabs.shopkeeperassignment.utils.CommonData;
import com.clicklabs.shopkeeperassignment.utils.DisplayAlert;
import com.clicklabs.shopkeeperassignment.utils.ProgressDialogSwitch;
import com.clicklabs.shopkeeperassignment.utils.RetrofitErrorHandler;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddCustomer extends BaseActivity implements View.OnClickListener {

    private String accessToken;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private EditText edtAddCustomerName;
    private EditText edtAddCustomerPhone;
    private EditText edtAddCustomerEmail;
    private EditText edtAddCustomerAddress;
    private Button btnAddCustomerDone;
    private TextWatcher textWatcher;
    private Button btnBackToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        init();
        btnAddCustomerDone.setOnClickListener(this);
        btnAddCustomerDone.setEnabled(false);


    }

    private void init() {
        initToolbar();
        edtAddCustomerName = (EditText) findViewById(R.id.et_add_customer_username);
        edtAddCustomerPhone = (EditText) findViewById(R.id.et_add_customer_phone);
        edtAddCustomerAddress = (EditText) findViewById(R.id.et_add_customer_address);
        edtAddCustomerEmail = (EditText) findViewById(R.id.et_add_customer_email);
        btnAddCustomerDone = (Button) findViewById(R.id.btn_add_customer_done);
        initAnimationsAndWatchers();
        btnBackToolBar.setOnClickListener(this);

    }


    private void getData() {
        accessToken = CommonData.getAppUser(getApplicationContext()).getData().get(0).getAccessToken();
        customerName = edtAddCustomerName.getText().toString();
        customerPhone = edtAddCustomerPhone.getText().toString();
        customerEmail = edtAddCustomerEmail.getText().toString();
        customerAddress = edtAddCustomerAddress.getText().toString();
    }

    private void registerCustomer() {

        RestClient.getApiService().registerDriver("bearer " + accessToken, customerName, customerPhone, customerEmail, customerAddress, new Callback<RegisterCustomer>() {
            @Override
            public void success(RegisterCustomer registerCustomer, Response response) {
                clearEditTextFields();
                ProgressDialogSwitch.dismissProgressDialog();
                displayCustomerAddedAlert();


            }

            @Override
            public void failure(RetrofitError error) {
                ProgressDialogSwitch.dismissProgressDialog();
                RetrofitErrorHandler.checkCode(AddCustomer.this, error);
                Log.v("TAG", "NOSuccessForRegisteringCustomer" + error.getLocalizedMessage());
            }
        });

    }

    private void displayCustomerAddedAlert()  {
        DisplayAlert.showAlert(AddCustomer.this, "CustomerAddedSuccessfully", new DisplayAlert.customActionInterface() {
            @Override
            public void onButtonClick() {
                Intent intent = new Intent(AddCustomer.this, DisplayCustomers.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_customer_done:

                getData();
                if (isEmailValid(customerEmail)) {
                    ProgressDialogSwitch.showProgressDialog(AddCustomer.this, null);
                    registerCustomer();

                }
                break;
            case R.id.btn_toolBar_back:
                onBackPressed();
                break;
        }

    }


    private void clearEditTextFields() {
        edtAddCustomerName.setText(null);
        edtAddCustomerPhone.setText(null);
        edtAddCustomerEmail.setText(null);
        edtAddCustomerAddress.setText(null);
        edtAddCustomerName.clearFocus();
        edtAddCustomerAddress.clearFocus();
        edtAddCustomerPhone.clearFocus();
        edtAddCustomerEmail.clearFocus();
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(new View(this).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void LinksEditTextsToTextWatcher() {
        edtAddCustomerName.addTextChangedListener(textWatcher);
        edtAddCustomerPhone.addTextChangedListener(textWatcher);
        edtAddCustomerEmail.addTextChangedListener(textWatcher);
        edtAddCustomerAddress.addTextChangedListener(textWatcher);
    }

    private void textWatcherForSignUpButton() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateFields();
            }

            private void validateFields() {
                if (edtAddCustomerName.getText().length() > 0 && edtAddCustomerPhone.getText().length() > 0 && edtAddCustomerEmail.getText().length() > 0 && edtAddCustomerAddress.getText().length() > 0) {
                    btnAddCustomerDone.setEnabled(true);
                } else {
                    btnAddCustomerDone.setEnabled(false);
                }
            }
        };
    }

    private Boolean isEmailValid(String email) {
        boolean isValid = false;

        if (!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = true;
        } else {
            Drawable drawableOutlineWhiteBounded = getResources().getDrawable(R.drawable.ic_error_outline_white_24dp);
            assert drawableOutlineWhiteBounded != null;
            drawableOutlineWhiteBounded.setBounds(0, 0,
                    drawableOutlineWhiteBounded.getIntrinsicWidth(), drawableOutlineWhiteBounded.getIntrinsicHeight());

            edtAddCustomerEmail.setError("Invalid Email", drawableOutlineWhiteBounded);
        }
        return isValid;
    }

    private void initAnimationsAndWatchers() {
        textWatcherForSignUpButton();
        LinksEditTextsToTextWatcher();
    }

    private void initToolbar() {
        TextView tvToolbarActivityName = (TextView) findViewById(R.id.tv_toolBar_activity_name);
        tvToolbarActivityName.setText(getClass().getSimpleName());
        btnBackToolBar = (Button) findViewById(R.id.btn_toolBar_back);

    }

}
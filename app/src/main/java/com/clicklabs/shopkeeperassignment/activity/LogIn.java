package com.clicklabs.shopkeeperassignment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.clicklabs.shopkeeperassignment.R;
import com.clicklabs.shopkeeperassignment.models.ShopkeeperData.Shopkeeper;
import com.clicklabs.shopkeeperassignment.retrofit.RestClient;
import com.clicklabs.shopkeeperassignment.utils.CommonData;
import com.clicklabs.shopkeeperassignment.utils.ProgressDialogSwitch;
import com.clicklabs.shopkeeperassignment.utils.RetrofitErrorHandler;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LogIn extends BaseActivity implements View.OnClickListener {


    private TextWatcher textWatcher;
    private EditText edtLogInEmail;
    private EditText edtLogInPassword;
    private Button btnLogInDone;
    private Button btnToolBarBack;
    private Boolean isDrawablePasswordVisibilityOff = false;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();
        btnLogInDone.setEnabled(false);
        btnLogInDone.setOnClickListener(this);
        btnToolBarBack.setOnClickListener(this);
    }

    private void init() {
        initToolbar();
        edtLogInEmail = (EditText) findViewById(R.id.et_sign_in_email);
        edtLogInPassword = (EditText) findViewById(R.id.et_sign_in_password);
        btnLogInDone = (Button) findViewById(R.id.btn_sign_in_done);
        initWatchers();
    }

    private void initToolbar() {
        TextView tvActivityName = (TextView) findViewById(R.id.tv_toolBar_activity_name);
        btnToolBarBack = (Button) findViewById(R.id.btn_toolBar_back);
        tvActivityName.setText(this.getClass().getSimpleName());

    }

    private void initWatchers() {
        textWatcherForSignUpButton();
        LinksEditTextsToTextWatcher();
        handlesPasswordDrawableRightButton();
    }

    private void handlesPasswordDrawableRightButton() {
        edtLogInPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtLogInPassword.getRight() - edtLogInPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        int imgDrawableLeft = R.drawable.ic_lock_outline_white_24dp;
                        if (isDrawablePasswordVisibilityOff) {
                            edtLogInPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                            edtLogInPassword.setSelection(edtLogInPassword.getText().length());
                            edtLogInPassword.setCompoundDrawablesWithIntrinsicBounds(imgDrawableLeft, 0, R.drawable.ic_visibility_off_white_24dp, 0);
                            isDrawablePasswordVisibilityOff = false;
                        } else {
                            edtLogInPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
                            edtLogInPassword.setSelection(edtLogInPassword.getText().length());
                            edtLogInPassword.setCompoundDrawablesWithIntrinsicBounds(imgDrawableLeft, 0, R.drawable.ic_visibility_white_24dp, 0);
                            isDrawablePasswordVisibilityOff = true;

                        }

                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void LinksEditTextsToTextWatcher() {
        edtLogInEmail.addTextChangedListener(textWatcher);
        edtLogInPassword.addTextChangedListener(textWatcher);
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
                if (edtLogInEmail.getText().length() > 0 && edtLogInPassword.getText().length() > 0) {
                    btnLogInDone.setEnabled(true);
                } else {
                    btnLogInDone.setEnabled(false);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in_done:
                ProgressDialogSwitch.showProgressDialog(LogIn.this, null);
                hideKeyBoard();
                getData();
                callBackForLogIn();
                break;
            case R.id.btn_toolBar_back:
                onBackPressed();

        }
    }

    private void hideKeyBoard() {
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(new View(this).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void startDisplayCustomersActivity() {
        Intent intent = new Intent(LogIn.this, DisplayCustomers.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    private void getData() {
        email = edtLogInEmail.getText().toString();
        password = edtLogInPassword.getText().toString();
    }

    private void callBackForLogIn() {

        RestClient.getApiService().login(email, password, new Callback<Shopkeeper>() {
            @Override
            public void success(Shopkeeper shopkeeper, Response response) {
                CommonData.saveAppUser(shopkeeper, getApplicationContext());
                Log.v("TAG", "SuccessForLoggingIn");
                ProgressDialogSwitch.dismissProgressDialog();
                startDisplayCustomersActivity();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.v("TAG", "NoSuccessForLoggingIn");
                Log.v("TAG", error.getLocalizedMessage());
                ProgressDialogSwitch.dismissProgressDialog();
                RetrofitErrorHandler.checkCode(LogIn.this, error);
            }
        });
    }

}

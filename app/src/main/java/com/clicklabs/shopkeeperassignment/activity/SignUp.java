package com.clicklabs.shopkeeperassignment.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clicklabs.shopkeeperassignment.R;
import com.clicklabs.shopkeeperassignment.models.ShopkeeperData.Shopkeeper;
import com.clicklabs.shopkeeperassignment.retrofit.RestClient;
import com.clicklabs.shopkeeperassignment.utils.CommonData;
import com.clicklabs.shopkeeperassignment.utils.DisplayAlert;
import com.clicklabs.shopkeeperassignment.utils.ProgressDialogSwitch;
import com.clicklabs.shopkeeperassignment.utils.RetrofitErrorHandler;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUp extends BaseActivity implements View.OnClickListener {

    private LinearLayout linearLayout;
    private EditText edtSignUpSupplierName;
    private EditText edtSignUpSupplierPassword;
    private EditText edtSignUpSupplierEmail;
    private EditText edtSignUpSupplierPhone;
    private Button btnSignUpDone;
    private Boolean isDrawablePasswordVisibilityOff = false;
    private TextWatcher textWatcher;
    private String supplierName;
    private String supplierPhone;
    private String supplierEmail;
    private String supplierPassword;
    private TextView tvBtnLogIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        btnSignUpDone.setOnClickListener(this);
        tvBtnLogIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up_done:
                getData();
                hideKeyboard();
                if (isValidate())
                    registerSupplierAndStartNextActivity();

                break;
            case R.id.tv_btn_sign_up_log_in:
                startLogInActivity();
                break;
        }
    }

    private boolean isValidate() {
        if (!isEmailValid(supplierEmail)) {
            DisplayAlert.showAlert(SignUp.this, "Email not valid");
            return false;
        }
        if (supplierPassword.length() < 6) {
            DisplayAlert.showAlert(SignUp.this, "Password must be at least 6 digit long");
            return false;
        }

        return true;
    }

    private void hideKeyboard() {
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(new View(this).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void startLogInActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setExitTransition(new Explode());
            Intent intent = new Intent(SignUp.this, LogIn.class);
        //    startActivity(intent,ActivityOptions.makeScaleUpAnimation(linearLayout,0,1050,0,1050).toBundle());
            startActivity(intent, ActivityOptions
                    .makeSceneTransitionAnimation(this).toBundle());

        }
        else
        {
            Intent intent = new Intent(SignUp.this, LogIn.class);
            startActivity(intent);
            //merge

        }

    }

    private void registerSupplierAndStartNextActivity() {

        ProgressDialogSwitch.showProgressDialog(SignUp.this, null);
        RestClient.getApiService().createSupplier(supplierName, supplierPhone, supplierEmail, supplierPassword, new Callback<Shopkeeper>() {
            @Override
            public void success(Shopkeeper shopkeeper, Response response) {

                CommonData.saveAppUser(shopkeeper, getApplicationContext());
                ProgressDialogSwitch.dismissProgressDialog();
                startCustomersDisplayActivity();

            }

            @Override
            public void failure(RetrofitError error) {
                ProgressDialogSwitch.dismissProgressDialog();
                RetrofitErrorHandler.checkCode(SignUp.this, error);
                Log.v("TAG", error.getLocalizedMessage());

            }
        });
    }

    private void getData() {
        supplierName = edtSignUpSupplierName.getText().toString();
        supplierPhone = edtSignUpSupplierPhone.getText().toString();
        supplierEmail = edtSignUpSupplierEmail.getText().toString();
        supplierPassword = edtSignUpSupplierPassword.getText().toString();
    }

    private void init() {
        initToolbar();
        initLayoutViews();
        initAnimationsAndWatchers();
        btnSignUpDone.setEnabled(false);

    }

    private void initAnimationsAndWatchers() {
        animationForLayoutBackground();
        textWatcherForSignUpButton();
        LinksEditTextsToTextWatcher();
        handlesPasswordDrawableRightButton();
    }

    private void initLayoutViews() {

        linearLayout = (LinearLayout) findViewById(R.id.layout_sign_up);
        tvBtnLogIn = (TextView) findViewById(R.id.tv_btn_sign_up_log_in);
        edtSignUpSupplierPassword = (EditText) findViewById(R.id.et_sign_up_password);
        edtSignUpSupplierName = (EditText) findViewById(R.id.et_sign_up_username);
        edtSignUpSupplierEmail = (EditText) findViewById(R.id.et_sign_up_email);
        edtSignUpSupplierPhone = (EditText) findViewById(R.id.et_sign_up_phone);
        btnSignUpDone = (Button) findViewById(R.id.btn_sign_up_done);


    }


    private void animationForLayoutBackground() {
        ColorDrawable[] color = {new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.colorBackgroundSignUpInitial)), new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.colorBackgroundSignUp))};
        TransitionDrawable trans = new TransitionDrawable(color);
        linearLayout.setBackgroundDrawable(trans);
        for (int i = 0; i <= 10; i++) {

            if (i % 2 == 0)
                trans.startTransition(10000);
            else
                trans.reverseTransition(10000);

        }
    }

    private void LinksEditTextsToTextWatcher() {
        edtSignUpSupplierEmail.addTextChangedListener(textWatcher);
        edtSignUpSupplierPhone.addTextChangedListener(textWatcher);
        edtSignUpSupplierPassword.addTextChangedListener(textWatcher);
        edtSignUpSupplierName.addTextChangedListener(textWatcher);
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
                if (edtSignUpSupplierPhone.getText().length() > 0 && edtSignUpSupplierName.getText().length() > 0 && edtSignUpSupplierEmail.getText().length() > 0 && edtSignUpSupplierPassword.getText().length() > 0) {
                    btnSignUpDone.setEnabled(true);
                } else {
                    btnSignUpDone.setEnabled(false);
                }
            }
        };
    }

    private void initToolbar() {
        Button btnBackToolbar = (Button) findViewById(R.id.btn_toolBar_back);
        btnBackToolbar.setVisibility(View.GONE);
        TextView tvLabelToolbar = (TextView) findViewById(R.id.tv_toolBar_activity_name);
        tvLabelToolbar.setText(getClass().getSimpleName());
    }

    private void handlesPasswordDrawableRightButton() {
        edtSignUpSupplierPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtSignUpSupplierPassword.getRight() - edtSignUpSupplierPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        int imgDrawableLeft = R.drawable.ic_lock_outline_white_24dp;
                        if (isDrawablePasswordVisibilityOff) {
                            edtSignUpSupplierPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                            edtSignUpSupplierPassword.setSelection(edtSignUpSupplierPassword.getText().length());
                            edtSignUpSupplierPassword.setCompoundDrawablesWithIntrinsicBounds(imgDrawableLeft, 0, R.drawable.ic_visibility_off_white_24dp, 0);
                            isDrawablePasswordVisibilityOff = false;
                        } else {
                            edtSignUpSupplierPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
                            edtSignUpSupplierPassword.setSelection(edtSignUpSupplierPassword.getText().length());
                            edtSignUpSupplierPassword.setCompoundDrawablesWithIntrinsicBounds(imgDrawableLeft, 0, R.drawable.ic_visibility_white_24dp, 0);
                            isDrawablePasswordVisibilityOff = true;

                        }

                        return true;
                    }
                }
                return false;
            }
        });

    }

    private Boolean isEmailValid(String email) {
        boolean isValid = false;


        if (!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = true;
        } else {

            Drawable drawableOutlineWhiteBounded = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_error_outline_white_24dp);
            assert drawableOutlineWhiteBounded != null;
            drawableOutlineWhiteBounded.setBounds(0, 0,
                    drawableOutlineWhiteBounded.getIntrinsicWidth(), drawableOutlineWhiteBounded.getIntrinsicHeight());

            edtSignUpSupplierEmail.setError("Invalid Email", drawableOutlineWhiteBounded);
        }
        return isValid;
    }


    private void startCustomersDisplayActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setExitTransition(new Explode());
            Intent intent = new Intent(SignUp.this, DisplayCustomers.class);
            startActivity(intent,ActivityOptions.makeScaleUpAnimation(linearLayout,0,0,50,50).toBundle());
            startActivity(intent, ActivityOptions
                    .makeSceneTransitionAnimation(this).toBundle());
            finish();
        }
        else
        {
            Intent intent = new Intent(SignUp.this, DisplayCustomers.class);
            startActivity(intent);
            finish();
        }

    }
}

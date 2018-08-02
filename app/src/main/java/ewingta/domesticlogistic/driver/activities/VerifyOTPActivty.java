package ewingta.domesticlogistic.driver.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

import ewingta.domesticlogistic.driver.MainActivity;
import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.listeners.SmsListener;
import ewingta.domesticlogistic.driver.models.City;
import ewingta.domesticlogistic.driver.models.VerifyResponse;
import ewingta.domesticlogistic.driver.receivers.SmsReceiver;
import ewingta.domesticlogistic.driver.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.driver.reterofit.RetrofitService;
import ewingta.domesticlogistic.driver.utils.PreferenceUtil;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saxxis25 on 7/30/2018.
 */

public class VerifyOTPActivty extends BaseActivity implements EasyPermissions.PermissionCallbacks, SmsListener, View.OnClickListener {

    private static final int RC_READ_SMS = 4875;
    public static String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static String OTP = "OTP";
    public static String USER_ID = "USER_ID";
    public String ordernumber;
    private String userId;
    public String mobileNumber;
    private EditText et_otp;
    private AppCompatButton btn_submit;
    private ProgressBar progress_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_otp);

        try {
            Toolbar toolbar_otp = findViewById(R.id.verifytoolbar_otp);
            toolbar_otp.setTitle(R.string.otp_verification);
            setSupportActionBar(toolbar_otp);

            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null)
            {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
            }

            userId = getIntent().getStringExtra(USER_ID);


            showInfoToast(R.string.otp_sent_to_registered_number);

            et_otp = findViewById(R.id.verifyet_otp);

            btn_submit = findViewById(R.id.verifybtn_submit);
            btn_submit.setOnClickListener(this);
            progress_submit = findViewById(R.id.verifyprogress_submit);

            getMessage();
        }
        catch (Exception e)
        {
            showErrorToast(R.string.error_message);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            getMessage();
        }
    }

    @AfterPermissionGranted(RC_READ_SMS)
    private void getMessage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.RECEIVE_SMS};
            if (EasyPermissions.hasPermissions(this, perms)) {
                SmsReceiver.bindListener(this);
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.read_sms_rationale), RC_READ_SMS, perms);
            }
        } else {
            SmsReceiver.bindListener(this);
        }
    }

    @Override
    public void messageReceived(String messageText) {
        if (messageText.contains(ordernumber)) {
            et_otp.setText(ordernumber);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verifybtn_submit:
                String otp = et_otp.getText().toString().trim();

                if (otp.isEmpty()) {
                    showErrorToast(R.string.enter_valid_otp);
                } else {
                    progress_submit.setVisibility(View.VISIBLE);
                    btn_submit.setVisibility(View.GONE);

                    RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
                    service.otpverifyActivation(userId, this.ordernumber).enqueue(new Callback<VerifyResponse>() {
                        @Override
                        public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                VerifyResponse Vr = response.body();

                                if (Vr.getStatus().equals("ok")) {
                                    PreferenceUtil.setVerifyUserDetails(VerifyOTPActivty.this, Vr);

                                    City city = PreferenceUtil.getCity(VerifyOTPActivty.this);

                                    if (city == null) {
                                        Intent intent = new Intent(VerifyOTPActivty.this, CityActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(VerifyOTPActivty.this, MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                        finish();
                                    }
                                } else {
                                    progress_submit.setVisibility(View.GONE);
                                    btn_submit.setVisibility(View.VISIBLE);

                                    /*if (Vr.getError_description() != null) {
                                        showErrorToast(lr.getError_description());
                                    } else if (Vr.getMessage() != null) {
                                        showErrorToast(lr.getMessage());
                                    } else {
                                        showErrorToast(R.string.error_message);
                                    }*/
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<VerifyResponse> call, Throwable t) {
                            progress_submit.setVisibility(View.GONE);
                            btn_submit.setVisibility(View.VISIBLE);
                            showErrorToast(R.string.error_message);
                        }
                    });
                }

                break;
        }
    }
}


package ewingta.domesticlogistic.driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.RegisterResponse;
import ewingta.domesticlogistic.driver.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.driver.reterofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_email, et_name, et_mobile_number, et_password;
    private ProgressBar progress_register;
    private AppCompatButton btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_mobile_number = findViewById(R.id.et_mobile_number);
        et_password = findViewById(R.id.et_password);
        progress_register = findViewById(R.id.progress_register);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String name = et_name.getText().toString().trim();
                String mobileNumber = et_mobile_number.getText().toString().trim();

                if (name.isEmpty()) {
                    showErrorToast(R.string.enter_valid_name);
                } else if (mobileNumber.isEmpty() || mobileNumber.length() < 10) {
                    showErrorToast(R.string.valid_mobile_number);
                } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showErrorToast(R.string.enter_valid_email);
                } else if (password.isEmpty()) {
                    showErrorToast(R.string.enter_valid_password);
                } else {
                    progress_register.setVisibility(View.VISIBLE);
                    btn_register.setVisibility(View.GONE);

                    RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
                    service.registerUser(name, email, mobileNumber, password).enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                            if (response.isSuccessful() && response.body() != null) {
                                RegisterResponse rr = response.body();

                                if (rr.getStatus().equals("ok")) {
                                    Intent intent = new Intent(RegistrationActivity.this, OTPActivity.class);
                                    intent.putExtra(OTPActivity.OTP, rr.getOtp());
                                    intent.putExtra(OTPActivity.USER_ID, rr.getUserid());
                                    intent.putExtra(OTPActivity.MOBILE_NUMBER, rr.getMobile());
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                    finish();
                                } else {
                                    progress_register.setVisibility(View.GONE);
                                    btn_register.setVisibility(View.VISIBLE);

                                    if (rr.getError_description() != null) {
                                        showErrorToast(rr.getError_description());
                                    } else if (rr.getMessage() != null) {
                                        showErrorToast(rr.getMessage());
                                    } else {
                                        showErrorToast(R.string.error_message);
                                    }
                                }

                            } else {
                                showErrorToast(R.string.error_message);
                                progress_register.setVisibility(View.GONE);
                                btn_register.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            showErrorToast(R.string.error_message);
                            progress_register.setVisibility(View.GONE);
                            btn_register.setVisibility(View.VISIBLE);
                        }
                    });
                }
                break;
        }
    }
}

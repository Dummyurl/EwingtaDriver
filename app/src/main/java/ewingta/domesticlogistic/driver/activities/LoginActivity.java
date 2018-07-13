package ewingta.domesticlogistic.driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import ewingta.domesticlogistic.driver.MainActivity;
import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.Area;
import ewingta.domesticlogistic.driver.models.City;
import ewingta.domesticlogistic.driver.models.LoginResponse;
import ewingta.domesticlogistic.driver.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.driver.reterofit.RetrofitService;
import ewingta.domesticlogistic.driver.utils.PreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_phone_number, et_password;
    private Button btn_login;
    private ProgressBar progress_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_phone_number = findViewById(R.id.et_mobile_number);
        et_password = findViewById(R.id.et_password);

        findViewById(R.id.tv_register).setOnClickListener(this);
        progress_login = findViewById(R.id.progress_login);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(this, RegistrationActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.btn_login:
                String phoneNumber = et_phone_number.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (phoneNumber.isEmpty() || phoneNumber.length() < 10) {
                    showErrorToast(R.string.valid_mobile_number);
                } else if (password.isEmpty()) {
                    showErrorToast(R.string.valid_password);
                } else {
                    progress_login.setVisibility(View.VISIBLE);
                    btn_login.setVisibility(View.GONE);

                    RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
                    service.loginUser(phoneNumber, password).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                LoginResponse lr = response.body();

                                if (lr != null && lr.getStatus().equals("ok")) {
                                    PreferenceUtil.setUserDetails(LoginActivity.this, lr);

                                    City city = PreferenceUtil.getCity(LoginActivity.this);

                                    if (city == null) {
                                        Intent intent = new Intent(LoginActivity.this, CityActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                        finish();
                                    }
                                } else {
                                    progress_login.setVisibility(View.GONE);
                                    btn_login.setVisibility(View.VISIBLE);

                                    if (lr.getError_description() != null) {
                                        showErrorToast(lr.getError_description());
                                    } else if (lr.getMessage() != null) {
                                        showErrorToast(lr.getMessage());
                                    } else {
                                        showErrorToast(R.string.error_message);
                                    }
                                }
                            } else {
                                showErrorToast(R.string.error_message);
                                progress_login.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            showErrorToast(R.string.error_message);
                            progress_login.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                        }
                    });
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}

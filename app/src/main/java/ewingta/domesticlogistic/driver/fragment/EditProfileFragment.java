package ewingta.domesticlogistic.driver.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.LoginResponse;
import ewingta.domesticlogistic.driver.models.RegisterResponse;
import ewingta.domesticlogistic.driver.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.driver.reterofit.RetrofitService;
import ewingta.domesticlogistic.driver.utils.PreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {

    private EditText et_password, et_retype_password;
    private AppCompatButton btn_submit;
    private ProgressBar progress_submit;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);

            LoginResponse lr = PreferenceUtil.getUserDetails(getContext());

            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_mobile_number = view.findViewById(R.id.tv_mobile_number);
            TextView tv_email = view.findViewById(R.id.tv_email);
            et_password = view.findViewById(R.id.et_password);
            et_retype_password = view.findViewById(R.id.et_retype_password);

            tv_name.setText(lr.getName());
            tv_mobile_number.setText(lr.getMobile());
            tv_email.setText(lr.getEmail());

            progress_submit = view.findViewById(R.id.progress_submit);
            btn_submit = view.findViewById(R.id.btn_submit);
            btn_submit.setOnClickListener(this);
        } catch (Exception e) {
            showErrorToast(R.string.error_message);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_submit:
                String newPassword = et_password.getText().toString().trim();
                String retypePassword = et_retype_password.getText().toString().trim();

                if (newPassword.isEmpty()) {
                    showErrorToast(R.string.please_enter_new_password);
                } else if (newPassword.length() < 6) {
                    showErrorToast(R.string.new_password_is_short);
                } else if (retypePassword.isEmpty()) {
                    showErrorToast(R.string.please_enter_confirm_password);
                } else if (retypePassword.length() < 6) {
                    showErrorToast(R.string.confirm_password_is_short);
                } else if (!newPassword.equals(retypePassword)) {
                    showErrorToast(R.string.password_are_not_equal);
                } else {
                    progress_submit.setVisibility(View.VISIBLE);
                    btn_submit.setVisibility(View.GONE);

                    LoginResponse lr = PreferenceUtil.getUserDetails(getContext());

                    RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
                    service.updatePassword(lr.getUserid(), lr.getName(), lr.getEmail(), lr.getMobile(), newPassword).enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                RegisterResponse lr = response.body();

                                if (lr.getStatus().equals("ok")) {
                                    et_password.setText("");
                                    et_retype_password.setText("");
                                    showSuccessToast(R.string.password_updated);
                                } else {
                                    if (lr.getError_description() != null) {
                                        showErrorToast(lr.getError_description());
                                    } else if (lr.getMessage() != null) {
                                        showErrorToast(lr.getMessage());
                                    } else {
                                        showErrorToast(R.string.error_message);
                                    }
                                }
                            }

                            progress_submit.setVisibility(View.GONE);
                            btn_submit.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
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

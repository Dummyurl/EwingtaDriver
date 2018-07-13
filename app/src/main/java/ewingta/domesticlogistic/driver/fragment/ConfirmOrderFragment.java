package ewingta.domesticlogistic.driver.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ewingta.domesticlogistic.driver.MainActivity;
import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.PriceResponse;
import ewingta.domesticlogistic.driver.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.driver.reterofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderFragment extends BaseFragment implements View.OnClickListener {

    private static final String ORDER_ID = "ORDER_ID";
    private RelativeLayout rl_progress;
    private String orderId;
    private AppCompatButton btn_submit;
    private ProgressBar progress_submit;

    public static ConfirmOrderFragment newInstance(String orderId) {
        ConfirmOrderFragment confirmOrderFragment = new ConfirmOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, orderId);
        confirmOrderFragment.setArguments(bundle);
        return confirmOrderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            orderId = getArguments().getString(ORDER_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);

            RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
            rl_progress = view.findViewById(R.id.rl_progress);
            rl_progress.setVisibility(View.VISIBLE);

            btn_submit = view.findViewById(R.id.btn_submit);
            btn_submit.setOnClickListener(this);
            progress_submit = view.findViewById(R.id.progress_submit);

            final TextView tv_price = view.findViewById(R.id.tv_price);

            service.getPrice(orderId).enqueue(new Callback<PriceResponse>() {
                @Override
                public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        PriceResponse priceResponse = response.body();

                        if (priceResponse.getStatus().equals("ok")) {
                            tv_price.setText(priceResponse.getPrice());
                        } else {
                            showErrorToast(R.string.error_message);
                        }

                    } else {
                        showErrorToast(R.string.error_message);
                    }

                    rl_progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<PriceResponse> call, Throwable t) {
                    rl_progress.setVisibility(View.GONE);
                    showErrorToast(R.string.error_message);
                }
            });

        } catch (Exception e) {
            showErrorToast(R.string.error_message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                Activity activity = getActivity();

                if (activity != null && !activity.isFinishing()) {
                    MainActivity mainActivity = (MainActivity) activity;
                    mainActivity.showOrders();
                }
                break;
        }
    }
}

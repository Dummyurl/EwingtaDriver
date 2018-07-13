package ewingta.domesticlogistic.driver.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.adapter.AddressAdapter;
import ewingta.domesticlogistic.driver.models.LoginResponse;
import ewingta.domesticlogistic.driver.models.MyAddressResponse;
import ewingta.domesticlogistic.driver.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.driver.reterofit.RetrofitService;
import ewingta.domesticlogistic.driver.utils.PreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressesFragment extends BaseFragment {

    private TextView tv_empty;
    private RelativeLayout rl_progress;
    private AddressAdapter addressAdapter;
    private RetrofitService service;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addresses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);

            service = RetrofitInstance.createService(RetrofitService.class);
            tv_empty = view.findViewById(R.id.tv_empty);
            rl_progress = view.findViewById(R.id.rl_progress);

            RecyclerView rl_orders = view.findViewById(R.id.rv_addresses);
            rl_orders.setLayoutManager(new LinearLayoutManager(getContext()));

            addressAdapter = new AddressAdapter();
            rl_orders.setAdapter(addressAdapter);

            LoginResponse lr = PreferenceUtil.getUserDetails(getContext());

            service.listAddress(lr.getUserid()).enqueue(new Callback<MyAddressResponse>() {
                @Override
                public void onResponse(Call<MyAddressResponse> call, Response<MyAddressResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        MyAddressResponse or = response.body();

                        addressAdapter.addItems(or.getMessage());

                        if (or.getMessage().isEmpty()) {
                            tv_empty.setVisibility(View.VISIBLE);
                        } else {
                            tv_empty.setVisibility(View.GONE);
                        }
                        rl_progress.setVisibility(View.GONE);
                    } else {
                        tv_empty.setVisibility(View.VISIBLE);
                        rl_progress.setVisibility(View.GONE);
                        showErrorToast(R.string.error_message);
                    }
                }

                @Override
                public void onFailure(Call<MyAddressResponse> call, Throwable t) {
                    tv_empty.setVisibility(View.VISIBLE);
                    rl_progress.setVisibility(View.GONE);
                    showErrorToast(R.string.error_message);
                }
            });
        } catch (Exception e) {
            showErrorToast(R.string.error_message);
        }

    }
}

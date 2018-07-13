package ewingta.domesticlogistic.driver.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ewingta.domesticlogistic.driver.MainActivity;
import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.adapter.AreaAdapter;
import ewingta.domesticlogistic.driver.adapter.CategoryAdapter;
import ewingta.domesticlogistic.driver.adapter.CityAdapter;
import ewingta.domesticlogistic.driver.adapter.SelectAddressAdapter;
import ewingta.domesticlogistic.driver.adapter.ServiceAdapter;
import ewingta.domesticlogistic.driver.listeners.DateTimeSetListener;
import ewingta.domesticlogistic.driver.models.Address;
import ewingta.domesticlogistic.driver.models.AddressResponse;
import ewingta.domesticlogistic.driver.models.Area;
import ewingta.domesticlogistic.driver.models.AreaResponse;
import ewingta.domesticlogistic.driver.models.Category;
import ewingta.domesticlogistic.driver.models.CategoryResponse;
import ewingta.domesticlogistic.driver.models.City;
import ewingta.domesticlogistic.driver.models.CityResponse;
import ewingta.domesticlogistic.driver.models.LoginResponse;
import ewingta.domesticlogistic.driver.models.RegisterResponse;
import ewingta.domesticlogistic.driver.models.Service;
import ewingta.domesticlogistic.driver.models.ServiceResponse;
import ewingta.domesticlogistic.driver.models.Time;
import ewingta.domesticlogistic.driver.models.TimeResponse;
import ewingta.domesticlogistic.driver.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.driver.reterofit.RetrofitService;
import ewingta.domesticlogistic.driver.utils.DateTimeUtil;
import ewingta.domesticlogistic.driver.utils.PreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrderFragment extends BaseFragment implements View.OnClickListener, DateTimeSetListener {

    private RetrofitService service;
    private TextView tv_date_time;
    private RelativeLayout rl_progress;
    private Spinner spinner_delivery_item, spinner_delivery_type, spinner_area;
    private List<Time> times;
    private Address pickupAddress, dropAddress;
    private TextView tv_pickup_location, tv_drop_location;
    private EditText et_pickup_name, et_pickup_phone_number, et_drop_name, et_drop_phone_number;
    private AppCompatButton btn_submit;
    private ProgressBar progress_submit;
    private String date;
    private Time time;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_add_orders, container, false);

            try {
                service = RetrofitInstance.createService(RetrofitService.class);

                rl_progress = view.findViewById(R.id.rl_progress);
                tv_date_time = view.findViewById(R.id.tv_date_time);

                tv_pickup_location = view.findViewById(R.id.tv_pickup_location);
                et_pickup_name = view.findViewById(R.id.et_pickup_name);
                et_pickup_phone_number = view.findViewById(R.id.et_pickup_phone_number);

                tv_drop_location = view.findViewById(R.id.tv_drop_location);
                et_drop_name = view.findViewById(R.id.et_drop_name);
                et_drop_phone_number = view.findViewById(R.id.et_drop_phone_number);

                spinner_delivery_item = view.findViewById(R.id.spinner_delivery_item);
                spinner_delivery_type = view.findViewById(R.id.spinner_delivery_type);
                spinner_area = view.findViewById(R.id.spinner_area);

                btn_submit = view.findViewById(R.id.btn_submit);
                progress_submit = view.findViewById(R.id.progress_submit);

                tv_date_time.setOnClickListener(this);

                service.getServices().enqueue(new Callback<ServiceResponse>() {
                    @Override
                    public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            ServiceResponse sr = response.body();

                            if (sr.getStatus().equals("ok")) {
                                ServiceAdapter serviceAdapter = new ServiceAdapter(getContext(), sr.getServicelist());
                                spinner_delivery_item.setAdapter(serviceAdapter);
                            }
                        }

                        rl_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ServiceResponse> call, Throwable t) {
                        rl_progress.setVisibility(View.GONE);
                        showErrorToast(R.string.error_message);
                    }
                });

                service.getCategories().enqueue(new Callback<CategoryResponse>() {
                    @Override
                    public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            CategoryResponse cr = response.body();

                            if (cr.getStatus().equals("ok")) {
                                CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), cr.getCategorylist());
                                spinner_delivery_type.setAdapter(categoryAdapter);
                            }
                        }

                        rl_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<CategoryResponse> call, Throwable t) {
                        rl_progress.setVisibility(View.GONE);
                        showErrorToast(R.string.error_message);
                    }
                });

                City city = PreferenceUtil.getCity(getContext());

                service.getAreas(city.getId()).enqueue(new Callback<AreaResponse>() {
                    @Override
                    public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            AreaResponse sr = response.body();

                            if (sr.getStatus().equals("ok")) {
                                AreaAdapter cityAdapter = new AreaAdapter(getContext(), sr.getLocations());
                                spinner_area.setAdapter(cityAdapter);
                            }
                        }

                        rl_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<AreaResponse> call, Throwable t) {
                        rl_progress.setVisibility(View.GONE);
                        showErrorToast(R.string.error_message);
                    }
                });

                if (times == null) {
                    service.getTimes().enqueue(new Callback<TimeResponse>() {
                        @Override
                        public void onResponse(Call<TimeResponse> call, Response<TimeResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                TimeResponse cr = response.body();

                                if (cr.getStatus().equals("ok")) {
                                    times = cr.getTimeslist();
                                }
                            }

                            rl_progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<TimeResponse> call, Throwable t) {
                            rl_progress.setVisibility(View.GONE);
                            showErrorToast(R.string.error_message);
                        }
                    });
                }

                tv_pickup_location.setOnClickListener(this);
                tv_drop_location.setOnClickListener(this);
                btn_submit.setOnClickListener(this);
                view.findViewById(R.id.iv_drop).setOnClickListener(this);
                view.findViewById(R.id.iv_pickup).setOnClickListener(this);

            } catch (Exception e) {
                showErrorToast(R.string.error_message);
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Object deliveryItem = null;
                Object deliveryType = null;
                Object deliveryArea = null;

                if (spinner_delivery_item.getSelectedItemPosition() > 0) {
                    deliveryItem = spinner_delivery_item.getSelectedItem();
                }

                if (spinner_delivery_type.getSelectedItemPosition() > 0) {
                    deliveryType = spinner_delivery_type.getSelectedItem();
                }

                if (spinner_area.getSelectedItemPosition() > 0) {
                    deliveryArea = spinner_area.getSelectedItem();
                }

                String pickUpName = et_pickup_name.getText().toString().trim();
                String pickupMobileNumber = et_pickup_phone_number.getText().toString().trim();
                String dropName = et_drop_name.getText().toString().trim();
                String dropMobileNumber = et_drop_phone_number.getText().toString().trim();


                if (deliveryItem == null) {
                    showErrorToast(R.string.select_what_do_you_want_to_deliver);
                } else if (deliveryType == null) {
                    showErrorToast(R.string.select_delivery_type);
                } else if (deliveryArea == null) {
                    showErrorToast(R.string.select_delivery_area);
                } else if (date == null || time == null) {
                    showErrorToast(R.string.select_date_time);
                } else if (pickupAddress == null) {
                    showErrorToast(R.string.pick_up_location_empty);
                } else if (pickUpName.isEmpty()) {
                    showErrorToast(R.string.pick_up_name_empty);
                } else if (pickupMobileNumber.isEmpty() || pickupMobileNumber.length() < 10) {
                    showErrorToast(R.string.enter_valid_pick_up_mobile_number);
                } else if (dropAddress == null) {
                    showErrorToast(R.string.drop_location_empty);
                } else if (dropName.isEmpty()) {
                    showErrorToast(R.string.drop_name_empty);
                } else if (dropMobileNumber.isEmpty() || dropMobileNumber.length() < 10) {
                    showErrorToast(R.string.enter_valid_drop_mobile_number);
                } else {
                    LoginResponse lr = PreferenceUtil.getUserDetails(getContext());
                    final Service deliveryService = (Service) deliveryItem;
                    Category type = (Category) deliveryType;
                    City city = PreferenceUtil.getCity(getContext());
                    Area area = (Area) deliveryArea;

                    progress_submit.setVisibility(View.VISIBLE);
                    btn_submit.setVisibility(View.GONE);

                    service.addOrder(lr.getUserid(), city.getId(), area.getId(), dropName, dropMobileNumber, dropAddress.getId(),
                            pickUpName, pickupMobileNumber, pickupAddress.getId(), time.getId(), type.getId()
                            , deliveryService.getId(), date).enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            if (response.body() != null && response.isSuccessful()) {
                                RegisterResponse rr = response.body();

                                if (rr.getStatus().equals("ok")) {
                                    progress_submit.setVisibility(View.GONE);
                                    btn_submit.setVisibility(View.VISIBLE);
                                    tv_drop_location.setText("");
                                    et_drop_name.setText("");
                                    et_drop_phone_number.setText("");

                                    tv_pickup_location.setText("");
                                    et_pickup_name.setText("");
                                    et_pickup_phone_number.setText("");

                                    spinner_delivery_item.setSelection(0);
                                    spinner_delivery_type.setSelection(0);
                                    spinner_area.setSelection(0);
                                    tv_date_time.setText("");

                                    Activity activity = getActivity();

                                    if (activity != null && !activity.isFinishing()) {
                                        MainActivity mainActivity = (MainActivity) activity;
                                        mainActivity.showConfirmOrder(rr.getOrdernumber());
                                    }
                                } else {
                                    progress_submit.setVisibility(View.GONE);
                                    btn_submit.setVisibility(View.VISIBLE);

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
                                progress_submit.setVisibility(View.GONE);
                                btn_submit.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            showErrorToast(R.string.error_message);
                            progress_submit.setVisibility(View.GONE);
                            btn_submit.setVisibility(View.VISIBLE);
                        }
                    });
                }

                break;
            case R.id.tv_date_time:
                new DateTimeUtil().datePicker(getContext(), this, times);
                break;
            case R.id.tv_pickup_location:
                getPickupLocation();
                break;
            case R.id.tv_drop_location:
                getDropLocation();
                break;
            case R.id.iv_drop:
            case R.id.iv_pickup:
                Activity activity = getActivity();

                if (activity != null && !activity.isFinishing()) {
                    MainActivity mainActivity = (MainActivity) activity;
                    mainActivity.actionAddAddress();
                }
                break;
        }
    }

    private void getDropLocation() {
        Context context = getContext();

        if (context != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_address, null);

            final TextView tv_empty = dialogView.findViewById(R.id.tv_empty);
            final RelativeLayout rl_progress = dialogView.findViewById(R.id.rl_progress);
            rl_progress.setVisibility(View.VISIBLE);

            RecyclerView rl_orders = dialogView.findViewById(R.id.rv_address);
            rl_orders.setLayoutManager(new LinearLayoutManager(getContext()));

            final SelectAddressAdapter addressAdapter = new SelectAddressAdapter(dropAddress);

            rl_orders.setAdapter(addressAdapter);

            AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
            builder.setView(dialogView);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dropAddress = addressAdapter.getSelectedAddress();

                    if (dropAddress != null) {
                        tv_drop_location.setText(dropAddress.getShortname());
                    }

                    dialog.dismiss();
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);

            negativeButton.setBackgroundColor(Color.WHITE);
            positiveButton.setBackgroundColor(Color.WHITE);

            LoginResponse lr = PreferenceUtil.getUserDetails(getContext());
            service.getUserAddresses(lr.getUserid()).enqueue(new Callback<AddressResponse>() {
                @Override
                public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        tv_empty.setVisibility(View.GONE);
                        addressAdapter.addItems(response.body().getMessage());
                        rl_progress.post(new Runnable() {
                            @Override
                            public void run() {
                                rl_progress.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        tv_empty.setVisibility(View.VISIBLE);
                        rl_progress.setVisibility(View.VISIBLE);
                        showErrorToast(R.string.error_message);
                    }
                }

                @Override
                public void onFailure(Call<AddressResponse> call, Throwable t) {
                    tv_empty.setVisibility(View.VISIBLE);
                    rl_progress.setVisibility(View.VISIBLE);
                    showErrorToast(R.string.error_message);
                }
            });
        }
    }

    private void getPickupLocation() {
        Context context = getContext();

        if (context != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_address, null);

            final TextView tv_empty = dialogView.findViewById(R.id.tv_empty);
            final RelativeLayout rl_progress = dialogView.findViewById(R.id.rl_progress);
            rl_progress.setVisibility(View.VISIBLE);

            RecyclerView rl_orders = dialogView.findViewById(R.id.rv_address);
            rl_orders.setLayoutManager(new LinearLayoutManager(getContext()));

            final SelectAddressAdapter addressAdapter = new SelectAddressAdapter(pickupAddress);

            rl_orders.setAdapter(addressAdapter);

            AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
            builder.setView(dialogView);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pickupAddress = addressAdapter.getSelectedAddress();

                    if (pickupAddress != null) {
                        tv_pickup_location.setText(pickupAddress.getShortname());
                    }

                    dialog.dismiss();
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);

            negativeButton.setBackgroundColor(Color.WHITE);
            positiveButton.setBackgroundColor(Color.WHITE);

            LoginResponse lr = PreferenceUtil.getUserDetails(getContext());
            service.getUserAddresses(lr.getUserid()).enqueue(new Callback<AddressResponse>() {
                @Override
                public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        tv_empty.setVisibility(View.GONE);
                        addressAdapter.addItems(response.body().getMessage());
                        rl_progress.post(new Runnable() {
                            @Override
                            public void run() {
                                rl_progress.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        tv_empty.setVisibility(View.VISIBLE);
                        rl_progress.setVisibility(View.VISIBLE);
                        showErrorToast(R.string.error_message);
                    }
                }

                @Override
                public void onFailure(Call<AddressResponse> call, Throwable t) {
                    tv_empty.setVisibility(View.VISIBLE);
                    rl_progress.setVisibility(View.VISIBLE);
                    showErrorToast(R.string.error_message);
                }
            });
        }
    }

    @Override
    public void onDateTimeSet(String date, Time time) {
        this.date = date;
        this.time = time;
        tv_date_time.setText(String.format("%s %s", date, time.getTime()));
    }
}
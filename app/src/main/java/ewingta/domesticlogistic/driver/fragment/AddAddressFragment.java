package ewingta.domesticlogistic.driver.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.AddAddressResponse;
import ewingta.domesticlogistic.driver.models.LoginResponse;
import ewingta.domesticlogistic.driver.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.driver.reterofit.RetrofitService;
import ewingta.domesticlogistic.driver.utils.PreferenceUtil;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

public class AddAddressFragment extends BaseFragment implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks, LocationListener {
    final public static int REQUEST_LOCATION = 1994;
    private SupportMapFragment mapFragment;
    private GoogleApiClient googleApiClient;
    private EditText et_short_name, et_address;
    private LocationManager locationManager;
    private Marker marker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = getContext();

        if (context != null) {
            final LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            if (manager != null) {
                if (!hasGPSDevice(context)) {
                    showErrorToast(R.string.gps_not_supported);
                }

                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(context)) {
                    showInfoToast(R.string.gps_not_enabled);

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            enableLoc(context);
                        }
                    });
                }
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);

            if (mapFragment == null) {
                mapFragment = SupportMapFragment.newInstance();
                mapFragment.getMapAsync(this);
            }

            et_short_name = view.findViewById(R.id.et_short_name);
            et_address = view.findViewById(R.id.et_address);

            view.findViewById(R.id.tv_address).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String shortName = et_short_name.getText().toString().trim();
                    String address = et_address.getText().toString().trim();

                    if (shortName.isEmpty()) {
                        showErrorToast(R.string.short_name_empty);
                    } else if (address.isEmpty()) {
                        showErrorToast(R.string.address_empty);
                    } else {
                        LoginResponse lr = PreferenceUtil.getUserDetails(getContext());

                        RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
                        service.addAddress(lr.getUserid(), shortName, address).enqueue(new Callback<AddAddressResponse>() {
                            @Override
                            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    AddAddressResponse ar = response.body();

                                    if (ar != null && ar.getStatus().equals("ok")) {
                                        et_address.setText("");
                                        et_short_name.setText("");
                                        showSuccessToast(R.string.address_added);
                                    } else {
                                        showErrorToast(R.string.error_message);
                                    }

                                } else {
                                    showErrorToast(R.string.error_message);
                                }
                            }

                            @Override
                            public void onFailure(Call<AddAddressResponse> call, Throwable t) {
                                showErrorToast(R.string.error_message);
                            }
                        });
                    }
                }
            });


            Context context = getContext();

            if (context != null) {
                locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            }

            getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        } catch (Exception e) {
            showErrorToast(R.string.error_message);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if (getActivity() != null && !getActivity().isFinishing()) {
            if (EasyPermissions.hasPermissions(getActivity(), perms)) {
                googleMap.setMyLocationEnabled(true);

                enableLocationListener();

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng point) {

                        if (marker != null) {
                            marker.remove();
                        }

                        marker = googleMap.addMarker(new MarkerOptions().position(point)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                        String address = getAddress(point.latitude, point.longitude);
                        et_address.setText(address);
                    }
                });

            } else {
                EasyPermissions.requestPermissions(getActivity(), getString(R.string.storage_location), REQUEST_LOCATION, perms);
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void enableLocationListener() {
        if (locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc(Context context) {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                        }
                    }).build();

            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                Activity activity = getActivity();

                                if (activity != null && !activity.isFinishing()) {
                                    status.startResolutionForResult(activity, REQUEST_LOCATION);
                                }
                            } catch (IntentSender.SendIntentException e) {

                            }
                            break;
                    }
                }
            });
        }
    }

    private String getAddress(double latitude, double longitude) {
        String address = "";
        Context context = getContext();

        if (context != null) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder sbReturnedAddress = new StringBuilder("");

                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        sbReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }

                    address = sbReturnedAddress.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return address;
    }

    @Override
    public void onLocationChanged(Location location) {
        String address = getAddress(location.getLatitude(), location.getLongitude());
        et_address.setText(address);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
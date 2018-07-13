package ewingta.domesticlogistic.driver.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationUtil implements LocationListener {
    private Context context;
    private LocationManager locationManager;
    private Location ToPassLocation = null;

    @SuppressLint("MissingPermission")
    public LocationUtil(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    public Location getLatLongLast() {
        if (ToPassLocation == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);

            if (provider.isEmpty()) {
                provider = LocationManager.NETWORK_PROVIDER;
                onProviderEnabled(provider);
                @SuppressLint("MissingPermission")
                Location location = locationManager.getLastKnownLocation(provider);
                onProviderEnabled(provider);
                return location;
            } else {
                provider = LocationManager.PASSIVE_PROVIDER;
                onProviderEnabled(provider);
                @SuppressLint("MissingPermission")
                Location location = locationManager.getLastKnownLocation(provider);
                onProviderEnabled(provider);
                return location;
            }
        } else {
            return ToPassLocation;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        ToPassLocation = location;
        location.getLatitude();
        location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }
}
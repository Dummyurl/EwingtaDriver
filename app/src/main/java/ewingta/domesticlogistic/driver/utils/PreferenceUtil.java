package ewingta.domesticlogistic.driver.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import ewingta.domesticlogistic.driver.models.Area;
import ewingta.domesticlogistic.driver.models.City;
import ewingta.domesticlogistic.driver.models.LoginResponse;

/**
 * Created by FAMILY on 14-12-2017.
 */

public class PreferenceUtil {
    public static LoginResponse getUserDetails(Context context) {
        LoginResponse loginResponse = null;

        String userValues = PreferenceManager.getDefaultSharedPreferences(context).getString("user_details", null);

        if (userValues != null) {
            loginResponse = new Gson().fromJson(userValues, LoginResponse.class);
        }

        return loginResponse;
    }

    public static void setUserDetails(Context context, LoginResponse loginResponse) {
        String userDetails = new Gson().toJson(loginResponse);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("user_details", userDetails).apply();
    }

    public static void setCity(Context context, City city) {
        String cityString = new Gson().toJson(city);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("city", cityString).apply();
    }

    public static City getCity(Context context) {
        City city = null;

        String cityString = PreferenceManager.getDefaultSharedPreferences(context).getString("city", null);

        if (cityString != null) {
            city = new Gson().fromJson(cityString, City.class);
        }

        return city;
    }
}

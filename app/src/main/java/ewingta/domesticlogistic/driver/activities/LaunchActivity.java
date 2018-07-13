package ewingta.domesticlogistic.driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import ewingta.domesticlogistic.driver.MainActivity;
import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.Area;
import ewingta.domesticlogistic.driver.models.City;
import ewingta.domesticlogistic.driver.models.LoginResponse;
import ewingta.domesticlogistic.driver.utils.PreferenceUtil;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginResponse userDetails = PreferenceUtil.getUserDetails(LaunchActivity.this);

                if (userDetails == null) {
                    Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    finish();
                } else {
                    City city = PreferenceUtil.getCity(LaunchActivity.this);

                    if (city == null) {
                        Intent intent = new Intent(LaunchActivity.this, CityActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        finish();
                    } else {
                        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        finish();
                    }
                }
            }
        }, 5000);

    }
}

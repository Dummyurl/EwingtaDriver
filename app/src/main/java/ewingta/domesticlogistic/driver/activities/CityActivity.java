package ewingta.domesticlogistic.driver.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import ewingta.domesticlogistic.driver.MainActivity;
import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.adapter.AreaAdapter;
import ewingta.domesticlogistic.driver.adapter.CityAdapter;
import ewingta.domesticlogistic.driver.models.Area;
import ewingta.domesticlogistic.driver.models.AreaResponse;
import ewingta.domesticlogistic.driver.models.City;
import ewingta.domesticlogistic.driver.models.CityResponse;
import ewingta.domesticlogistic.driver.reterofit.RetrofitInstance;
import ewingta.domesticlogistic.driver.reterofit.RetrofitService;
import ewingta.domesticlogistic.driver.utils.PreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityActivity extends BaseActivity implements CityAdapter.ISetOnCityClickListener {
    public static final String FROM_LOGIN = "FROM_LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        Toolbar toolbar_city = findViewById(R.id.toolbar_city);
        toolbar_city.setTitle(R.string.select_city);
        setSupportActionBar(toolbar_city);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        RecyclerView rv_city = findViewById(R.id.rv_city);
        rv_city.setLayoutManager(new LinearLayoutManager(this));
        final CityAdapter cityAdapter = new CityAdapter(this);
        rv_city.setAdapter(cityAdapter);

        final RelativeLayout rl_progress = findViewById(R.id.rl_progress);

        final RetrofitService service = RetrofitInstance.createService(RetrofitService.class);
        service.getCities().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CityResponse cr = response.body();

                    if (cr.getStatus().equals("ok")) {
                        cityAdapter.addItems(cr.getCities());
                    }
                }

                rl_progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                rl_progress.setVisibility(View.GONE);
                showErrorToast(R.string.error_message);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();
        boolean fromMain = intent.getBooleanExtra(FROM_LOGIN, false);

        if (fromMain) {
            Intent returnIntent = getIntent();
            setResult(MainActivity.CITY_CHANGE, returnIntent);
            finish();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        } else {
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            finish();
        }
    }

    @Override
    public void onCityClicked(City city) {
        PreferenceUtil.setCity(this, city);
        onBackPressed();
    }
}

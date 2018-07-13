package ewingta.domesticlogistic.driver.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.Area;
import ewingta.domesticlogistic.driver.models.City;

public class CityAdapter extends CommonRecyclerAdapter<City> {

    private ISetOnCityClickListener iSetOnCityClickListener;

    public CityAdapter(ISetOnCityClickListener iSetOnCityClickListener) {
        this.iSetOnCityClickListener = iSetOnCityClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);

        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        CityViewHolder cityViewHolder = (CityViewHolder) holder;
        cityViewHolder.bindData(position);
    }


    private class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_city;

        private CityViewHolder(View view) {
            super(view);
            tv_city = view.findViewById(R.id.tv_city);
            view.setOnClickListener(this);
        }

        private void bindData(int position) {
            City city = getItem(position);
            tv_city.setText(city.getLocation_name());
        }

        @Override
        public void onClick(View v) {
            City city = getItem(getAdapterPosition());
            iSetOnCityClickListener.onCityClicked(city);
        }
    }

    public interface ISetOnCityClickListener {
        void onCityClicked( City city );
    }
}

package ewingta.domesticlogistic.driver.adapter;

import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.Price;


public class PriceAdapter extends CommonRecyclerAdapter<Price> {

    private Price selectedPrice;
    private AppCompatRadioButton rb_selected;

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_price, parent, false);

        return new PriceViewHolder(itemView);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        PriceViewHolder viewHolder = (PriceViewHolder) holder;
        viewHolder.bindData(position);
    }

    public Price getSelectedPrice() {
        return selectedPrice;
    }

    private class PriceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout rl_prices;
        private TextView tv_price_name, tv_price_value, tv_gst;
        private AppCompatRadioButton rb_price;

        private PriceViewHolder(View view) {
            super(view);
            rl_prices = view.findViewById(R.id.rl_prices);
            tv_price_name = view.findViewById(R.id.tv_price_name);
            tv_price_value = view.findViewById(R.id.tv_price_value);
            tv_gst = view.findViewById(R.id.tv_gst);
            rb_price = view.findViewById(R.id.rb_price);

            view.setOnClickListener(this);
            rl_prices.setOnClickListener(this);
        }


        private void bindData(int position) {
            Price price = getItem(position);
            tv_price_name.setText(price.getPrice_name());
            tv_price_value.setText(price.getPrice_value());
            tv_gst.setText(String.format("GST : %s", price.getGst()));

            if (selectedPrice != null && selectedPrice.getId().equals(price.getId())) {
                rb_selected = rb_price;
                rb_price.setChecked(true);
            } else {
                rb_price.setChecked(false);
            }
        }

        @Override
        public void onClick(View v) {

            if (rb_selected != null) {
                rb_selected.setChecked(false);
            }

            rb_selected = rb_price;
            selectedPrice = getItem(getAdapterPosition());
            rb_price.setChecked(true);
        }
    }
}
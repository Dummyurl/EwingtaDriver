package ewingta.domesticlogistic.driver.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.Address;

public class AddressAdapter extends CommonRecyclerAdapter<Address> {
    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);

        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        AddressViewHolder viewHolder = (AddressViewHolder) holder;
        viewHolder.bindData(position);
    }

    private class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_short_name, tv_address;

        private AddressViewHolder(View view) {
            super(view);
            tv_short_name = view.findViewById(R.id.tv_short_name);
            tv_address = view.findViewById(R.id.tv_address);
        }


        private void bindData(int position) {
            Address address = getItem(position);
            tv_short_name.setText(address.getShortname());
            tv_address.setText(address.getLocation());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
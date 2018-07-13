package ewingta.domesticlogistic.driver.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.Address;

public class SelectAddressAdapter extends CommonRecyclerAdapter<Address> {

    private Address selectedAddress;
    private RadioButton rb__selected_checked;

    public SelectAddressAdapter(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_address, parent, false);

        return new SelectAddressViewHolder(itemView);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        SelectAddressViewHolder viewHolder = (SelectAddressViewHolder) holder;
        viewHolder.bindData(position);
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    private class SelectAddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_short_name, tv_address;
        private RadioButton rb_checked;

        private SelectAddressViewHolder(View view) {
            super(view);
            tv_short_name = view.findViewById(R.id.tv_short_name);
            tv_address = view.findViewById(R.id.tv_address);
            rb_checked = view.findViewById(R.id.rb_checked);
            view.setOnClickListener(this);
            view.findViewById(R.id.rl_address).setOnClickListener(this);
            rb_checked.setOnClickListener(this);
        }


        private void bindData(int position) {
            Address address = getItem(position);
            tv_short_name.setText(address.getShortname());
            tv_address.setText(address.getLocation());

            if (selectedAddress == null) {
                rb_checked.setChecked(false);
            } else {
                if (selectedAddress.getId() == address.getId()) {
                    rb__selected_checked = rb_checked;
                    rb_checked.setChecked(true);
                } else {
                    rb_checked.setChecked(false);
                }
            }
        }

        @Override
        public void onClick(View v) {
            if (rb__selected_checked != null) {
                rb__selected_checked.setChecked(false);
            }

            rb__selected_checked = rb_checked;
            selectedAddress = getItem(getAdapterPosition());

            rb_checked.setChecked(true);
        }
    }
}
package ewingta.domesticlogistic.driver.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.activities.OrderActivity;
import ewingta.domesticlogistic.driver.models.Order;

public class OrdersAdapter extends CommonRecyclerAdapter<Order> {

    private Context context;

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_order, parent, false);

        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        OrderViewHolder viewHolder = (OrderViewHolder) holder;
        viewHolder.bindData(position);
    }

    private class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_order_id, tv_order_type, tv_customer_name, tv_customer_phone, tv_status,tv_detail;

        private OrderViewHolder(View view) {
            super(view);
            tv_order_id = view.findViewById(R.id.tv_order_id);
            tv_order_type = view.findViewById(R.id.tv_order_type);
            tv_customer_name = view.findViewById(R.id.tv_customer_name);
            tv_customer_phone = view.findViewById(R.id.tv_customer_phone);
            tv_status = view.findViewById(R.id.tv_status);
            tv_detail = view.findViewById(R.id.tv_detail);
            tv_detail.setOnClickListener(this);
        }


        private void bindData(int position) {
            Order order = getItem(position);

            tv_order_id.setText(String.format("#%s", order.getOrder_num()));
            tv_status.setText(order.getStatus());
            tv_customer_name.setText(order.getCustomer_name());
            tv_customer_phone.setText(order.getCustomer_phone());
            tv_order_type.setText(order.getTime());
        }

        @Override
        public void onClick(View v) {
            Order order = getItem(getAdapterPosition());
            String orderValue = new Gson().toJson(order);

            Intent orderIntent = new Intent(context, OrderActivity.class);
            orderIntent.putExtra(OrderActivity.ORDER, orderValue);
            context.startActivity(orderIntent);
            Activity activity = (Activity) context;

            if (activity != null && !activity.isFinishing()) {
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        }
    }
}

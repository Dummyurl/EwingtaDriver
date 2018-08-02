package ewingta.domesticlogistic.driver.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.activities.VerifyOTPActivty;
import ewingta.domesticlogistic.driver.models.Order;

public class OrderDetailsFragment extends BottomSheetDialogFragment {

    private static final String ORDER_DETAILS = "ORDER_DETAILS";
    private Order order;

    public static OrderDetailsFragment newInstance(String orderDetails) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_DETAILS, orderDetails);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String orderValue = getArguments().getString(ORDER_DETAILS);
        order = new Gson().fromJson(orderValue, Order.class);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_order_details, null);

        TextView tv_order_type = contentView.findViewById(R.id.tv_order_type);
        TextView tv_customer_name = contentView.findViewById(R.id.tv_customer_name);
        TextView tv_customer_phone = contentView.findViewById(R.id.tv_customer_phone);
        TextView tv_delivery = contentView.findViewById(R.id.tv_delivery);
        TextView tv_order_size = contentView.findViewById(R.id.tv_order_size);
        TextView putv_customer_phone = contentView.findViewById(R.id.putv_customer_phone);
        TextView putv_customer_name = contentView.findViewById(R.id.putv_customer_name);
        TextView putv_pick_up = contentView.findViewById(R.id.putv_pick_up);
        TextView tv_Otp=contentView.findViewById(R.id.otptv_completed);
        TextView tv_status=contentView.findViewById(R.id.tv_status);



        tv_order_type.setText(order.getTime());
        tv_customer_name.setText(order.getDrop_name());
        tv_customer_phone.setText(order.getDrop_phone());
        tv_delivery.setText(order.getLocation_id());
        tv_order_size.setText(order.getCat_name());

        putv_customer_name.setText(order.getCustomer_name());
        putv_customer_phone.setText(order.getCustomer_phone());
        putv_pick_up.setText(order.getCustomer_address());

        tv_customer_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8520083022"));
                startActivity(intent);
            }
        });

        tv_Otp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getContext(), VerifyOTPActivty.class));
            }
        });

      /*  tv_status.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getContext(), StartRideActivity.class));
            }
        });*/

        dialog.setContentView(contentView);
    }
}


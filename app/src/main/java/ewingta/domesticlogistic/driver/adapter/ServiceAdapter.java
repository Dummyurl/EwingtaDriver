package ewingta.domesticlogistic.driver.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.Service;

public class ServiceAdapter extends BaseAdapter {

    private List<Service> services;
    private Context context;

    public ServiceAdapter(Context context, List<Service> services) {
        Service service = new Service();
        service.setService_name(context.getString(R.string.what_do_you_want_to_deliver));
        service.setPublished(context.getString(R.string.what_do_you_want_to_deliver));
        service.setService_key(context.getString(R.string.what_do_you_want_to_deliver));
        service.setId(0);

        this.services = new ArrayList<>();
        this.services.add(service);
        this.services.addAll(services);
        this.context = context;
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Service getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return services.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Service service = getItem(position);
        ServiceViewHolder priceVH;
        View rowView = convertView;

        if (rowView == null) {

            priceVH = new ServiceViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inflater != null) {
                rowView = inflater.inflate(R.layout.item_spinner, null, false);

                priceVH.tv_title = rowView.findViewById(R.id.tv_title);
                rowView.setTag(priceVH);
            }
        } else {
            priceVH = (ServiceViewHolder) rowView.getTag();
        }

        if (service != null) {
            priceVH.tv_title.setText(String.valueOf(service.getService_name()));

            if (position == 0) {
                priceVH.tv_title.setTextColor(Color.GRAY);
            } else {
                priceVH.tv_title.setTextColor(Color.BLACK);
            }
        }

        return rowView;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    private class ServiceViewHolder {
        TextView tv_title;
    }
}


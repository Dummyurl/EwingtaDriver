package ewingta.domesticlogistic.driver.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.models.Time;

public class TimeAdapter extends BaseAdapter {

    private List<Time> times;
    private Context context;

    public TimeAdapter(Context context, List<Time> times) {
        this.times = times;
        this.context = context;
    }

    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public Time getItem(int position) {
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        return times.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Time time = getItem(position);
        TimeViewHolder priceVH;
        View rowView = convertView;

        if (rowView == null) {

            priceVH = new TimeViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inflater != null) {
                rowView = inflater.inflate(R.layout.item_spinner_grey, null, false);

                priceVH.tv_title = rowView.findViewById(R.id.tv_title);
                rowView.setTag(priceVH);
            }
        } else {
            priceVH = (TimeViewHolder) rowView.getTag();
        }

        if (time != null) {
            priceVH.tv_title.setText(String.valueOf(time.getTime()));

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

    private class TimeViewHolder {
        TextView tv_title;
    }
}


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
import ewingta.domesticlogistic.driver.models.Area;

public class AreaAdapter extends BaseAdapter {

    private List<Area> areas;
    private Context context;

    public AreaAdapter(Context context, List<Area> areas) {
        Area city = new Area();
        city.setBranch_name(context.getString(R.string.select_area));
        city.setId(0);

        this.areas = new ArrayList<>();
        this.areas.add(city);
        this.areas.addAll(areas);
        this.context = context;
    }

    @Override
    public int getCount() {
        return areas.size();
    }

    @Override
    public Area getItem(int position) {
        return areas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return areas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Area area = getItem(position);
        AreaViewHolder priceVH;
        View rowView = convertView;

        if (rowView == null) {

            priceVH = new AreaViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inflater != null) {
                rowView = inflater.inflate(R.layout.item_spinner_grey, null, false);

                priceVH.tv_title = rowView.findViewById(R.id.tv_title);
                rowView.setTag(priceVH);
            }
        } else {
            priceVH = (AreaViewHolder) rowView.getTag();
        }

        if (area != null) {
            priceVH.tv_title.setText(String.valueOf(area.getBranch_name()));

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

    private class AreaViewHolder {
        TextView tv_title;
    }
}
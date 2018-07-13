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
import ewingta.domesticlogistic.driver.models.Category;

public class CategoryAdapter extends BaseAdapter {

    private List<Category> categories;
    private Context context;

    public CategoryAdapter(Context context, List<Category> categories) {
        Category category = new Category();
        category.setCat_name(context.getString(R.string.delivery_type));
        category.setPublished(context.getString(R.string.delivery_type));
        category.setId(0);

        this.categories = new ArrayList<>();
        this.categories.add(category);
        this.categories.addAll(categories);
        this.context = context;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Category category = getItem(position);
        CategoryViewHolder priceVH;
        View rowView = convertView;

        if (rowView == null) {

            priceVH = new CategoryViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inflater != null) {
                rowView = inflater.inflate(R.layout.item_spinner, null, false);
                priceVH.tv_title = rowView.findViewById(R.id.tv_title);
                rowView.setTag(priceVH);
            }
        } else {
            priceVH = (CategoryViewHolder) rowView.getTag();
        }

        if (category != null) {
            priceVH.tv_title.setText(String.valueOf(category.getCat_name()));

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

    private class CategoryViewHolder {
        TextView tv_title;
    }
}

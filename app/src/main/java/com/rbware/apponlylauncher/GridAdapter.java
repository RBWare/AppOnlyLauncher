package com.rbware.apponlylauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context context;

    public class ViewHolder {
        public ImageView imageView;
        public TextView textTitle;
    }

    private ArrayList<ApplicationItem> items;
    private LayoutInflater mInflater;

    public GridAdapter(Context context, ArrayList<ApplicationItem> locations) {

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        items = locations;

    }

    public ArrayList<ApplicationItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ApplicationItem> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        if (items != null && position >= 0 && position < getCount()) {
            return items.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setItemsList(ArrayList<ApplicationItem> locations) {
        this.items = locations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {

            view = mInflater.inflate(R.layout.grid_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view
                    .findViewById(R.id.grid_item_image);
            viewHolder.textTitle = (TextView) view
                    .findViewById(R.id.grid_item_label);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ApplicationItem gridItems = items.get(position);
        viewHolder.textTitle.setText(gridItems.getName());
        viewHolder.imageView.setImageDrawable(gridItems.getIcon());

        return view;
    }
}
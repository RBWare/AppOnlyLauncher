package com.rbware.apponlylauncher.view;

import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.service.notification.StatusBarNotification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rbware.apponlylauncher.R;

import java.util.ArrayList;

import androidx.core.app.NotificationCompat;

public class NotificationAdapter extends BaseAdapter {

    public class ViewHolder {

        public ImageView imageView;
        public TextView textAppTitle;
        public TextView textTime;
        public TextView textTitle;
        public TextView textDescription;

    }

    private ArrayList<StatusBarNotification> items;
    private LayoutInflater mInflater;

    public NotificationAdapter(Context context, ArrayList<StatusBarNotification> locations) {

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        items = locations;

    }

    public ArrayList<StatusBarNotification> getItems() {
        return items;
    }

    public void setItems(ArrayList<StatusBarNotification> items) {
        this.items = items;
    }

    public boolean contains(StatusBarNotification notification) {
        if (items != null && !items.isEmpty()){
            for (StatusBarNotification displayedNotifcation: items) {
                if (displayedNotifcation.getId() == notification.getId()){
                    return true;
                }
            }
        }

        return false;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {

            view = mInflater.inflate(R.layout.notification_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view
                    .findViewById(R.id.notification_item_icon);
            viewHolder.textAppTitle = (TextView)view.findViewById(R.id.notification_item_app_title);
            viewHolder.textTime = (TextView)view.findViewById(R.id.notification_item_time);
            viewHolder.textTitle = (TextView) view
                    .findViewById(R.id.notification_item_title);
            viewHolder.textDescription = (TextView) view
                    .findViewById(R.id.notification_item_description);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        StatusBarNotification notification = items.get(position);

        try {
            PackageManager packageManager = view.getContext().getPackageManager();
            String appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(notification.getPackageName(), PackageManager.GET_META_DATA));
            viewHolder.textAppTitle.setText(appName);
        } catch(PackageManager.NameNotFoundException e){

        }

        if (notification.getNotification().actions != null && notification.getNotification().actions[0].title != null) {

            String title = "";
            if (notification.getNotification().extras != null &&
                    notification.getNotification().extras.getString("android.title") != null &&
                    !notification.getNotification().extras.getString("android.title").equals("")){
                title = notification.getNotification().extras.getString("android.title");
            } else if ( notification.getNotification().actions !=null && notification.getNotification().actions[0].title != null) {
                title = (String)notification.getNotification().actions[0].title;
            }

            viewHolder.textDescription.setVisibility(View.VISIBLE);

            viewHolder.textTitle.setText(title);
            if (notification.getNotification().extras != null)
                viewHolder.textDescription.setText(notification.getNotification().extras.getString("android.text"));

        } else {
            viewHolder.textTitle.setText(notification.getNotification().tickerText);
            viewHolder.textDescription.setVisibility(View.GONE);
        }

        Icon icon = notification.getNotification().getLargeIcon() != null ? notification.getNotification().getLargeIcon() : notification.getNotification().getSmallIcon();
        if (icon != null) {
            viewHolder.imageView.setImageDrawable(icon.loadDrawable(view.getContext()));
        }


        return view;
    }
}
package com.rbware.apponlylauncher.fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.rbware.apponlylauncher.R;
import com.rbware.apponlylauncher.view.NotificationAdapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class NotificationListFragment extends Fragment {

    private NotificationReceiver nReceiver;

    private ListView notificationListView;
    private NotificationAdapter notificationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_list_fragment, container, false);

        notificationListView = (ListView) v.findViewById(R.id.notification_listview);

        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
        getContext().registerReceiver(nReceiver,filter);

        Intent i = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        i.putExtra("command","list");
        getContext().sendBroadcast(i);

        notificationAdapter = new NotificationAdapter(getContext(), new ArrayList<StatusBarNotification>());
        if (notificationListView != null) {
            notificationListView.setAdapter(notificationAdapter);
        }

        notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {
//                onGridItemClick((GridView) parent, view, position, id);
            }
        });

        notificationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                showApplicationDataIntent(gridItems.get(position).getPackageName());
                return true;
            }
        });


        Button testNotificationButton = (Button)v.findViewById(R.id.list_notifications);
        Button generateNotificationButton = (Button)v.findViewById(R.id.test_notification);
        testNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
                i.putExtra("command","list");
                getContext().sendBroadcast(i);
            }
        });

        generateNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                // Test

                NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

// The id of the channel.
                String id = "my_channel_01";

// The user-visible name of the channel.
                CharSequence name = "Channel Name";

// The user-visible description of the channel.
                String description = "Description";

                int importance = NotificationManager.IMPORTANCE_LOW;

                NotificationChannel mChannel = new NotificationChannel(id, name,importance);

// Configure the notification channel.
                mChannel.setDescription(description);

                mChannel.enableLights(true);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
                mChannel.setLightColor(Color.RED);

                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                mNotificationManager.createNotificationChannel(mChannel);

                mNotificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

// Sets an ID for the notification, so it can be updated.
                int notifyID = 1;

// The id of the channel.
                String CHANNEL_ID = "my_channel_01";

                Notification.Action action = new Notification.Action(R.mipmap.ic_launcher, "Testing", null);

// Create a notification and set the notification channel.
                Notification notification = new Notification.Builder(getContext())
                        .setContentTitle("New Message")
                        .setActions(action)
                        .setContentText("You've received new messages.")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setChannelId(CHANNEL_ID)
                        .build();

// Issue the notification.
                mNotificationManager.notify(notifyID, notification);
            }
        });


        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        v.setBackground(wallpaperDrawable);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(nReceiver);
    }

    public static NotificationListFragment newInstance() {

        NotificationListFragment f = new NotificationListFragment();
        // No bundle for now
        return f;
    }

    class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch(intent.getStringExtra("notification_event")){
                case "onNotificationRemoved":
                    break;
                case "onNotificationPosted":
                default:
                    StatusBarNotification notification = (StatusBarNotification)intent.getParcelableExtra("notification");

                    if (notification != null) {

                        if (!notificationAdapter.contains(notification)){
                            // Check if this exact notification is in the list already.
                            notificationAdapter.getItems().add(0, notification);
                            notificationAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }

        }
    }
}

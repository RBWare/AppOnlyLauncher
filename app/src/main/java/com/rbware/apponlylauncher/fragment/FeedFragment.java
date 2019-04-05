package com.rbware.apponlylauncher.fragment;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.nirhart.parallaxscroll.views.ParallaxListView;
import com.rbware.apponlylauncher.R;
import com.rbware.apponlylauncher.view.CustomListAdapter;

import androidx.fragment.app.Fragment;

public class FeedFragment extends Fragment {

    private TextView batteryWeatherLocationText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.feed_fragment, container, false);


//        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
//        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
//        v.setBackground(wallpaperDrawable);
//
//

        ListView listView = (ListView) v.findViewById(R.id.feed_listview);
        CustomListAdapter adapter = new CustomListAdapter(LayoutInflater.from(getContext()));

        batteryWeatherLocationText = (TextView)v.findViewById(R.id.battery_weather_location_textview);

        listView.setAdapter(adapter);

        getContext().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        return v;
    }

    public static FeedFragment newInstance() {

        FeedFragment f = new FeedFragment();
        Bundle b = new Bundle();

        return f;
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            String widgetInfo = getContext().getString(R.string.battery_weather_location);
            // 100% | 83º Partly Cloudy | Haslet, TX

            batteryWeatherLocationText.setText(String.format(widgetInfo, String.valueOf(level) + "%", "83º Partly Cloudy","Haslet, TX"));
        }
    };
}

package com.rbware.apponlylauncher;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager awesomePager;
    private PagerAdapter pm;
    private PackageManager mPackageManager;

    private ArrayList<ApplicationItem> mApplicationList = new ArrayList<>();
    private int ITEMS_PER_PAGE;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onResume() {
        super.onResume();

        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        ((FrameLayout)findViewById(R.id.container)).setBackground(wallpaperDrawable);

        setupViews();
    }

    @Override
    public void onBackPressed() {
        // NOTHING!
    }

    private void setupViews(){

        mApplicationList.clear();
        mPackageManager = getPackageManager();
        ITEMS_PER_PAGE = getResources().getInteger(R.integer.number_of_icons);

        final Context context = getBaseContext();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities( mainIntent, 0);
        for ( ResolveInfo P : pkgAppsList ) {
            addAppToArray(P);
        };

        Collections.sort(mApplicationList);

        awesomePager = (ViewPager) findViewById(R.id.pager);

        List<GridFragment> gridFragments = new ArrayList<GridFragment>();
        int totalPages = mApplicationList.size() / ITEMS_PER_PAGE;
        if (mApplicationList.size() % ITEMS_PER_PAGE != 0) totalPages += 1;

        for (int i = 0; i < totalPages; i++){
            ArrayList<ApplicationItem> tempList = new ArrayList<>();

            int lowRange = i * ITEMS_PER_PAGE;
            int highRange = lowRange + ITEMS_PER_PAGE;

            if (highRange > mApplicationList.size()) highRange = mApplicationList.size();

            for (int x = lowRange; x < highRange; x++){
                tempList.add(mApplicationList.get(x));
            }

            gridFragments.add(new GridFragment(tempList, MainActivity.this));
        }

        pm = new PagerAdapter(getSupportFragmentManager(), gridFragments);
        awesomePager.setAdapter(pm);
        // ViewPager Indicator
        CirclePageIndicator mIndicator = (CirclePageIndicator) findViewById(R.id.pagerIndicator);
        mIndicator.setViewPager(awesomePager);
    }

    private void addAppToArray(ResolveInfo appInfo){

        ApplicationItem applicationItem = new ApplicationItem();
        applicationItem.setName(appInfo.loadLabel(mPackageManager).toString());
        applicationItem.setPackageName(appInfo.activityInfo.applicationInfo.packageName);
        applicationItem.setIcon(appInfo.loadIcon(mPackageManager));

        if (applicationItem.getPackageName() != null) {
            if (!applicationItem.getName().equals(getString(R.string.app_name)))
                mApplicationList.add(applicationItem);
        }
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        private List<GridFragment> fragments;

        public PagerAdapter(android.support.v4.app.FragmentManager fm, List<GridFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }
}

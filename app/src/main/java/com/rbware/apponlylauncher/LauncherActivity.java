package com.rbware.apponlylauncher;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LauncherActivity extends FragmentActivity {

    private ViewPager awesomePager;
    private PagerAdapter pm;
    private PackageManager mPackageManager;

    private ArrayList<ApplicationItem> mApplicationList = new ArrayList<>();
    private int ITEMS_PER_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        ((LinearLayout)findViewById(R.id.main_container)).setBackground(wallpaperDrawable);

        setupViews();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // NOTHING
    }

    private void setupViews(){

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

            gridFragments.add(new GridFragment(tempList, LauncherActivity.this));
        }

        pm = new PagerAdapter(getSupportFragmentManager(), gridFragments);
        awesomePager.setAdapter(pm);
        // ViewPager Indicator
        CirclePageIndicator mIndicator = (CirclePageIndicator) findViewById(R.id.pagerIndicator);
        mIndicator.setViewPager(awesomePager);
//        mIndicator.setViewPager(awesomePager);

    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        private List<GridFragment> fragments;

        public PagerAdapter(FragmentManager fm, List<GridFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }

    private void addAppToArray(ResolveInfo appInfo){

        ApplicationItem applicationItem = new ApplicationItem();
        applicationItem.setName(appInfo.loadLabel(mPackageManager).toString());
        applicationItem.setPackageName(appInfo.activityInfo.applicationInfo.packageName);
        applicationItem.setIcon(appInfo.loadIcon(mPackageManager));

        if (applicationItem.getPackageName() != null) {
            mApplicationList.add(applicationItem);
        }
    }

    private int itemsPerPage(){
        // TODO - based on screen dimens eventually
        return 20;
    }
}

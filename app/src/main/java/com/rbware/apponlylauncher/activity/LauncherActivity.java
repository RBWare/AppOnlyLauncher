package com.rbware.apponlylauncher.activity;

import android.os.Bundle;

import com.rbware.apponlylauncher.R;
import com.rbware.apponlylauncher.fragment.AppListFragment;
import com.rbware.apponlylauncher.fragment.FeedFragment;
import com.rbware.apponlylauncher.fragment.NotificationListFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class LauncherActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(1); // Default to the center
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return AppListFragment.newInstance();
                case 1: return FeedFragment.newInstance();
                case 2: return NotificationListFragment.newInstance();
                default: return FeedFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}







//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.os.Build;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.rbware.apponlylauncher.model.ApplicationItem;
//import com.rbware.apponlylauncher.fragment.GridFragment;
//import com.rbware.apponlylauncher.R;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//
//import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
//
//public class LauncherActivity extends FragmentActivity {
//
//    private String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE"};
//    private final int PERMISSION_REQUEST_CODE = 200;
//
//    private ListView mAppListView;
//    private ArrayAdapter mAppListViewAdapter;
//
//    private PackageManager mPackageManager;
//    private ArrayList<ApplicationItem> mApplicationList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_launcher);
//
////        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
////        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
////        ((LinearLayout)findViewById(R.id.main_container)).setBackground(wallpaperDrawable);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (checkPermission()) {
////            Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
//        } else {
////            Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
//        }
//
//        setupViews();
//    }
//
//    @Override
//    public void onBackPressed() {
//        // super.onBackPressed();
//        // NOTHING
//    }
//
//    private boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
////        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
//
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermission() {
//        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0) {
//
//                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                    if (locationAccepted && cameraAccepted) {
////                        Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
//                    } else {
//
////                        Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
//                                showMessageOKCancel("You need to allow access to both the permissions",
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{READ_EXTERNAL_STORAGE},
//                                                            PERMISSION_REQUEST_CODE);
//                                                }
//                                            }
//                                        });
//                                return;
//                            }
//                        }
//
//                    }
//                }
//
//
//                break;
//        }
//    }
//
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(LauncherActivity.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
//
//    private void setupViews(){
//
//        mApplicationList.clear();
//        mPackageManager = getPackageManager();
//
//        final Context context = getBaseContext();
//        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        final List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities( mainIntent, 0);
//        for ( ResolveInfo P : pkgAppsList ) {
//            addAppToArray(P);
//        };
//
//        Collections.sort(mApplicationList);
//
//        mAppListView = findViewById(R.id.app_listview);
//
////        List<GridFragment> gridFragments = new ArrayList<GridFragment>();
////        int totalPages = mApplicationList.size() / ITEMS_PER_PAGE;
////        if (mApplicationList.size() % ITEMS_PER_PAGE != 0) totalPages += 1;
////
////        for (int i = 0; i < totalPages; i++){
////            ArrayList<ApplicationItem> tempList = new ArrayList<>();
////
////            int lowRange = i * ITEMS_PER_PAGE;
////            int highRange = lowRange + ITEMS_PER_PAGE;
////
////            if (highRange > mApplicationList.size()) highRange = mApplicationList.size();
////
////            for (int x = lowRange; x < highRange; x++){
////                tempList.add(mApplicationList.get(x));
////            }
////
////            gridFragments.add(new GridFragment(tempList, LauncherActivity.this));
////        }
//
////        pm = new PagerAdapter(getSupportFragmentManager(), gridFragments);
//        //mAppListViewAdapter = new ArrayAdapter(getSupportFragmentManager(), mApplicationList);
//
//        mAppListView.setAdapter(mAppListViewAdapter);
//
//    }
//
//    private class PagerAdapter extends FragmentStatePagerAdapter {
//        private List<GridFragment> fragments;
//
//        public PagerAdapter(FragmentManager fm, List<GridFragment> fragments) {
//            super(fm);
//            this.fragments = fragments;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return this.fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return this.fragments.size();
//        }
//    }
//
//    private void addAppToArray(ResolveInfo appInfo){
//
//        ApplicationItem applicationItem = new ApplicationItem();
//        applicationItem.setName(appInfo.loadLabel(mPackageManager).toString());
//        applicationItem.setPackageName(appInfo.activityInfo.applicationInfo.packageName);
//        applicationItem.setIcon(appInfo.loadIcon(mPackageManager));
//
//        if (applicationItem.getPackageName() != null) {
//            if (!applicationItem.getName().equals(getString(R.string.app_name)))
//                mApplicationList.add(applicationItem);
//        }
//    }
//
//    private int itemsPerPage(){
//        // TODO - based on screen dimens eventually
//        return 20;
//    }
//}

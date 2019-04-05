package com.rbware.apponlylauncher.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.rbware.apponlylauncher.model.ApplicationItem;
import com.rbware.apponlylauncher.R;
import com.rbware.apponlylauncher.view.AppAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.fragment.app.Fragment;

public class AppListFragment extends Fragment {

    private GridView mGridView;
    private AppAdapter mAppAdapter;
    ArrayList<ApplicationItem> gridItems = new ArrayList<>();

    private ArrayList<ApplicationItem> mApplicationList = new ArrayList<>();
    private PackageManager mPackageManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.app_list_fragment, container, false);

        mGridView = (GridView) v.findViewById(R.id.app_list_gridview);

        return v;
    }

    public static AppListFragment newInstance() {

        AppListFragment f = new AppListFragment();
        // No bundle, for now
        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isAdded() && !isHidden()) {

            mApplicationList.clear();
            gridItems.clear();

            mPackageManager = getContext().getPackageManager();

            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            final List<ResolveInfo> pkgAppsList = getContext().getPackageManager().queryIntentActivities( mainIntent, 0);
            for ( ResolveInfo P : pkgAppsList ) {
                addAppToArray(P);
            };
            Collections.sort(mApplicationList);
            gridItems.addAll(mApplicationList);

            mAppAdapter = new AppAdapter(getContext(), gridItems);
            if (mGridView != null) {
                mGridView.setAdapter(mAppAdapter);
            }

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view,
                                        int position, long id) {
                    onGridItemClick((GridView) parent, view, position, id);
                }
            });

            mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    showApplicationDataIntent(gridItems.get(position).getPackageName());
                    return true;
                }
            });
        }
    }

    public void onGridItemClick(GridView g, View v, int position, long id) {
        Intent intent = getActivity()
                .getPackageManager()
                .getLaunchIntentForPackage(
                        gridItems.get(position).getPackageName());
        getActivity().startActivity(intent);
    }

    private void showApplicationDataIntent(String packageName){
        try {
            //Open the specific App Info page:
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            startActivity(intent);

        } catch ( ActivityNotFoundException e ) {
            //e.printStackTrace();

            //Open the generic Apps page:
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            startActivity(intent);

        }
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
}

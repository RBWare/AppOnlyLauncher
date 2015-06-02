package com.rbware.apponlylauncher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class GridFragment extends Fragment {

    private GridView mGridView;
    private GridAdapter mGridAdapter;
    ArrayList<ApplicationItem> gridItems = new ArrayList<>();
    private Activity activity;

    public GridFragment(ArrayList<ApplicationItem> gridItems, Activity activity){
        this.gridItems = gridItems;
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.grid_layout, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (activity != null) {

            mGridAdapter = new GridAdapter(activity, gridItems);
            if (mGridView != null) {
                mGridView.setAdapter(mGridAdapter);
            }

            mGridView.setOnItemClickListener(new OnItemClickListener() {
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
}
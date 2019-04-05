package com.rbware.apponlylauncher.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rbware.apponlylauncher.R;

import androidx.fragment.app.Fragment;

public class QuickControlsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_list_fragment, container, false);


        return v;
    }

    public static QuickControlsFragment newInstance(String text) {

        QuickControlsFragment f = new QuickControlsFragment();
        Bundle b = new Bundle();

        return f;
    }
}

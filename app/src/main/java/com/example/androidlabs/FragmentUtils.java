package com.example.androidlabs;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.example.androidlabs.DetailsFragment;

public class FragmentUtils {
    public static void displayDetailsFragment(FragmentManager fragmentManager, String name, String height, String mass, int containerId) {
        DetailsFragment fragment = new DetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("height", height);
        bundle.putString("mass", mass);
        fragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }
}

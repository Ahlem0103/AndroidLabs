package com.example.androidlabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {

    private TextView tvName;
    private TextView tvHeight;
    private TextView tvMass;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        tvName = view.findViewById(R.id.tvName);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvMass = view.findViewById(R.id.tvMass);

        // Get the data passed to this fragment
        Bundle args = getArguments();
        if (args != null) {
            tvName.setText("Name: " + args.getString("name", "N/A"));
            tvHeight.setText("Height: " + args.getString("height", "N/A"));
            tvMass.setText("Mass: " + args.getString("mass", "N/A"));
        }

        return view;
    }
}

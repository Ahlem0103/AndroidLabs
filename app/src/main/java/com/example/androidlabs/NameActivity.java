package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        Button buttonThankYou = findViewById(R.id.buttonThankYou);
        Button buttonDonCallMeThat = findViewById(R.id.buttonDonCallMeThat);

        String name = getIntent().getStringExtra("USER_NAME");
        textViewWelcome.setText(getString(R.string.welcome_user, name));

        buttonThankYou.setOnClickListener(v -> {
            setResult(1);
            finish();
        });

        buttonDonCallMeThat.setOnClickListener(v -> {
            setResult(0);
            finish();
        });
    }
}
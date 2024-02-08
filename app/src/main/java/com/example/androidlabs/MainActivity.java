package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        Button buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            Intent intent = new Intent(MainActivity.this, NameActivity.class);
            intent.putExtra("USER_NAME", name);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putString("name", editTextName.getText().toString());
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 0) {
                editTextName.setText("");
            } else if (resultCode == 1) {
                finish();
            }
        }
    }
}
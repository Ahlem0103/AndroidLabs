package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        Button pressMeButton = findViewById(R.id.button_press);
        EditText editText = findViewById(R.id.edittext_love_android);
        TextView textView = findViewById(R.id.text_view_love_android);
        CheckBox checkBox = findViewById(R.id.checkbox);

        pressMeButton.setOnClickListener(v -> {
            String editTextContent = editText.getText().toString();
            textView.setText(editTextContent);
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.toast_message), Toast.LENGTH_SHORT).show();
        });
        boolean isProgrammaticChange = false;
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = isChecked ?
                    getResources().getString(R.string.checkbox_status_on) :
                    getResources().getString(R.string.checkbox_status_off);
            Snackbar.make(buttonView, message, Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.undo), v ->
                            checkBox.setChecked(!isChecked))
                    .show();
        });
    }
}
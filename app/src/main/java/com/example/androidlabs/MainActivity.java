package com.example.androidlabs;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.graphics.Color;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<TodoItem> todoItems = new ArrayList<>();
    private TodoAdapter adapter;
    private EditText editTextTodo;
    private SwitchCompat switchUrgent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listViewTodos);
        editTextTodo = findViewById(R.id.editTextTodo);
        switchUrgent = findViewById(R.id.switchUrgent);

        Button buttonAdd = findViewById(R.id.buttonAdd);

        adapter = new TodoAdapter();
        listView.setAdapter(adapter);

        buttonAdd.setOnClickListener(v -> {
                String text = editTextTodo.getText().toString();
                boolean isUrgent = switchUrgent.isChecked();
                if (!text.isEmpty()) {
                    todoItems.add(0, new TodoItem(text, isUrgent));
                    adapter.notifyDataSetChanged();
                    editTextTodo.setText("");
                }
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Log.d("MainActivity", "Long click detected on position: " + position);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.confirm_delete)
                        .setMessage(getString(R.string.selected_row, position))
                        .setPositiveButton(R.string.delete, (dialog, which) -> {
                            todoItems.remove(position);
                            adapter.notifyDataSetChanged();
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
                return true;

        });
    }

    private class TodoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return todoItems.size();
        }

        @Override
        public TodoItem getItem(int position) {
            return todoItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
            }
            TextView textView = convertView.findViewById(R.id.textViewTodoItem);
            TodoItem todoItem = getItem(position);

            textView.setText(todoItem.getText());
            if (todoItem.isUrgent()) {
                convertView.setBackgroundColor(Color.RED);
                textView.setTextColor(Color.WHITE);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }
}
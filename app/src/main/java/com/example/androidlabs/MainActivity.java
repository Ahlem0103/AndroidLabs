package com.example.androidlabs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private final ArrayList<String> characterNames = new ArrayList<>();
    private MyListAdapter adapter;
    private ArrayList<String> characterHeights = new ArrayList<>();
    private ArrayList<String> characterMasses = new ArrayList<>();

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isTablet()) {
            setContentView(R.layout.layout_b);
        } else {
            setContentView(R.layout.layout_a);

        }

        listView = findViewById(R.id.listView);
        adapter = new MyListAdapter();
        listView.setAdapter(adapter);


        listView.setOnItemClickListener((parent, view, position, id) -> {
            String name = characterNames.get(position);
            String height = getHeightForCharacter(position);
            String mass = getMassForCharacter(position);

            Log.i("ListView Click", "Item clicked: " + name);

            FrameLayout detailsContainer = findViewById(R.id.detailsContainer);
            if (detailsContainer != null) {
                // Tablet mode
                FragmentUtils.displayDetailsFragment(getSupportFragmentManager(), name, height, mass, R.id.detailsContainer);
            } else {
                // Phone mode
                Intent intent = new Intent(MainActivity.this, EmptyActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("height", height);
                intent.putExtra("mass", mass);
                startActivity(intent);
            }
        });


        new FetchCharactersTask().execute("https://akabab.github.io/starwars-api/api/all.json");
    }

    private String getHeightForCharacter(int position) {
        //logic
        return characterHeights.get(position);
    }

    private String getMassForCharacter(int position) {
        //logic
        return characterMasses.get(position);
    }

    private void parseJSONAndPopulateListView(String json) {
        try {
            JSONArray characters = new JSONArray(json);
            for (int i = 0; i < characters.length(); i++) {
                JSONObject character = characters.getJSONObject(i);
                String name = character.optString("name", "N/A");
                // Use optString and convert to String, to handle different types
                String height = character.optString("height", "N/A");
                String mass = character.optString("mass", "N/A");

                Log.i("JSON Parsing", "Character parsed: Name=" + name + ", Height=" + height + ", Mass=" + mass);

                characterNames.add(name);
                characterHeights.add(height);
                characterMasses.add(mass);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data: " + e.toString());
        }
    }


    private class FetchCharactersTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response == null) {
                Log.e("FetchCharactersTask", "Response is null - check API or network connectivity.");
            } else {
                Log.i("FetchCharactersTask", "Response received: " + response);
                parseJSONAndPopulateListView(response);
            }
        }
    }

        private class MyListAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return characterNames.size();
            }

            @Override
            public Object getItem(int position) {
                return characterNames.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(MainActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                TextView textView = convertView.findViewById(android.R.id.text1);
                textView.setText(characterNames.get(position));
                Log.i("ListAdapter", "View created for item: " + characterNames.get(position));
                return convertView;
            }



        }
    }


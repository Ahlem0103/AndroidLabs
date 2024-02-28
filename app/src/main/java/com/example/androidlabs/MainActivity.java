package com.example.androidlabs;

import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        //asynctask
        new CatImages().execute();
    }

    //asynctask to download and display images cats
    private class CatImages extends AsyncTask<String, Integer, Void> {
        private Bitmap currentCatImage;

        @Override
        protected Void doInBackground(String... strings) {
            while (true) {
                try {
                    //random cat image from json logic
                    URL url = new URL("https://cataas.com/cat?json=true");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream inputStream = connection.getInputStream();
                    String result = convertStreamToString(inputStream);
                    JSONObject jsonObject = new JSONObject(result);

                    //
                    if (jsonObject.has("_id")) {
                        String catImageId = jsonObject.getString("_id");
                        String catImageUrl = "https://cataas.com/cat/" + catImageId;

                        //Downlad of cat image
                        InputStream in = new URL(catImageUrl).openStream();
                        currentCatImage = BitmapFactory.decodeStream(in);
                    } else {
                        //error handling
                        Log.e("CatImages", "No URL found in the JSON response");
                    }

                    //update UI
                    for (int i = 0; i < 100; i++) {
                        publishProgress(i);
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            if (values[0] == 0) { //update image
                imageView.setImageBitmap(currentCatImage);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (imageView != null && currentCatImage != null) {
                imageView.setImageBitmap(currentCatImage);
            }
        }

        //method to convert inputStream to String
        private String convertStreamToString(InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }
}
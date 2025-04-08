package com.example.hustagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        grid = (GridView) findViewById(R.id.gridView);

        ArrayList<Bitmap> results = new ArrayList<Bitmap>();
        ArrayList<String> comment_results = new ArrayList<String>();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();
        String url = "http://10.0.0.39:5000/images";
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject data = response.getJSONObject(i);
                                        String pictureString = data.getString("image");
                                        String commentString = data.getString("comment");

                                        byte[] pictureBytes = Base64.decode(pictureString, Base64.DEFAULT);

                                        Bitmap picture =  BitmapFactory.decodeByteArray(pictureBytes, 0, pictureBytes.length);

                                        results.add(picture);
                                        comment_results.add(commentString);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Log.d("ViewActivity", "Result size " + results.size());
                                PictureListAdapter adapter = new PictureListAdapter(grid.getContext(), results, comment_results);
                                grid.setAdapter(adapter);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSONArray Error", "Error:" + error);
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
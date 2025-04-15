package com.example.inventorymanagementapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText search_bar = findViewById(R.id.search_bar);
        Button filter_button = findViewById(R.id.filter_button);
        Button add_item_button = findViewById(R.id.add_item_button);
        ListView item_list = findViewById(R.id.item_list);

        ArrayList<String> item_names = new ArrayList<String>();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();
        String url = getString(R.string.server_url_root)+"/images";
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject data = response.getJSONObject(i);
                                        String item_name = data.getString("name");

                                        item_names.add(item_name);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Log.d("ViewActivity", "Result size " + item_names.size());
                                ItemListAdapter adapter = new ItemListAdapter(item_list.getContext(), item_names);
                                item_list.setAdapter(adapter);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSONArray Error", "Error:" + error);
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }
            @Override
            public void afterTextChanged(Editable s) {
                updateMain();
            }
        });

        filter_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this, "Filter Button Clicked.", Toast.LENGTH_SHORT).show();
            }
        });

        add_item_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(i);
            }
        });

        updateMain();
    }

    protected void updateMain(){
        Toast.makeText(MainActivity.this, "Main Activity page updated.", Toast.LENGTH_SHORT).show();
    }
}
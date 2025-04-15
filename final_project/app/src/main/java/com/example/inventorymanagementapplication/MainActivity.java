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
    protected void onResume() {
        super.onResume();
        updateMain();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText search_bar = findViewById(R.id.search_bar);
        Button add_item_button = findViewById(R.id.add_item_button);

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
        EditText search_bar = findViewById(R.id.search_bar);
        ArrayList<ArrayList<ArrayList<String>>> items = new ArrayList<ArrayList<ArrayList<String>>>();
        // [
        //      [
        //          [name, count, location a, description],
        //          [name, count, location a, description]...
        //      ],
        //      [
        //          [name, count, location b, description]...
        //      ]...
        //  ]
        ListView item_list = findViewById(R.id.item_list);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();
        String url = getString(R.string.server_url_root)+"/get_items";
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

                                        if (item_name.contains(search_bar.getText().toString())) {
                                            ArrayList<String> new_item = new ArrayList<String>();
                                            new_item.add(item_name);
                                            new_item.add(data.getString("count"));
                                            new_item.add(data.getString("location"));
                                            new_item.add(data.getString("description"));

                                            Boolean location_known = false;
                                            for (int j = 0; j < items.size(); j++){
                                                if (items.get(j).get(0).get(2).equals(new_item.get(2))){
                                                // if locations are same
                                                    items.get(j).add(new_item);
                                                    location_known = true;
                                                    break;
                                                }
                                            }
                                            if (! location_known){
                                                ArrayList<ArrayList<String>> new_location_list = new ArrayList<ArrayList<String>>();
                                                new_location_list.add(new_item);
                                                items.add(new_location_list);
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Log.d("ViewActivity", "Result size " + items.size());
                                ItemListAdapter adapter = new ItemListAdapter(item_list.getContext(), items);
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
    }
}
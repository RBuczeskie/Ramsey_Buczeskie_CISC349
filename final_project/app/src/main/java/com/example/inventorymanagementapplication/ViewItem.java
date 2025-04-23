package com.example.inventorymanagementapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class ViewItem extends AppCompatActivity {
    static ItemListAdapter adapter;
    ArrayList<String> item;
    // [id, name, count, location, description]

    public static Intent newIntent(Context packageContext, ItemListAdapter adapterRef) {
        Intent i = new Intent(packageContext, ViewItem.class);
        adapter = adapterRef;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);

        int index = getIntent().getIntExtra(ItemListAdapter.EXTRA_SELECTED_ITEM, -1);
        if (index >= 0)
        {
            item = adapter.populateView(findViewById(R.id.item_view), index);
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();

        Button edit_button = (Button) findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        Button delete_button = (Button) findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.server_url_root)+"/delete_item?id="+item.get(0);
                JsonArrayRequest jsonArrayRequest =
                        new JsonArrayRequest(Request.Method.PUT,
                                url, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        for (int i = 0; i < response.length(); i++) {
                                            try {
                                                JSONObject data = response.getJSONObject(i);
                                                String item_name = data.getString("name");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("JSONArray Error", "Error:" + error);
                            }
                        });
                // Add the request to the RequestQueue.
                queue.add(jsonArrayRequest);
                finish();
            }
        });

        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
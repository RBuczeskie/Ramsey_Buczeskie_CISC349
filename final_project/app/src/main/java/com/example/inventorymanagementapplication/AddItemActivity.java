package com.example.inventorymanagementapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);

        EditText name_text = findViewById(R.id.name_text);
        EditText count_text = findViewById(R.id.count_text);
        EditText location_text = findViewById(R.id.location_text);
        EditText description_text = findViewById(R.id.description_text);
        Button add_item_button = findViewById(R.id.add_item_button);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();

        add_item_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String item_name = name_text.getText().toString();
                String item_count_string = count_text.getText().toString();
                String item_location = location_text.getText().toString();
                if (item_name.equals("") || item_count_string.equals("") || item_location.equals("")) {
                    Toast.makeText(AddItemActivity.this, "Please enter a name, count, and location for the item.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer item_count = Integer.valueOf(item_count_string);
                String item_description = description_text.getText().toString();

                JSONObject json = new JSONObject();
                try {
                    json.put("name", item_name);
                    json.put("count", item_count);
                    json.put("location", item_location);
                    json.put("description", item_description);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = getString(R.string.server_url_root)+"/add_item";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Hello", "Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Hello", error.getMessage());
                    }
                });

                queue.add(jsonObjectRequest);
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

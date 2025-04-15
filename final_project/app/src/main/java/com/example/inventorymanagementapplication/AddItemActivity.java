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
        Button add_item_button = findViewById(R.id.add_item_button);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();

        add_item_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String item_name = name_text.getText().toString();

                JSONObject json = new JSONObject();
                try {
                    json.put("name", item_name);
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

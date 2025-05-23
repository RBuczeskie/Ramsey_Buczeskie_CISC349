package com.example.hustagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    EditText edit;
    BitmapDrawable drawable;
    String comment_string;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button capture_image_button = findViewById(R.id.capture_image_button);
        Button upload_image_button = findViewById(R.id.upload_image_button);
        Button view_images_button = findViewById(R.id.view_images_button);
        imageView = findViewById(R.id.cameraImageView);
        edit = findViewById(R.id.edit_text);

        queue = Volley.newRequestQueue(this);
        queue.start();

        capture_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });

        upload_image_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                drawable = (BitmapDrawable) imageView.getDrawable();
                comment_string = edit.getText().toString();
                if (drawable != null && ! comment_string.equals("")) {
                    final Bitmap bitmap = drawable.getBitmap();
                    uploadToServer(encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 100), comment_string);
                } else {
                    Toast.makeText(MainActivity.this, "Please take a picture and write a comment to upload.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view_images_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(i);
            }
        });

    }
            @Override
            protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == 1) {
                    if (resultCode == RESULT_OK) {
                        assert data != null;
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(imageBitmap);

                    } else if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
                ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
                image.compress(compressFormat, quality, byteArrayOS);
                return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
            };

    private void uploadToServer(final String image, final String comment) {

        JSONObject json = new JSONObject();
        try {
            json.put("image", image);
            json.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://10.0.0.39:5000/image";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Hello", "Response: " + response.toString());
                        Toast.makeText(MainActivity.this, "Image uploaded.", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Hello", error.getMessage());
            }
        });

        queue.add(jsonObjectRequest);
    }
}
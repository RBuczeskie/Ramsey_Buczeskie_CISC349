package com.example.hustagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ViewImage extends AppCompatActivity {
    static PictureListAdapter adapter;

    public static Intent newIntent(Context packageContext, PictureListAdapter adapterRef) {
        Intent i = new Intent(packageContext, ViewImage.class);
        adapter = adapterRef;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);

        int index = getIntent().getIntExtra(PictureListAdapter.EXTRA_SELECTED_ITEM, -1);
        if (index >= 0)
        {
            adapter.populateView(findViewById(R.id.image_view), index);
        }

        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

package com.example.inventorymanagementapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ViewItem extends AppCompatActivity {
    static ItemListAdapter adapter;

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
            adapter.populateView(findViewById(R.id.item_view), index);
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
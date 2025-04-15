package com.example.inventorymanagementapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends BaseAdapter {
    List<String> item_names;
    private Context context;
    public static final String EXTRA_SELECTED_ITEM = "com.example.inventorymanagementapplication.selecteditem";

    public ItemListAdapter(Context context, ArrayList<String> item_names) {
        this.context = context;
        this.item_names = item_names;
    }


    @Override
    public int getCount() {
        if (null == item_names) return 0;
        else return item_names.size();
    }

    @Override
    public Object getItem(int i) {
        if (null == item_names) return null;
        else return item_names.get(i);
    }

    @Override
    public long getItemId(int i) {
        if (null == item_names) return 0;
        else return item_names.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.list_item,
                viewGroup, false);

        Log.d("ItemListAdapter", "Index " + i);

        String name = item_names.get(i);

        TextView item_name = view.findViewById(R.id.item_name);

        item_name.setText(name);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Item Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = ViewItem.newIntent(view.getContext(),ItemListAdapter.this);
                intent.putExtra(EXTRA_SELECTED_ITEM, i);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }

    public void populateView(View view, int index){
        TextView textView = view.findViewById(R.id.item_name);
        textView.setText(item_names.get(index));
    }

}

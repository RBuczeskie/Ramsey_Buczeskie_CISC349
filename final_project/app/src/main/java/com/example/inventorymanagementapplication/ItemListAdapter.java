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
    ArrayList<String> item_ids = new ArrayList<String>();
    ArrayList<String> item_names = new ArrayList<String>();
    ArrayList<String> item_counts = new ArrayList<String>();
    ArrayList<String> item_locations = new ArrayList<String>();
    ArrayList<String> item_descriptions = new ArrayList<String>();

    private Context context;
    public static final String EXTRA_SELECTED_ITEM = "com.example.inventorymanagementapplication.selecteditem";
    String item_description;

    public ItemListAdapter(Context context, ArrayList<ArrayList<ArrayList<String>>> items) {
        this.context = context;
        for (int i=0; i < items.size(); i++){
            for (int j=0; j < items.get(i).size(); j++){
                item_ids.add(items.get(i).get(j).get(0));
                item_names.add(items.get(i).get(j).get(1));
                item_counts.add(items.get(i).get(j).get(2));
                item_locations.add(items.get(i).get(j).get(3));
                item_description = items.get(i).get(j).get(4);
                if (item_description.equals("")){item_description = "No description.";}
                item_descriptions.add(item_description);
            }
        }
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

        TextView item_name = view.findViewById(R.id.item_name);
        TextView item_count = view.findViewById(R.id.item_count);
        TextView item_location = view.findViewById(R.id.item_location);

        item_name.setText(item_names.get(i));
        item_count.setText(item_counts.get(i));
        item_location.setText(item_locations.get(i));

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

    public ArrayList<String> populateView(View view, int index){
        ArrayList<String> item = new ArrayList<String>();
        // [id, name, count, location, description]
        String id = item_ids.get(index);
        String name = item_names.get(index);
        String count = item_counts.get(index);
        String location = item_locations.get(index);
        String description = item_descriptions.get(index);

        TextView textView = view.findViewById(R.id.item_id);
        textView.setText("ID: "+id);
        item.add(id);
        textView = view.findViewById(R.id.item_name);
        textView.setText(name);
        item.add(name);
        textView = view.findViewById(R.id.item_count);
        textView.setText(count+" available at ");
        item.add(count);
        textView = view.findViewById(R.id.item_location);
        textView.setText(location);
        item.add(location);
        textView = view.findViewById(R.id.item_description);
        textView.setText("Item Description: "+description);
        item.add(description);

        return item;
    }

}

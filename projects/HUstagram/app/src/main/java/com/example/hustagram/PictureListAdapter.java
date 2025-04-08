package com.example.hustagram;

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

public class PictureListAdapter extends BaseAdapter {
    //implements AdapterView.OnItemClickListener
    List<Bitmap> pictures;
    List<String> comments;
    private Context context;
    public static final String EXTRA_SELECTED_ITEM = "com.example.hustagram.selecteditem";

    public PictureListAdapter(Context context, ArrayList<Bitmap> pictures, ArrayList<String> comments) {
        this.context = context;
        this.pictures = pictures;
        this.comments = comments;
    }


    @Override
    public int getCount() {
        if (null == pictures) return 0;
        else return pictures.size();
    }

    @Override
    public Object getItem(int i) {
        if (null == pictures) return null;
        else return pictures.get(i);
    }

    @Override
    public long getItemId(int i) {
        if (null == pictures) return 0;
        else return pictures.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.list_item,
                viewGroup, false);

        Log.d("PictureListAdapter", "Image index " + i);

        Bitmap picture = pictures.get(i);

        ImageView image = (ImageView) view.findViewById(R.id.listImageView);

        image.setImageBitmap(picture);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Item Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = ViewImage.newIntent(view.getContext(),PictureListAdapter.this);
                intent.putExtra(EXTRA_SELECTED_ITEM, i);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }

    public void populateView(View view, int index){
        //HolidaySongs album = holidaySongs.get(index);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageBitmap(pictures.get(index));
        TextView textView = view.findViewById(R.id.comment);
        textView.setText(comments.get(index));

        //NetworkImageView image = (NetworkImageView) view.findViewById(R.id.albumDisplayImageView);
        //image.setImageUrl(album.getPlaylistImage(), imageLoader);
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
//    {
//        Toast.makeText(context, "Item Clicked", Toast.LENGTH_SHORT).show();
//        Intent intent = ViewImage.newIntent(adapterView.getContext(),this);
//        intent.putExtra(EXTRA_SELECTED_ITEM, i);
//        adapterView.getContext().startActivity(intent);
//    }
}

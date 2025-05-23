package com.example.dynamiclistview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class HolidaySongsAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private Context context;
    private ArrayList<HolidaySongs> holidaySongs;
    private ImageLoader imageLoader;
    public static final String EXTRA_SELECTED_ITEM = "com.example.dynamiclistview.selecteditem";
    public HolidaySongsAdapter(Context context, ArrayList<HolidaySongs> holidaySongs, RequestQueue queue) {
        this.context = context;
        this.holidaySongs = holidaySongs;

        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });
    }

    @Override
    public int getCount(){
        if (holidaySongs == null) return 0;
        return holidaySongs.size();
    }
    @Override
    public Object getItem(int i){
        if (holidaySongs == null) return null;
        return holidaySongs.get(i);
    }
    @Override
    public long getItemId(int i){
        if (holidaySongs == null) return 0;
        return holidaySongs.get(i).hashCode();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        view = LayoutInflater.from(context).inflate(R.layout.album_view, viewGroup, false);
        HolidaySongs album = holidaySongs.get(i);

        TextView tv = view.findViewById(R.id.albumName);
        tv.setText(album.getName());
        tv = view.findViewById(R.id.artistName);
        tv.setText(album.getArtist());
        tv = view.findViewById(R.id.danceability);
        tv.setText(String.format("Danceability: %.3f", album.getDanceability()));
        tv = view.findViewById(R.id.duration);
        tv.setText(String.format("%d:%d", (album.getDurationMs()/1000)/60, album.getDurationMs()/1000%60));

        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.albumImageView);
        image.setImageUrl(album.getImage(), imageLoader);

        return view;
    }
    public void populateView(View view, int index){
        HolidaySongs album = holidaySongs.get(index);
        TextView tv = view.findViewById(R.id.albumDisplayName);
        tv.setText(album.getName());
        tv = view.findViewById(R.id.artistDisplayName);
        tv.setText(album.getArtist());

        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.albumDisplayImageView);
        image.setImageUrl(album.getPlaylistImage(), imageLoader);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent = ViewSongs.newIntent(adapterView.getContext(),this);
        intent.putExtra(EXTRA_SELECTED_ITEM, i);
        adapterView.getContext().startActivity(intent);
    }
}
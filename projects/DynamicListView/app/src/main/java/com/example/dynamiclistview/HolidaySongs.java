package com.example.dynamiclistview;

import org.json.JSONException;
import org.json.JSONObject;

public class HolidaySongs {
    private String album_img;
    private String album_name;
    private String artist_name;
    private double danceability;
    private int duration_ms;
    private String playlist_img;

    public HolidaySongs(JSONObject jsonData) throws JSONException {
        this.setImage(jsonData.getString("album_img"));
        this.setName(jsonData.getString("album_name"));
        this.setArtist(jsonData.getString("artist_name"));
        this.setDanceability(jsonData.getDouble("danceability"));
        this.setDurationMs(jsonData.getInt("duration_ms"));
        this.setPlaylistImage(jsonData.getString("playlist_img"));
    }

    public String getImage() {
        return album_img;
    }

    public void setImage(String image) {
        this.album_img = image;
    }

    public String getName() {
        return album_name;
    }

    public void setName(String name) {
        this.album_name = name;
    }

    public String getArtist() {
        return artist_name;
    }

    public void setArtist(String artist) {
        this.artist_name = artist;
    }

    public double getDanceability() {
        return danceability;
    }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    public int getDurationMs() {
        return duration_ms;
    }

    public void setDurationMs(int durationMs) {
        this.duration_ms = durationMs;
    }

    public String getPlaylistImage() {
        return playlist_img;
    }

    public void setPlaylistImage(String playlistImage) {
        this.playlist_img = playlistImage;
    }
}
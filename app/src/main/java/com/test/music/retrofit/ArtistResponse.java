package com.test.music.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.music.pojo.Album;
import com.test.music.pojo.Artist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArtistResponse implements Serializable{
    @SerializedName("artists")
    @Expose
    private List<Artist> artists = new ArrayList<Artist>();

    @SerializedName("albums")
    @Expose
    private List<Album> albums = new ArrayList<Album>();

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
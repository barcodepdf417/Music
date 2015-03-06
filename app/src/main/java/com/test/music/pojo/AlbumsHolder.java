package com.test.music.pojo;

import java.io.Serializable;
import java.util.List;

public class AlbumsHolder implements Serializable {
    private List<Album> albums;

    public AlbumsHolder(List<Album> albums) {
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}

package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data;

import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;

import java.util.ArrayList;
import java.util.Objects;

public class Playlist {
    private final double latitude;
    private final double longitude;
    private String url;
    private final String name;
    private Integer image;
    private ArrayList<Tag> tagList;

    public Playlist(double latitude, double longitude, String url, String name, Integer image, ArrayList<Tag> tagList) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
        this.name = name;
        this.image = image;
        this.tagList = tagList;
    }

    /* For playlist duplication */
    public Playlist(Playlist other) {
        this.latitude = other.getLatitude();
        this.longitude = other.getLongitude();
        this.url = other.getUrl();
        this.name = other.getName();
        this.image = other.getImage();
        this.tagList = other.getTagList();
    }

    double getLatitude() {
        return latitude;
    }

    double getLongitude() {
        return longitude;
    }

    String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    String getName() {
        return name;
    }

    Integer getImage() {
        return image;
    }

    void setImage(Integer image) {
        this.image = image;
    }

    ArrayList<Tag> getTagList() {
        return new ArrayList<>(tagList);
    }

    void setTagList(ArrayList<Tag> tagList) {
        this.tagList = tagList;
    }

    Playlist copy() {
        return new Playlist(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(getName(), playlist.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}

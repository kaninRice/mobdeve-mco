package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data;

import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a playlist with a latitude, longitude, URL, name, image, and tag list.
 * Provides methods for getters and setters for its attributes
 */
public class Playlist {
    private final long _id;
    private final double latitude;
    private final double longitude;
    private String url;
    private final String name;
    private Integer image;
    private ArrayList<Tag> tagList;

    /**
     * Creates an instance of {@link Playlist}
     * @param _id The id of the playlist
     * @param latitude The latitude associated with the playlist
     * @param longitude The longitude associated with the playlist
     * @param url The URL of the playlist
     * @param name The name of the playlist
     * @param image The image of the playlist
     * @param tagList THe tag list of the playlist
     */
    public Playlist(long _id, double latitude, double longitude, String url, String name, Integer image, ArrayList<Tag> tagList) {
        this._id = _id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
        this.name = name;
        this.image = image;
        this.tagList = tagList;
    }

    /**
     * Creates an instance of {@link Playlist}. Used for Playlist duplication
     * @param other The {@link Playlist} to be duplicated
     */
    public Playlist(Playlist other) {
        this._id = other.getId();
        this.latitude = other.getLatitude();
        this.longitude = other.getLongitude();
        this.url = other.getUrl();
        this.name = other.getName();
        this.image = other.getImage();
        this.tagList = other.getTagList();
    }

    /**
     * Gets the id
     * @return The playlist id
     */
    long getId() {
        return _id;
    }

    /**
     * Gets the playlist latitude
     * @return The playlist latitude
     */
    double getLatitude() {
        return latitude;
    }

    /**
     * Gets the playlist longitude
     * @return The playlist longitude
     */
    double getLongitude() {
        return longitude;
    }

    /**
     * Gets the playlist URL
     * @return The playlist URL
     */
    String getUrl() {
        return url;
    }

    /**
     * Sets the playlist URL
     * @param url The new URL
     */
    void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the playlist name
     * @return The playlist name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the playlist image
     * @return The playlist Image
     */
    Integer getImage() {
        return image;
    }

    /**
     * Sets the playlist image
     * @param image The new playlist image
     */
    void setImage(Integer image) {
        this.image = image;
    }

    /**
     * Gets the playlist tag list
     * @return The playlist tag list
     */
    ArrayList<Tag> getTagList() {
        return new ArrayList<>(tagList);
    }

    /**
     * Sets the playlist tag list
     * @param tagList The new playlist tag list
     */
    void setTagList(ArrayList<Tag> tagList) {
        this.tagList = tagList;
    }

    /**
     * Creates a duplicate of the playlist
     * @return A duplicate of this {@link Playlist}
     */
    Playlist copy() {
        return new Playlist(this);
    }

    /**
     * Indicates whether an object is equals to this object
     * @param o The object to be compared to this object
     * @return {@code True} if the object is equals, {@code False} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(getName(), playlist.getName());
    }

    /**
     * Returns the hash code value of this object
     * @return The hash code value of this object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}

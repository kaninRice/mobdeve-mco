package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;

import java.util.ArrayList;
import java.util.Objects;

public class Playlist implements Parcelable {
    private final double latitude;
    private final double longitude;
    private String url;
    private String name;
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

    public Playlist(Playlist other) {
        this.latitude = other.getLatitude();
        this.longitude = other.getLongitude();
        this.url = other.getUrl();
        this.name = other.getName();
        this.image = other.getImage();
        this.tagList = other.getTagList();
    }

    protected Playlist(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        url = in.readString();
        name = in.readString();
        image = in.readInt();
        tagList = in.readArrayList(Tag.class.getClassLoader());
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

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

    void setName(String name) {
        this.name = name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(url);
        parcel.writeString(name);
        parcel.writeInt(image);
        parcel.writeList(tagList);
    }
}

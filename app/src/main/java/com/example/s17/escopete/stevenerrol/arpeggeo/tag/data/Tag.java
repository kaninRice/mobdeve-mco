package com.example.s17.escopete.stevenerrol.arpeggeo.tag.data;

import java.util.Objects;

/**
 * Represents a tag with an id, name, color, text color, playlist id it is associated to
 * Provides methods for getters and setters for its attributes
 */
public class Tag {
    private final long _id;
    private final String name;
    private String color;
    private TextColor textColor;
    private final long _playlistId;

    /**
     * Creates an instance of {@link Tag}
     * @param name The name of the tag
     * @param color The color of the tag; Also used to determine text color
     */
    Tag(long _id, String name, String color, long _playlistId) {
        this._id = _id;
        this.name = name;
        this.color = color;
        this.textColor = determineTextColor();
        this._playlistId = _playlistId;
    }

    /**
     * Creates an instance of {@link Tag}
     * @param _id The id of the tag
     * @param name The name of the tag
     * @param color The color of the tag
     * @param textColor The text color of the tag
     * @param _playlistId The playlist id the tag is associated to
     */
    public Tag(long _id, String name, String color, String textColor, long _playlistId) {
        this._id = _id;
        this.name = name;
        this.color = color;
        this.textColor = TextColor.valueOf(textColor);
        this._playlistId = _playlistId;
    }

    /**
     * Creates an instance of {@link Tag}. Used for Tag duplication
     * @param other The {@link Tag} to be duplicated
     */
    Tag(Tag other) {
        this._id = other.getId();
        this.name = other.getName();
        this.color = other.getColor();
        this.textColor = determineTextColor();
        this._playlistId = other.getPlaylistId();
    }

    /**
     * Determines the text color based on {@link Tag} color. Assumes #FFFFFF string format
     * @return {@link TextColor}
     */
    private TextColor determineTextColor() {
        int red = Integer.parseInt(color.substring(1, 3), 16);
        int green = Integer.parseInt(color.substring(3,5), 16);
        int blue = Integer.parseInt(color.substring(5), 16);

        /* overall color intensity formula is used to determine appropriate text color */
        if ((red * 0.299) + (green * 0.587) + (blue * 0.114) <= 186) {
            return TextColor.LIGHT;
        }

        return TextColor.DARK;
    }

    /**
     * Gets the tag id
     * @return The tag id typed {@code long}
     */
    long getId() {
        return _id;
    }

    /**
     * Gets the tag name
     * @return The tag name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the tag color
     * @return The tag color
     */
    String getColor() {
        return color;
    }

    /**
     * Sets the tag color
     * @param color The new tag color
     */
    void setColor(String color) {
        this.color = color;
        this.textColor = determineTextColor();
    }

    /**
     * Gets the tag text color
     * @return A {@link TextColor}
     */
    TextColor getTextColor() {
        return textColor;
    }

    /**
     * Gets the playlist id
     * @return playlist id typed {@code long}
     */
    long getPlaylistId() {
        return _playlistId;
    }

    /**
     * Create a duplicate of the tag
     * @return A duplicate of this {@link Tag}
     */
    Tag copy() {
        return new Tag(this);
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
        Tag tag = (Tag) o;
        return Objects.equals(getName(), tag.getName());
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


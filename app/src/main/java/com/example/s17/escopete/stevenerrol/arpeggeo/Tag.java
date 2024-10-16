package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Tag implements Parcelable {
    private String name;
    private String color;
    private TextColor textColor;

    Tag(String name, String color) {
        this.name = name;
        this.color = color;
        this.textColor = determineTextColor();
    }

    /* assumes #FFFFFF format */
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

    protected Tag(Parcel in) {
        name = in.readString();
        color = in.readString();
        textColor = TextColor.valueOf(in.readString());
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getColor() {
        return color;
    }

    void setColor(String color) {
        this.color = color;
        this.textColor = determineTextColor();
    }

    TextColor getTextColor() {
        return textColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(getName(), tag.getName());
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
        parcel.writeString(name);
        parcel.writeString(color);
        parcel.writeString(textColor.name());
    }
}

enum TextColor {
    LIGHT,
    DARK
}

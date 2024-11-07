package com.example.s17.escopete.stevenerrol.arpeggeo.tag.data;

import java.util.Objects;

public class Tag {
    private final String name;
    private String color;
    private TextColor textColor;

    Tag(String name, String color) {
        this.name = name;
        this.color = color;
        this.textColor = determineTextColor();
    }

    /* For tag duplication */
    Tag(Tag other) {
        this.name = other.getName();
        this.color = other.getColor();
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

    String getName() {
        return name;
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

    Tag copy() {
        return new Tag(this);
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
}


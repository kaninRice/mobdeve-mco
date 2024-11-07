package com.example.s17.escopete.stevenerrol.arpeggeo.tag.data;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TagRepositoryImpl implements TagRepository {
    private final ArrayList<Tag> tagList;

    @Inject
    public TagRepositoryImpl() {
        // TODO: Get tags from local storage
        tagList = new ArrayList<>();

        tagList.add(
                new Tag("teka lang", "#04A793")
        );

        tagList.add(
                new Tag("asdf", "#000000")
        );

        tagList.add(
                new Tag("Tata", "#D035A5")
        );

        for(int i = 0; i < 50; i++) {
            tagList.add(
                    new Tag("New Tag " + i, "#FFFFFF")
            );
        }
    }

    @Override
    public ArrayList<Tag> getAllTags() {
        return new ArrayList<>(tagList);
    }

    @Override
    public Tag getTagByIndex(int index)  {
        return tagList.get(index).copy();
    }

    @Override
    public String getTagNameByIndex(int index)  {
        return tagList.get(index).getName();
    }

    @Override
    public String getTagColor(String name) {
        for (Tag tag : tagList) {
            if (tag.getName().equals(name)) {
                return  tag.getColor();
            }
        }

        return "";
    }

    @Override
    public TextColor getTagTextColor(String name) {
        for (Tag tag : tagList) {
            if (tag.getName().equals(name)) {
                return tag.getTextColor();
            }
        }

        return TextColor.LIGHT;
    }
}

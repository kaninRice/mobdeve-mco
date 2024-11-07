package com.example.s17.escopete.stevenerrol.arpeggeo.tag.data;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TagRepositoryImpl implements TagRepository {
    private final ArrayList<Tag> tagList;

    @Inject
    public TagRepositoryImpl() {
        // temp
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
    public String getTagName(Tag tag)  {
        return tag.getName();
    }

    @Override
    public String getTagColor(Tag tag) {
        return tag.getColor();
    }

    @Override
    public TextColor getTagTextColor(Tag tag) {
        return tag.getTextColor();
    }
}

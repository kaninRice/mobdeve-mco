package com.example.s17.escopete.stevenerrol.arpeggeo.tag.data;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The implementation of the {@link TagRepository} interface
 */
@Singleton
public class TagRepositoryImpl implements TagRepository {
    private final ArrayList<Tag> tagList;

    /**
     * Initializes {@link TagRepositoryImpl}. Retrieves tags from local storage
     */
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

    /**
     * Retrieves all tags
     * Returns a copy of the {@link ArrayList} of all {@link Tag}
     * @return An {@link ArrayList} of {@link Tag}s
     */
    @Override
    public ArrayList<Tag> getAllTags() {
        return new ArrayList<>(tagList);
    }

    /**
     * Retrieves a tag
     * @param index The index of the {@link Tag} to be retrieved
     * @return A {@link Tag} object
     */
    @Override
    public Tag getTagByIndex(int index)  {
        return tagList.get(index).copy();
    }

    /**
     * Retrieves the name of a {@link Tag} based on its index
     * Assumes the index is valid
     * @param index The index of the {@link Tag} to be retrieved
     * @return The name of the tag as {@link String}
     */
    @Override
    public String getTagNameByIndex(int index)  {
        return tagList.get(index).getName();
    }

    /**
     * Retrieves the color of a {@link Tag} based on its name
     * This iterates over the {@link ArrayList} of {@link Tag} until a {@link Tag}
     * matches the given {@code name}
     * @param name The name of the {@link Tag} to be retrieved
     * @return The color of the tag as {@link String}. {@code ""} if {@link Tag} is not found
     */
    @Override
    public String getTagColor(String name) {
        for (Tag tag : tagList) {
            if (tag.getName().equals(name)) {
                return  tag.getColor();
            }
        }

        return "";
    }

    /**
     * Retrieves the text color of a {@link Tag} based on its name
     * This iterates over the {@link ArrayList} of {@link Tag} until a {@link Tag}
     * matches the given {@code name}
     * @param name The name of the {@link Tag} to be retrieved
     * @return The text color of the tag as {@link TextColor}. {@link TextColor}{@code .LIGHT} if {@link Tag} is not found
     */
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

package com.example.s17.escopete.stevenerrol.arpeggeo.tag.data;

import java.util.ArrayList;

/**
 * The {@link TagRepositoryImpl} interface to define the methods for accessing {@link Tag}
 * objects and their properties
 */
public interface TagRepository {

    /**
     * Retrieves all {@link Tag}s
     * @return An {@link ArrayList} of {@link Tag}s
     */
    ArrayList<Tag> getAllTags();

    /**
     * Retrieves all tags of a playlist
     * @param playlistId The playlist id of the tags to retrieve
     * @return An {@link ArrayList} of {@link Tag}s
     */
    ArrayList<Tag> getAllTagsWithPlaylistId(long playlistId);

    /**
     * Retrieves a {@link Tag} based on given index
     * @param index The index of the {@link Tag} to be retrieved
     * @return A {@link Tag} Object
     */
    Tag getTagByIndex(int index);

    /**
     * Retrieves the name of a {@link Tag} based on its index
     * @param index The index of the {@link Tag} to be retrieved
     * @return The name of the tag as {@link String}
     */
    String getTagNameByIndex(int index);

    /**
     * Retrieves the color of a {@link Tag} based on its name
     * @param name The name of the {@link Tag} to be retrieved
     * @return The color of the tag as {@link String}
     */
    String getTagColor(String name);

    /**
     * Retrieves the text color of a {@link Tag} based on its name
     * @param name The name of the {@link Tag} to be retrieved
     * @return The text color of the tag as {@link TextColor}
     */
    TextColor getTagTextColor(String name);

    /**
     * Retrieves the highest-numbered id in the local storage
     * @return The highest-numbered id typed {@code long}
     */
    long getHighestId();

    /**
     * Inserts a tag
     * @param _id The id of the tag
     * @param name The name of the tag
     * @param color The color of the tag
     * @param textColor The text color of the tag
     * @param _playlistId The playlist id of the tag
     */
    void insertTag(long _id, String name, String color, String textColor, long _playlistId);
}

package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data;

import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;

import java.util.ArrayList;

/**
 * The {@link PlaylistRepositoryImpl} interface to define the methods for accessing {@link Playlist}
 * objects and their properties
 */
public interface PlaylistRepository {

    /**
     * Retrieves all {@link Playlist}s
     * @return An {@link ArrayList} of {@link Playlist}s
     */
    ArrayList<Playlist> getAllPlaylists();

    /**
     * Retrieves a {@link Playlist} based on given index
     * @param index The index of the {@link Playlist} to be retrieved
     * @return A {@link Playlist} Object
     */
    Playlist getPlaylistByIndex(int index);

    /**
     * Retrieves the latitude of a {@link Playlist} based on its name
     * @param name The name of the {@link Playlist} to be retrieved
     * @return The latitude of the playlist as {@link Double}
     */
    Double getPlaylistLatitude(String name);

    /**
     * Retrieves the longitude of a {@link Playlist} based on its name
     * @param name The name of the {@link Playlist} to be retrieved
     * @return The longitude of the playlist as {@link Double}
     */
    Double getPlaylistLongitude(String name);

    /**
     * Retrieves the PLAYLIST_URL of a {@link Playlist} based on its name
     * @param name The name of the {@link Playlist} to be retrieved
     * @return The PLAYLIST_URL of the playlist as {@link String}
     */
    String getPlaylistUrl(String name);

    /**
     * Retrieves the name of a {@link Playlist} based on its index
     * @param index The index of the {@link Playlist} to be retrieved
     * @return The name of the playlist as {@link String}
     */
    String getPlaylistNameByIndex(int index);

    /**
     * Retrieves the id of the playlist based on its name
     * @param name The name of the playlist
     * @return id typed {@code long}
     */
    Long getPlaylistIdByName(String name);

    /**
     * Retrieves the image of a {@link Playlist} based on its name
     * @param name The name of the {@link Playlist} to be retrieved
     * @return The image of the playlist as {@link Integer}
     */
    Integer getPlaylistImage(String name);

    /**
     * Retrieves the {@link Tag}s of a {@link Playlist} based on its name
     * @param name The name of the {@link Playlist} to be retrieved
     * @return An {@link ArrayList} of {@link Tag}s of the playlist
     */
    ArrayList<Tag> getPlaylistTagList(String name);

    /**
     * Retrieves the highest-numbered id in the local storage
     * @return The highest-numbered id typed {@code long}
     */
    long getHighestId();

    /**
     * Inserts a playlist
     * @param _id The id of the playlist
     * @param name The name of the playlist
     * @param url The url of the playlist
     * @param image The image of the playlist
     * @param latitude The latitude of the playlist
     * @param longitude The longitude of the playlist
     */
    void insertPlaylist(long _id, String name, String url,  Integer image, double latitude, double longitude);

    /**
     * Deletes a playlist based on name
     * @param name The name of the playlist to be deleted
     */
    void deletePlaylist(String name);

    /**
     * Deletes playlists based on their name
     * @param names An {@link ArrayList} of {@link String} of the playlists to be deleted
     */
    void deletePlaylist(ArrayList<String> names);
}

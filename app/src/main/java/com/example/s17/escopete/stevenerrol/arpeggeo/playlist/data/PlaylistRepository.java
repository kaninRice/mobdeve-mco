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
     * Retrieves the URL of a {@link Playlist} based on its name
     * @param name The name of the {@link Playlist} to be retrieved
     * @return The URL of the playlist as {@link String}
     */
    String getPlaylistUrl(String name);

    long getPlaylistId(String name);

    /**
     * Retrieves the name of a {@link Playlist} based on its index
     * @param index The index of the {@link Playlist} to be retrieved
     * @return The name of the playlist as {@link String}
     */
    String getPlaylistNameByIndex(int index);

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

    long getHighestId();
    void insertPlaylist(long _id, String name, String url,  Integer image, double latitude, double longitude);
    void deletePlaylist(String name);
}

package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data;

import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The implementation of the {@link PlaylistRepository} interface
 */
@Singleton
public class PlaylistRepositoryImpl implements PlaylistRepository {
    private final PlaylistDbManager playlistDbManager;
    private ArrayList<Playlist> playlistList;

    /**
     * Initializes {@link PlaylistRepositoryImpl}. Retrieves playlists from local storage
     * @param playlistDbManager The {@link PlaylistDbManager};
     *                          Used to get data from local storage
     */
    @Inject
    public PlaylistRepositoryImpl(PlaylistDbManager playlistDbManager) {
        this.playlistDbManager = playlistDbManager.open();
        playlistList = playlistDbManager.getAllPlaylists();

        /*
        playlistList.add(
                new Playlist(
                        14.5826, 120.9787,
                        "https://open.spotify.com/playlist/37i9dQZEVXbNBz9cRCSFkY",
                        "Top 50 - Philippines",
                        R.drawable.image_playlist_cover_0,
                        tagRepositoryImpl.getAllTags()
                )
        );

        playlistList.add(
                new Playlist(
                        14.5648, 120.9932,
                        "https://open.spotify.com/playlist/52o2wQe2s1J59bjpCSFxby",
                        "itchy nadal",
                        R.drawable.ic_default_playlist_image,
                        new ArrayList<>(Arrays.asList(
                                tagRepositoryImpl.getTagByIndex(0),
                                tagRepositoryImpl.getTagByIndex(1),
                                tagRepositoryImpl.getTagByIndex(2)
                        ))
                )
        );

        playlistList.add(
                new Playlist(
                        14.5352, 120.9819,
                        "https://open.spotify.com/playlist/6P20B2kzD3G25bQYJ6HSPl",
                        "Byaheng UV Express",
                        R.drawable.image_playlist_cover_1,
                        new ArrayList<>()
                )
        );
        */
    }

    private void updatePlaylist() {
        playlistList = playlistDbManager.getAllPlaylists();
    }

    /**
     * Retrieves all playlists
     * Returns a copy of the {@link ArrayList} of all {@link Playlist}
     * @return An {@link ArrayList} of {@link Playlist}s
     */
    @Override
    public ArrayList<Playlist> getAllPlaylists() {
        updatePlaylist();
        return new ArrayList<>(playlistList);
    }

    /**
     * Retrieves a playlist
     * Returns a copy of the {@link Playlist}
     * @param index The index of the playlist to be retrieved
     * @return A {@link Playlist} object
     */
    @Override
    public Playlist getPlaylistByIndex(int index) {
        return playlistList.get(index).copy();
    }

    /**
     * Retrieves the latitude of a {@link Playlist} based on its name.
     * This iterates over the {@link ArrayList} of {@link Playlist} until a {@link Playlist}
     * matches the given {@code name}
     * @param name The name of the {@link Playlist} to be retrieved
     * @return The latitude of the playlist as {@link Double}. {@code -1.0} if {@link Playlist} is not found
     */
    @Override
    public Double getPlaylistLatitude(String name) {
        for (Playlist playlist : playlistList) {
            if (playlist.getName().equals(name)) {
                return  playlist.getLatitude();
            }
        }

        return -1.0;
    }

    /**
     * Retrieves the longitude of a {@link Playlist} based on its name.
     * This iterates over the {@link ArrayList} of {@link Playlist} until a {@link Playlist}
     * matches the given {@code name}
     * @param name The name of the {@link Playlist} to be retrieved
     * @return The longitude of the playlist as {@link Double}. {@code -1.0} if {@link Playlist} is not found
     */
    @Override
    public Double getPlaylistLongitude(String name) {
        for (Playlist playlist : playlistList) {
            if (playlist.getName().equals(name)) {
                return  playlist.getLongitude();
            }
        }

        return -1.0;
    }

    /**
     * Retrieves the url of a {@link Playlist} based on its name.
     * This iterates over the {@link ArrayList} of {@link Playlist} until a {@link Playlist}
     * matches the given {@code name}
     * @param name The name of the {@link Playlist} to be retrieved
     * @return The url of the playlist as {@link Double}. {@code ""} if {@link Playlist} is not found
     */
    @Override
    public String getPlaylistUrl(String name) {
        for (Playlist playlist : playlistList) {
            if (playlist.getName().equals(name)) {
                return  playlist.getUrl();
            }
        }

        return "";
    }

    /**
     * Retrieves the name of a {@link Playlist} based on its index
     * Assumes the index is valid
     * @param index The index of the {@link Playlist} to be retrieved
     * @return The name of the playlist as {@link String}
     */
    @Override
    public String getPlaylistNameByIndex(int index) {
        return playlistList.get(index).getName();
    }

    /**
     * Retrieves the image of a {@link Playlist} based on its name
     * This iterates over the {@link ArrayList} of {@link Playlist} until a {@link Playlist}
     * matches the given {@code name}
     * @param name The name of the {@link Playlist} to be retrieved
     * @return The image of the playlist as {@link Integer}. {@code -1} if {@link Playlist} is not found
     */
    @Override
    public Integer getPlaylistImage(String name) {
        for (Playlist playlist : playlistList) {
            if (playlist.getName().equals(name)) {
                return  playlist.getImage();
            }
        }

        return -1;
    }

    /**
     * Retrieves the {@link Tag}s of a {@link Playlist} based on its name
     * This iterates over the {@link ArrayList} of {@link Playlist} until a {@link Playlist}
     * matches the given {@code name}
     * @param name The name of the {@link Playlist} to be retrieved
     * @return An {@link ArrayList} of {@link Tag} instances of the playlist. Returns an empty
     * {@link ArrayList} if a {@link Playlist} is not found.
     */
    @Override
    public ArrayList<Tag> getPlaylistTagList(String name) {
        for (Playlist playlist : playlistList) {
            if (playlist.getName().equals(name)) {
                return  playlist.getTagList();
            }
        }

        return new ArrayList<>();
    }

    @Override
    public void insertPlaylist(String name, String url,  Integer image, double latitude, double longitude) {
        playlistDbManager.insert(name, url, image, latitude, longitude);
    }
}

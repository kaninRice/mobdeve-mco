package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data;

import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlaylistRepositoryImpl implements PlaylistRepository {
    private final ArrayList<Playlist> playlistList;

    @Inject
    public PlaylistRepositoryImpl(TagRepositoryImpl tagRepositoryImpl) {
        // TODO: Get playlists from local storage
        playlistList = new ArrayList<>();

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
    }

    @Override
    public ArrayList<Playlist> getAllPlaylists() {
        return new ArrayList<>(playlistList);
    }

    @Override
    public Playlist getPlaylistByIndex(int index) {
        return playlistList.get(index).copy();
    }

    @Override
    public Double getPlaylistLatitude(String name) {
        for (Playlist playlist : playlistList) {
            if (playlist.getName().equals(name)) {
                return  playlist.getLatitude();
            }
        }

        return -1.0;
    }

    @Override
    public Double getPlaylistLongitude(String name) {
        for (Playlist playlist : playlistList) {
            if (playlist.getName().equals(name)) {
                return  playlist.getLongitude();
            }
        }

        return -1.0;
    }

    @Override
    public String getPlaylistUrl(String name) {
        for (Playlist playlist : playlistList) {
            if (playlist.getName().equals(name)) {
                return  playlist.getUrl();
            }
        }

        return "";
    }

    @Override
    public String getPlaylistNameByIndex(int index) {
        return playlistList.get(index).getName();
    }

    @Override
    public Integer getPlaylistImage(String name) {
        for (Playlist playlist : playlistList) {
            if (playlist.getName().equals(name)) {
                return  playlist.getImage();
            }
        }

        return -1;
    }

    @Override
    public ArrayList<Tag> getPlaylistTagList(String name) {
        for (Playlist playlist : playlistList) {
            if (playlist.getName().equals(name)) {
                return  playlist.getTagList();
            }
        }

        return new ArrayList<>();
    }
}

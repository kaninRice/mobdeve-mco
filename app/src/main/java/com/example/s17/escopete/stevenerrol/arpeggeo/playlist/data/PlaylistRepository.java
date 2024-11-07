package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data;

import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;

import java.util.ArrayList;

public interface PlaylistRepository {
    ArrayList<Playlist> getAllPlaylists();
    Playlist getPlaylistByIndex(int index);
    Double getPlaylistLatitude(String name);
    Double getPlaylistLongitude(String name);
    String getPlaylistUrl(String name);
    String getPlaylistNameByIndex(int index);
    Integer getPlaylistImage(String name);
    ArrayList<Tag> getPlaylistTagList(String name);
}

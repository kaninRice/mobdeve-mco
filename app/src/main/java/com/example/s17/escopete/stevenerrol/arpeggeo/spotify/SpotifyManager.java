package com.example.s17.escopete.stevenerrol.arpeggeo.spotify;

import static com.example.s17.escopete.stevenerrol.arpeggeo.BuildConfig.SPOTIFY_CLIENT_ID;
import static com.example.s17.escopete.stevenerrol.arpeggeo.BuildConfig.SPOTIFY_REDIRECT_URI;

import android.content.Context;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Manages Spotify connection and playback controls
 */
@Singleton
public class SpotifyManager {
    private SpotifyAppRemote spotifyAppRemote;
    private String accessToken;

    @Inject
    public SpotifyManager() {}

    /**
     * Sets the access token
     * @param token The access token to be set
     */
    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    /**
     * Connects to Spotify App Remote
     * @param context The context used to connect
     */
    public void connect(Context context) {
        ConnectionParams connectionParams = new ConnectionParams.Builder(SPOTIFY_CLIENT_ID)
                .setRedirectUri(SPOTIFY_REDIRECT_URI)
                .showAuthView(true) // User prompt if access is not authorized
                .build();

        SpotifyAppRemote.connect(context, connectionParams, new Connector.ConnectionListener() {
            @Override
            public void onConnected(SpotifyAppRemote remote) {
                spotifyAppRemote = remote;
            }

            @Override
            public void onFailure(Throwable error) {
            }
        });
    }

    /**
     * Plays a playlist
     * @param shareLink The link of the playlist to be played
     */
    public void play(String shareLink) {
        /* Convert "share link" to URI */

        /* Link is invalid */
        if (shareLink == null || !shareLink.contains("open.spotify.com")) {
            return;
        }

        /* Remove query parameters */
        String cleanedLink = shareLink.split("\\?")[0];

        /* Extract the entity type and ID */
        String[] parts = cleanedLink.split("/");
        if (parts.length < 5) {
            return; /* Not enough parts in the PLAYLIST_URL */
        }

        String entityType = parts[3]; /* e.g., track, album, playlist, artist */
        String id = parts[4]; /* Unique Spotify ID */

        /* URI Construction */
        String uri = "spotify:" + entityType + ":" + id;

        if (spotifyAppRemote != null && spotifyAppRemote.isConnected()) {
            spotifyAppRemote.getPlayerApi().play(uri);
        }
    }
}
